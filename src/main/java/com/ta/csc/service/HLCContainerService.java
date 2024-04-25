package com.ta.csc.service;

import com.ta.csc.domain.Container;
import com.ta.csc.domain.Interval;
import com.ta.csc.repositroy.ContainerRepository;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.ta.csc.helper.Fixtures.*;
import static com.ta.csc.intervals.HLCContainerIntervals.*;

@Service
public class HLCContainerService {

    private final ContainerRepository containerRepository;

    public final double hazardousPrice = 22.68;
    public final double hazardousPrice_2023 = 23.44;
    public final double hazardousPrice_2024 = 23.61;
    public final double transshipmentReeferConnectionPrice = 31.23;
    public final double transshipmentReeferConnectionPrice_2023 = 32.28;
    public final double transshipmentReeferConnectionPrice_2024 = 36.50;
    public final double directReeferConnectionPrice = 35.53;
    public final double directReeferConnectionPrice_2023 = 36.24;
    public final double directReeferConnectionPrice_2024 = 36.96;
    public final double emptyContainerPrice = 2.08;

    public HLCContainerService(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    public double calculateHazardousContainerSurcharge(Container container, String month, String year)
            throws ParseException {
        int numOfDays = numberOfDaysForEachIntervalInMonth(container, getContainerIntervals(container), month, year)
                .stream().mapToInt(Integer::intValue).sum();
        int coefficient = getContainerTeus(container);
        double getCurrentHazardousPrice = (container.getIncDate().getYear() <= 123)
                ? ((container.getIncDate().getYear() == 123) ? hazardousPrice_2023
                        : hazardousPrice)
                : hazardousPrice_2024;
        if (container.isImdg())
            return DoubleRounder.round((coefficient * numOfDays * getCurrentHazardousPrice), 2);
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

    public double calculateReeferConnectionSurcharge(Container container, String month, String year)
            throws ParseException {
        int numOfDays = numberOfDaysForEachIntervalInMonth(container, getContainerIntervals(container), month, year)
                .stream().mapToInt(Integer::intValue).sum();
        double gettransshipmentReeferConnectionPrice = (container.getIncDate().getYear() <= 123)
                ? ((container.getIncDate().getYear() == 123)
                        ? transshipmentReeferConnectionPrice_2023
                        : transshipmentReeferConnectionPrice)
                : transshipmentReeferConnectionPrice_2024;
        double getdirectReeferConnectionPrice = (container.getIncDate().getYear() <= 123)
                ? ((container.getIncDate().getYear() == 123)
                        ? directReeferConnectionPrice_2023
                        : directReeferConnectionPrice)
                : directReeferConnectionPrice_2024;
        if (container.isReef() && container.getInvoiceCategory().equals("Transshipment"))
            return DoubleRounder.round((numOfDays * gettransshipmentReeferConnectionPrice), 2);
        if (container.isReef() && !container.getInvoiceCategory().equals("Transshipment"))
            return DoubleRounder.round(numOfDays * getdirectReeferConnectionPrice, 2);
        return 0;
    }

    public double calculateContainerTotalStoragePricing(Container container, String month, String year)
            throws ParseException {
        if (container.getFullOrEmpty().equals("Empty"))
            return 0;
        return storageOfEachIntervalInMonth(container, getContainerIntervals(container), month, year).stream()
                .mapToDouble(Double::doubleValue).sum();

    }

    public double calculateDMGSurchargeContainer(Container container, String month, String year) throws ParseException {
        double HazardousPrice = 0, oogPrice = 0, tankPrice = 0, price = 0;
        if (container.isImdg() || container.getType().equals("DC")
                || (container.getType().equals("OT") && !container.isOog()))
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
        if (container.getType().equals("TK") && container.isImdg()) {
            double price = storageOfEachIntervalInMonth(container, getSurchargeIntervals(container), month,
                    year).stream().mapToDouble(Double::doubleValue).sum()
                    + calculateHazardousContainerSurcharge(container, month, year);
            return DoubleRounder.round(price, 2);
        } else if (container.getType().equals("TK") || container.getType().equals("TO"))
            return storageOfEachIntervalInMonth(container, getSurchargeIntervals(container), month, year).stream()
                    .mapToDouble(Double::doubleValue).sum();
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
        return storageOfEachIntervalInMonth(container, getContainerIntervals(container), month, year);
    }

    public List<Double> getEmptyContainerPrices(String month, String year) throws ParseException {
        List<Double> prices = new ArrayList<>();
        double price = 0;

        for (int number : getNumberOfEmptyContainersTeus(month, year, getAllHLCContainers())) {
            if (number > 3545) {
                price = DoubleRounder.round(((number - 3545) * emptyContainerPrice), 2);
            }
            prices.add(price);
            price = 0;
        }
        return prices;
    }

    public List<Container> getAllHLCContainers() {
        return containerRepository.findByShippingLine("HLC");
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
        if (container.isReef() && container.getFullOrEmpty().equals("Full")
                && container.getInvoiceCategory().equals("Transshipment"))
            return (container.getIncDate().getYear() <= 123)
                    ? ((container.getIncDate().getYear() == 122) ? fullReeferTransshipmentIntervals()
                            : (container.getIncDate().getYear() == 123) ? fullReeferTransshipmentIntervals2023()
                                    : fullReeferTransshipmentIntervals2021())
                    : fullReeferTransshipmentIntervals2024();

        else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Transshipment"))
            return (container.getIncDate().getYear() <= 123)
                    ? ((container.getIncDate().getYear() == 122) ? fullTransshipmentIntervals()
                            : (container.getIncDate().getYear() == 123) ? fullTransshipmentIntervals2023()
                                    : fullTransshipmentIntervals2021())
                    : fullTransshipmentIntervals2024();

        else if (container.isReef() && container.getFullOrEmpty().equals("Full")
                && !container.getInvoiceCategory().equals("Transshipment"))
            return (container.getIncDate().getYear() <= 123)
                    ? ((container.getIncDate().getYear() == 122) ? fullReeferDirectIntervals()
                            : (container.getIncDate().getYear() == 123) ? fullReeferDirectIntervals2023()
                                    : fullReeferDirectIntervals2021())
                    : fullReeferDirectIntervals2024();

        else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Import"))
            return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 122)
                    ? fullImportIntervals()
                    : (container.getIncDate().getYear() == 123) ? fullImportIntervals2023() : fullImportIntervals2021())
                    : fullImportIntervals2024();

        else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Export"))
            return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 122)
                    ? fullExportIntervals()
                    : (container.getIncDate().getYear() == 123) ? fullExportIntervals2023() : fullExportIntervals2021())
                    : fullExportIntervals2024();
        return null;
    }
}
