package com.ta.csc.service;

import com.ta.csc.domain.Container;
import com.ta.csc.domain.Interval;
import com.ta.csc.repositroy.ContainerRepository;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ta.csc.helper.Fixtures.*;
import static com.ta.csc.intervals.YMLContainerIntervals.*;

@Service
public class YMLContainerService {

    private final ContainerRepository containerRepository;

    public final double hazardousPrice = 23.61;
    public final double transshipmentReeferConnectionPrice = 45;
    public final double transshipmentReeferConnectionPrice_2024 = 55;
    public final double directReeferConnectionPrice = 35.53;
    public final double directReeferConnectionPrice_2023 = 36.24;
    public final double directReeferConnectionPrice_2024 = 36.96;
    public final double emptyContainerPrice = 2;

    public YMLContainerService(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

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
        int numOfDays = numberOfDaysForEachIntervalInMonth(container, getContainerIntervals(container), month, year)
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
            return price;
        return 0;
    }

    public double calculateReeferConnectionSurcharge(Container container, String month, String year)
            throws ParseException {

        int numOfDays = numberOfDaysForEachIntervalInMonth(container, getContainerIntervals(container), month, year)
                .stream().mapToInt(Integer::intValue).sum();
        double getdirectReeferConnectionPrice = (container.getIncDate().getYear() <= 123)
                ? ((container.getIncDate().getYear() == 123)
                        ? directReeferConnectionPrice_2023
                        : directReeferConnectionPrice)
                : (directReeferConnectionPrice_2024);
        if (container.isReef() && container.getInvoiceCategory().equals("Transshipment"))
            return numOfDays * ((container.getIncDate().getYear() <= 123) ? transshipmentReeferConnectionPrice
                    : transshipmentReeferConnectionPrice_2024);
        if (container.isReef() && !container.getInvoiceCategory().equals("Transshipment"))
            return DoubleRounder.round(numOfDays * getdirectReeferConnectionPrice, 2);
        return 0;
    }

    public double calculateDMGSurchargeContainer(Container container, String month, String year) throws ParseException {
        double HazardousPrice = 0, oogPrice = 0, tankPrice = 0;
        double price = storageOfEachIntervalInMonth(container, getSurchargeIntervals(container), month, year).stream()
                .mapToDouble(Double::doubleValue).sum();
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
        if ((container.getType().equals("TK") || container.getType().equals("TO")) && container.isImdg()) {
            double price = calculateStorageOfEachInterval(container, month, year).stream()
                    .mapToDouble(Double::doubleValue).sum()
                    + calculateHazardousContainerSurcharge(container, month, year)
                    + freeDays * getContainerIntervals(container).get(1).getPrice();
            return DoubleRounder.round(price, 2);
        } else if (container.getType().equals("TK") || container.getType().equals("TO"))
            return storageOfEachIntervalInMonth(container, getSurchargeIntervals(container), month, year).stream()
                    .mapToDouble(Double::doubleValue).sum();
        return 0;
    }

    public double calculateContainerTotalStoragePricing(Container container, String month, String year)
            throws ParseException {
        if (container.getFullOrEmpty().equals("Empty"))
            return 0;
        return storageOfEachIntervalInMonth(container, getContainerIntervals(container), month, year).stream()
                .mapToDouble(Double::doubleValue).sum();

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
        return storageOfEachIntervalInMonth(container, getContainerIntervals(container), month, year);
    }

    public List<Double> getEmptyContainerPrices(String month, String year) throws ParseException {
        List<Double> prices = new ArrayList<>();
        double price = 0;
        for (int number : getNumberOfEmptyContainersTeus(month, year, getAllYMLContainers())) {
            if (number > 300) {
                price = DoubleRounder.round((number - 300) * emptyContainerPrice, 2);
            }
            prices.add(price);
            price = 0;
        }
        return prices;
    }

    public List<Container> getAllYMLContainers() {
        return containerRepository.findByShippingLine("YML");
    }

    public List<Interval> getSurchargeIntervals(Container container) {
        List<Interval> intervals = getContainerIntervals(container);
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).getPrice() == 0)
                intervals.get(i).setPrice(intervals.get(i + 1).getPrice());
        }
        return intervals;
    }

    public List<Interval> getContainerIntervals(Container container) {
        if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Transshipment"))
            return (container.getIncDate().getYear() == 121) ? fullTransshipmentIntervals2021()
                    : fullTransshipmentIntervals();

        else if (container.isReef() && container.getFullOrEmpty().equals("Full")
                && !container.getInvoiceCategory().equals("Transshipment"))
            return (container.getIncDate().getYear() <= 123)
                    ? ((container.getIncDate().getYear() == 121) ? fullReeferDirectIntervals2021()
                            : (container.getIncDate().getYear() == 122) ? fullReeferDirectIntervals()
                                    : fullReeferDirectIntervals2023())
                    : fullReeferDirectIntervals2024();

        else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Import"))
            return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 121)
                    ? fullImportIntervals2021()
                    : (container.getIncDate().getYear() == 122) ? fullImportIntervals() : fullImportIntervals2023())
                    : fullImportIntervals2024();

        else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Export"))
            return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 121)
                    ? fullExportIntervals2021()
                    : (container.getIncDate().getYear() == 122) ? fullExportIntervals() : fullExportIntervals2023())
                    : fullExportIntervals2024();
        return null;
    }

}
