package com.ta.csc.helper;

import com.ta.csc.domain.Container;
import com.ta.csc.domain.Interval;
import org.decimal4j.util.DoubleRounder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.ta.csc.helper.Constants.*;


public class Fixtures {

    //Get the first and last date of each interval
    public static List<Map<String,Date>> getMinAndMAxValueDateOfEachInterval(Container container,List<Interval> intervals) {
        List<Map<String,Date>> list = new ArrayList<>();
        List<Integer> numberOfDays =numberOfDaysForEachInterval(container,intervals);
        Map<String, Date> map = new HashMap<>();
        map.put("minDate", container.getIncDate());
        map.put("maxDate",convertToDate(convertToLocalDate(container.getIncDate()).plusDays(numberOfDays.get(0)-1)));
        list.add(map);
        for(int i=1;i<numberOfDays.size();i++) {
            Map<String, Date> stringDateMap = new HashMap<>();
            stringDateMap.put("minDate",convertToDate(convertToLocalDate(list.get(i-1).get("maxDate")).plusDays(1)));
            stringDateMap.put("maxDate",convertToDate(convertToLocalDate(stringDateMap.get("minDate")).plusDays(numberOfDays.get(i)-1)));
            list.add(stringDateMap);
        }
        return list;
    }

    // Get number of days in each intervals
    public static List<Integer> numberOfDaysForEachInterval(Container container,List<Interval> intervals) {
        List<Integer> days = new ArrayList<>();
        int invoiceStorageDuration = container.getInvoiceStorageDuration();
        int remainingDays = invoiceStorageDuration;
        for (Interval interval :intervals) {
            if(remainingDays <= 0) {
                days.add(0);
            }
            else if (invoiceStorageDuration <= interval.getMaxValue()) {
                days.add(remainingDays);
                remainingDays = 0;
            }
            else {
                remainingDays -= (interval.getMaxValue()-interval.getMinValue()+1);
                days.add(interval.getMaxValue()-interval.getMinValue()+1);
            }
                  }
        return days;
    }
    // Get the amount of storage in each interval in a month
    public static List<Double> storageOfEachIntervalInMonth(Container container, List<Interval> intervals,String month,String year) throws ParseException {
        List <Double> prices = new ArrayList<>();
        boolean isFullReeferDirectContainer = container.isReef() && !container.getInvoiceCategory().equals("Transshipment") && container.getFullOrEmpty().equals("Full");
        boolean isEmptyDirectContainer = !container.getInvoiceCategory().equals("Transshipment") && container.getFullOrEmpty().equals("Empty");

        int coefficient = 1;
        if(container.getLength().equals("40") && !isFullReeferDirectContainer && !isEmptyDirectContainer) coefficient = 2;
        else  if(container.getLength().equals("45") && !isFullReeferDirectContainer && !isEmptyDirectContainer) coefficient = 3;
        for (int i=0; i<numberOfDaysForEachIntervalInMonth(container,intervals,month,year).size();i++) {
            prices.add(DoubleRounder.round(intervals.get(i).getPrice()*coefficient*numberOfDaysForEachIntervalInMonth(container,intervals,month,year).get(i),2));
        }

        return prices;
    }

