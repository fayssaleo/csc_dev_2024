package com.ta.csc.service;

import com.ta.csc.domain.Container;
import com.ta.csc.domain.Interval;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.ta.csc.helper.Fixtures.*;
import static com.ta.csc.intervals.StandardContainerIntervals.*;

@Service
public class StandardContainerService {

    public final double hazardousPrice = 23.61;
    public final double transshipmentReeferConnectionPrice = 45;
    public final double transshipmentReeferConnectionPrice_2024 = 55;
    public final double directReeferConnectionPrice = 35.53;
    public final double directReeferConnectionPrice_2023 = 36.24;
    public final double directReeferConnectionPrice_2024 = 36.96;

    public double calculateHazardousContainerSurcharge(Container container, String month, String year)
            throws ParseException {
        Date firstDate = container.getIncDate();
        Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
        int freeDays = 0;
        if (dateIsIncludedInMonth(firstDate, month, year) && dateIsIncludedInMonth(secondDate, month, year))
            freeDays = 2;
        if (dateIsIncludedInMonth(firstDate, month, year) && !dateIsIncludedInMonth(secondDate, month, year))
            freeDays = 1;
        if (!dateIsIncludedInMonth(firstDate, month, year) && dateIsIncludedInMonth(secondDate, month, year))
            freeDays = 1;
        int numOfDays = numberOfDaysForEachIntervalInMonth(container, getContainersIntervals(container), month, year)
                .stream().mapToInt(Integer::intValue).sum();
        while (numOfDays < freeDays) {
            freeDays--;
        }
        numOfDays -= freeDays;
        int coefficient = getContainerTeus(container);
        if (container.isImdg())
            return DoubleRounder.round(coefficient * numOfDays * hazardousPrice, 2);
        return 0;
    }

    public double calculateOOGSurchargeContainer(Container container, String month, String year) throws ParseException {
        double price = storageOfEachIntervalInMonth(container, getSurchargeIntervals(container), month, year).stream()
                .mapToDouble(Double::doubleValue).sum();
        if (container.isOog() && container.isImdg()) {
            price = calculateHazardousContainerSurcharge(container, month, year)
                    + calculateContainerTotalStoragePricing(container, month, year);
            return DoubleRounder.round(price, 2);
        } else if (container.isOog())
            return DoubleRounder.round(price, 2);
        return 0;
    }

    public double calculateDMGSurchargeContainer(Container container, String month, String year) throws ParseException {
        double HazardousPrice = 0, oogPrice = 0, tankPrice = 0, price = 0;
        if (container.isImdg() || container.getType().equals("DC"))
            price = storageOfEachIntervalInMonth(container, getSurchargeIntervals(container), month, year).stream()
                    .mapToDouble(Double::doubleValue).sum();
        else
            price = storageOfEachIntervalInMonthWithFreeDays(container, getSurchargeIntervals(container), month, year)
                    .stream().mapToDouble(Double::doubleValue).sum();
        if (container.isImdg())
            HazardousPrice = calculateHazardousContainerSurcharge(container, month, year);
        if (container.isOog())
            oogPrice = calculateOOGSurchargeContainer(container, month, year);
        if (container.getType().equals("TK") || container.getType().equals("TO"))
            tankPrice = calculateTankSurchargeContainer(container, month, year);
        if (container.isDmg())
            return DoubleRounder.round(2 * (price + HazardousPrice + oogPrice + tankPrice), 2);
        return 0;
    }

    public double calculateTankSurchargeContainer(Container container, String month, String year)
            throws ParseException {
        Date firstDate = container.getIncDate();
        Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
        int freeDays = 0;
        if (dateIsIncludedInMonth(firstDate, month, year) && dateIsIncludedInMonth(secondDate, month, year))
            freeDays = 2;
        if (dateIsIncludedInMonth(firstDate, month, year) && !dateIsIncludedInMonth(secondDate, month, year))
            freeDays = 1;
        if (!dateIsIncludedInMonth(firstDate, month, year) && dateIsIncludedInMonth(secondDate, month, year))
            freeDays = 1;
        double price = storageOfEachIntervalInMonth(container, getSurchargeIntervals(container), month, year).stream()
                .mapToDouble(Double::doubleValue).sum();
        if ((container.getType().equals("TK") || container.getType().equals("TO")) && container.isImdg()) {
            price = calculateStorageOfEachInterval(container, month, year).stream().mapToDouble(Double::doubleValue)
                    .sum()
                    + calculateHazardousContainerSurcharge(container, month, year)
                    + freeDays * getContainersIntervals(container).get(1).getPrice();
            return DoubleRounder.round(price, 2);
        } else if (container.getType().equals("TK") || container.getType().equals("TO"))
            return DoubleRounder.round(price, 2);
        return 0;
    }

    public double calculateContainerTotalStoragePricing(Container container, String month, String year)
            throws ParseException {
        return storageOfEachIntervalInMonth(container, getContainersIntervals(container), month, year).stream()
                .mapToDouble(Double::doubleValue).sum();

    }

    public double calculateReeferConnectionSurcharge(Container container, String month, String year)
            throws ParseException {
        double getdirectReeferConnectionPrice = (container.getIncDate().getYear() <= 123)
                ? ((container.getIncDate().getYear() == 123)
                        ? directReeferConnectionPrice_2023
                        : directReeferConnectionPrice)
                : directReeferConnectionPrice_2024;

        int numberOfDays = numberOfDaysForEachIntervalInMonth(container, getContainersIntervals(container), month, year)
                .stream().mapToInt(Integer::intValue).sum();
        if (container.isReef() && container.getInvoiceCategory().equals("Transshipment")
                && (container.getShippingLine().equals("MSK") || container.getShippingLine().equals("HSD")
                        || container.getShippingLine().equals("SEA") || container.getShippingLine().equals("SGL")))
            return DoubleRounder.round(
                    numberOfDays * ((container.getIncDate().getYear() <= 123) ? transshipmentReeferConnectionPrice
                            : transshipmentReeferConnectionPrice_2024),
                    2);
        if (container.isReef() && container.getInvoiceCategory().equals("Transshipment"))
            return numberOfDays * ((container.getIncDate().getYear() <= 123) ? transshipmentReeferConnectionPrice
                    : transshipmentReeferConnectionPrice_2024);
        if (container.isReef() && !container.getInvoiceCategory().equals("Transshipment"))
            return DoubleRounder.round(numberOfDays * getdirectReeferConnectionPrice, 2);
        return 0;
    }

    public double calculateTotalSurchargePrice(Container container, String month, String year) throws ParseException {
        return DoubleRounder.round(calculateHazardousContainerSurcharge(container, month, year)
                + calculateOOGSurchargeContainer(container, month, year)
                + calculateDMGSurchargeContainer(container, month, year)
                + calculateTankSurchargeContainer(container, month, year)
                + calculateReeferConnectionSurcharge(container, month, year), 2);
    }

    public double calculateTotalPrice(Container container, String month, String year) throws ParseException {
        return DoubleRounder.round(calculateTotalSurchargePrice(container, month, year)
                + calculateContainerTotalStoragePricing(container, month, year), 2);
    }

    public List<Double> calculateStorageOfEachInterval(Container container, String month, String year)
            throws ParseException {
        return storageOfEachIntervalInMonth(container, getContainersIntervals(container), month, year);
    }

    double getDirectReeferConnectionPrice(String shippingLine) {
        return shippingLine.equals("MSK") || shippingLine.equals("HSD") || shippingLine.equals("SEA")
                || shippingLine.equals("SGL") ? directReeferConnectionPrice_2024 : directReeferConnectionPrice;
    }

    public List<Interval> getSurchargeIntervals(Container container) {
        List<Interval> intervals = getContainersIntervals(container);
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).getPrice() == 0)
                intervals.get(i).setPrice(intervals.get(i + 1).getPrice());
        }
        return intervals;
    }

    public List<Interval> getContainersIntervals(Container container) {
        if (container.getInvoiceCategory().equals("Transshipment"))
            return (container.getIncDate().getYear() <= 122) ? transshipmentIntervals() : transshipmentIntervals2021();

        else if (container.isReef() && container.getFullOrEmpty().equals("Full")
                && !container.getInvoiceCategory().equals("Transshipment"))
            return (container.getIncDate().getYear() == 122) ? fullReeferDirectIntervals()
                    : (container.getIncDate().getYear() == 123) ? fullReeferDirectIntervals2023()
                            : (container.getIncDate().getYear() == 124) ? fullReeferDirectIntervals2024()
                                    : fullReeferDirectIntervals2021();

        else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Import"))
            return (container.getIncDate().getYear() == 122) ? fullImportIntervals()
                    : (container.getIncDate().getYear() == 123) ? fullImportIntervals2023()
                            : (container.getIncDate().getYear() == 124) ? fullImportIntervals2024()
                                    : fullImportIntervals2021();

        else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Export"))
            return (container.getIncDate().getYear() == 122) ? fullExportIntervals()
                    : (container.getIncDate().getYear() == 123) ? fullExportIntervals2023()
                            : (container.getIncDate().getYear() == 124) ? fullExportIntervals2024()
                                    : fullExportIntervals2021();

        ////// hnaaaa b9iiitiii

        else if (container.getFullOrEmpty().equals("Empty") && !container.getInvoiceCategory().equals("Import"))
            return (container.getIncDate().getYear() <= 123)
                    ? ((container.getIncDate().getYear() == 122) ? emptyImportIntervals()
                            : (container.getIncDate().getYear() == 123) ? emptyImportIntervals2023()
                                    : emptyImportIntervals2021())
                    : emptyImportIntervals2024();

        else if (container.getFullOrEmpty().equals("Empty") && !container.getInvoiceCategory().equals("Export"))
            return (container.getIncDate().getYear() <= 123)
                    ? ((container.getIncDate().getYear() == 122) ? emptyExportIntervals()
                            : (container.getIncDate().getYear() == 123) ? emptyExportIntervals2023()
                                    : emptyExportIntervals2021())
                    : emptyExportIntervals2024();
        return null;

    }

}