    // Get the amount of storage in each interval in a month
    public static List<Double> storageOfEachIntervalInMonthWithFreeDays(Container container, List<Interval> intervals,String month,String year) throws ParseException {
        List <Double> prices = new ArrayList<>();
        boolean isFullReeferDirectContainer = container.isReef() && !container.getInvoiceCategory().equals("Transshipment") && container.getFullOrEmpty().equals("Full");
        boolean isEmptyDirectContainer = !container.getInvoiceCategory().equals("Transshipment") && container.getFullOrEmpty().equals("Empty");

        int coefficient = 1;
        if(container.getLength().equals("40") && !isFullReeferDirectContainer && !isEmptyDirectContainer) coefficient = 2;
        else  if(container.getLength().equals("45") && !isFullReeferDirectContainer && !isEmptyDirectContainer) coefficient = 3;
        for (int i=1; i<numberOfDaysForEachIntervalInMonth(container,intervals,month,year).size();i++) {
            prices.add(DoubleRounder.round(intervals.get(i).getPrice()*coefficient*numberOfDaysForEachIntervalInMonth(container,intervals,month,year).get(i),2));
        }

        return prices;
    }
    // Get number of days for each interval in a certain month
    public static List<Integer> numberOfDaysForEachIntervalInMonth(Container container,List<Interval> intervals,String month,String year) throws ParseException {
        List<Integer> numbers = new ArrayList<>();
        int count = 0;
        String strStartDate = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month)-1).get("minValue")+"/"+month+"/"+year;
        String strEndDate = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month)-1).get("maxValue")+"/"+month+"/"+year;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = simpleDateFormat.parse(strStartDate);
        Date endDate = simpleDateFormat.parse(strEndDate);
        for(Map<String,Date> map: getMinAndMAxValueDateOfEachInterval(container,intervals)) {
            for (LocalDate date = convertToLocalDate(startDate); !date.isAfter(convertToLocalDate(endDate)); date = date.plusDays(1)) {
                if(!date.isBefore(convertToLocalDate(map.get("minDate"))) && !date.isAfter(convertToLocalDate(map.get("maxDate")))) {
                    count++;
                }
            }
            numbers.add(count);
            count = 0;
        }
        return numbers;
    }

    //Get the quantity of teus in each interval in a certain month
    public static List<Integer> quantityOfTeusForEachIntervalInMonth(List<Container> containers,List<Interval> intervals,String month,String year) throws ParseException {
        List<Integer> quantities = new ArrayList<>();
        for(int i=0;i<intervals.size();i++) quantities.add(0);
        for(int i=0;i<quantities.size();i++) {
            int numberOfTeus = 0;
            for(Container container :containers) {
                numberOfTeus += numberOfDaysForEachIntervalInMonth(container,intervals,month,year).get(i)*getContainerTeus(container);
            }
            quantities.set(i,(quantities.get(i)+(numberOfTeus)));
        }
        return quantities;
    }

    //Get the quantity of units in each interval in a certain month
    public static List<Integer> quantityOfUnitsForEachIntervalInMonth(List<Container> containers,List<Interval> intervals,String month,String year) throws ParseException {
        List<Integer> quantities = new ArrayList<>();
        for(int i=0;i<intervals.size();i++) quantities.add(0);
        for(int i=0;i<quantities.size();i++) {
            int numberOfTeus = 0;
            for(Container container :containers) {
                numberOfTeus += numberOfDaysForEachIntervalInMonth(container,intervals,month,year).get(i);;
            }
            quantities.set(i,(quantities.get(i)+(numberOfTeus)));
        }
        return quantities;
    }


    public static int getContainerTeus(Container container) {
        switch (container.getLength()) {
            case "45": return 3;
            case "40": return 2;
            default: return 1;
        }
    }
    public static List<Integer> getNumberOfEmptyContainersTeus(String month,String year,List<Container> containers) throws ParseException {
        List<Integer> numbers = new ArrayList<>();
        List<Container> hlcEmptyContainers = containers.stream().filter(container -> container.getFullOrEmpty().equals("Empty")).collect(Collectors.toList());
        String firstDay = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month)-1).get("minValue");
        String lastDay = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month)-1).get("maxValue");
        String strFirstDate = firstDay+"/"+month+"/"+year;
        String strLastDate = lastDay+"/"+month+"/"+year;
        Date firstDate = new SimpleDateFormat("dd/MM/yyyy").parse(strFirstDate);
        Date lastDate = new SimpleDateFormat("dd/MM/yyyy").parse(strLastDate);
        LocalDate startDate = convertToLocalDate(firstDate);
        LocalDate endDate = convertToLocalDate(lastDate);
        int number = 0;
        for(LocalDate date = startDate; !date.isAfter(endDate);date = date.plusDays(1)) {
            for(Container container: hlcEmptyContainers) {
                if(isDateBetweenTwoDates(convertToDate(date),container.getIncDate(),container.getOutDate()))
                    number += getContainerTeus(container);
            }
            numbers.add(number);
            number =0;
        }

        return numbers;
    }

    public static List<Interval> surchargeIntervals(List<Interval> intervals) {
        for(int i=0;i<intervals.size();i++) {
            if(intervals.get(i).getPrice() == 0) {
                intervals.get(i).setPrice(intervals.get(i+1).getPrice());
            }
        }
        return intervals;
    }

    public static boolean isSpecialShippingLine(Container container) {
        List<String> specialShippingLines = specialStandardShippingLine();
        boolean isSpecial = false;
        for(String specialLine :specialShippingLines) {
            if(container.getShippingLine().equals(specialLine)) {
                isSpecial = true;
                break;
            }
        }
        return isSpecial;
    }

    public static boolean isSpecialShippingLine(String shippingLine) {
        List<String> specialShippingLines = specialStandardShippingLine();
        boolean isSpecial = false;
        for(String specialLine :specialShippingLines) {
            if(shippingLine.equals(specialLine)) {
                isSpecial = true;
                break;
            }
        }
        return isSpecial;
    }

    public static boolean hasShippingLineFreePool(String shippingLine) {
        List<String> shippingLinesWithFreePool = shippinginesHavingFreePool();
        boolean hasFreePool = false;
        for(String sl :shippingLinesWithFreePool) {
            if(shippingLine.equals(sl)) {
                hasFreePool = true;
                break;
            }
        }
        return hasFreePool;
    }

    public static boolean isDateBetweenTwoDates(Date date,Date firstDate,Date lastDate) {
        return firstDate.compareTo(date) * date.compareTo(lastDate) >= 0;
    }


    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date convertToDate(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public static String createCellSumFormula(String column,int firstRow,int lastRow) {
        StringBuilder sequenceBuilder = new StringBuilder();
        for (int i=firstRow; i<= lastRow;i++) {
            sequenceBuilder.append(column).append(i + 1).append(":");
        }
        sequenceBuilder.deleteCharAt(sequenceBuilder.length()-1);
        return "SUM("+sequenceBuilder+")";
    }
    public static boolean dateIsIncludedInMonth(Date date,String month,String year) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fistStrDate = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month)-1).get("minValue")+"/"+month+"/"+year;
        String lastStrDate = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month)-1).get("maxValue")+"/"+month+"/"+year;
        Date firstDate = simpleDateFormat.parse(fistStrDate);
        Date lastDate = simpleDateFormat.parse(lastStrDate);
        return isDateBetweenTwoDates(date,firstDate,lastDate);
    }

    public static String rank(int i) {
        String[] ranks = {"first","second","third","fourth","fifth","sixth","seventh","eigth"};
        return ranks[i];
    }

}
