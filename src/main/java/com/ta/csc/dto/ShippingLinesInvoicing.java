package com.ta.csc.dto;

import com.ta.csc.domain.Container;
import com.ta.csc.domain.Interval;
import com.ta.csc.intervals.*;
import com.ta.csc.service.HLCContainerService;
import org.decimal4j.util.DoubleRounder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import com.ta.csc.helper.Fixtures.*;

import static com.ta.csc.helper.Constants.getMinAndMaxDaysOfMonth;
import static com.ta.csc.helper.Fixtures.*;
import static com.ta.csc.intervals.ARKContainerIntervals.fullExportIntervals;
import static com.ta.csc.intervals.ARKContainerIntervals.fullImportIntervals;
import static com.ta.csc.intervals.ARKContainerIntervals.fullReeferDirectIntervals;
import static com.ta.csc.intervals.ARKContainerIntervals.fullTransshipmentIntervals;
import static com.ta.csc.intervals.HLCContainerIntervals.*;

public class ShippingLinesInvoicing {

        public String shippingLine;
        public String containerNumber;
        public String type;
        public String length;
        public String invoiceCategory;
        public String fullOrEmpty;
        public boolean reef;
        public boolean imdg;
        public boolean oog;
        public boolean dmg;
        public Date incDate;
        public Date outDate;
        public int invoiceStorageDuration;
        public double reeferSurcharge;
        public double imdgSurcharge;
        public double oogSurcharge;
        public double dmgSurcharge;
        public double tankSurcharge;
        public double totalSurcharge;
        public List<Double> storageOfEachIntervalInMonth;
        public double totalStorageOfMonth;
        public boolean stillInYard = false;

        public static FileWriter fw = null;
        public static BufferedWriter bw = null;

        public static double hazardousPrice = 0;
        public static double hazardousPrice_2023 = 0;
        public static double hazardousImportTotal = 0;
        public static double hazardousImportTotal_2023 = 0;
        public static double hazardousImportTotal_2024 = 0;
        public static int hazardousImportQuantity = 0;
        public static int hazardousImportQuantity_2023 = 0;
        public static int hazardousImportQuantity_2024 = 0;

        public static double hazardousExportTotal = 0;
        public static double hazardousExportTotal_2023 = 0;
        public static double hazardousExportTotal_2024 = 0;
        public static int hazardousExportQuantity = 0;
        public static int hazardousExportQuantity_2023 = 0;
        public static int hazardousExportQuantity_2024 = 0;

        public static double hazardousTranssipmentTotal = 0;
        public static double hazardousTranssipmentTotal_2023 = 0;
        public static double hazardousTranssipmentTotal_2024 = 0;
        public static int hazardousTransshipmentQuantity = 0;
        public static int hazardousTransshipmentQuantity_2023 = 0;
        public static int hazardousTransshipmentQuantity_2024 = 0;

        public static double reeferConnectionTransshipmentPrice = 0;
        public static double reeferConnectionTransshipmentPrice_2023 = 0;
        public static double reeferConnectionTransshipmentPrice_2024 = 0;
        public static double reeferConnectionTransshipmentTotal = 0;
        public static double reeferConnectionTransshipmentTotal_2023 = 0;
        public static double reeferConnectionTransshipmentTotal_2024 = 0;
        public static int reeferConnectionTransshipmentQuantity = 0;
        public static int reeferConnectionTransshipmentQuantity_2023 = 0;
        public static int reeferConnectionTransshipmentQuantity_2024 = 0;

        public static double reeferConnectionImportExportPrice = 0;
        public static double reeferConnectionImportExportPrice_2023 = 0;
        public static double reeferConnectionImportExportPrice_2024 = 0;
        public static double reeferConnectionImportTotal = 0;
        public static double reeferConnectionImportTotal_2023 = 0;
        public static double reeferConnectionImportTotal_2024 = 0;
        public static int reeferConnectionImportQuantity = 0;
        public static int reeferConnectionImportQuantity_2023 = 0;
        public static int reeferConnectionImportQuantity_2024 = 0;

        public static double reeferConnectionExportTotal = 0;
        public static double reeferConnectionExportTotal_2023 = 0;
        public static double reeferConnectionExportTotal_2024 = 0;
        public static int reeferConnectionExportQuantity = 0;
        public static int reeferConnectionExportQuantity_2023 = 0;
        public static int reeferConnectionExportQuantity_2024 = 0;

        public static double storageOfDamagedContainersTotal = 0;
        public static double storageOfDamagedContainersTotal_2023 = 0;
        public static double storageOfDamagedContainersTotal_2024 = 0;
        public static int storageOfDamagedContainersQuantity = 0;
        public static int storageOfDamagedContainersQuantity_2023 = 0;
        public static int storageOfDamagedContainersQuantity_2024 = 0;

        public static double[] storageFullExportInervalsTotal = { 0, 0, 0 };
        public static double[] storageFullExportInervalsTotal_2023 = { 0, 0, 0 };
        public static double[] storageFullExportInervalsTotal_2024 = { 0, 0, 0 };
        public static int[] storageFullExportInervalsQuantities = { 0, 0, 0 };
        public static int[] storageFullExportInervalsQuantities_2023 = { 0, 0, 0 };
        public static int[] storageFullExportInervalsQuantities_2024 = { 0, 0, 0 };

        public static double[] storageFullImportInervalsTotal = { 0, 0, 0 };
        public static double[] storageFullImportInervalsTotal_2023 = { 0, 0, 0 };
        public static double[] storageFullImportInervalsTotal_2024 = { 0, 0, 0 };
        public static int[] storageFullImportInervalsQuantities = { 0, 0, 0 };
        public static int[] storageFullImportInervalsQuantities_2023 = { 0, 0, 0 };
        public static int[] storageFullImportInervalsQuantities_2024 = { 0, 0, 0 };

        public static double[] storageFullTransshipmentInervalsTotal = { 0, 0, 0, 0, 0, 0 };
        public static double[] storageFullTransshipmentInervalsTotal_2023 = { 0, 0, 0, 0, 0, 0 };
        public static double[] storageFullTransshipmentInervalsTotal_2024 = { 0, 0, 0, 0, 0, 0 };
        public static int[] storageFullTransshipmentInervalsQuantities = { 0, 0, 0, 0, 0, 0 };
        public static int[] storageFullTransshipmentInervalsQuantities_2023 = { 0, 0, 0, 0, 0, 0 };
        public static int[] storageFullTransshipmentInervalsQuantities_2024 = { 0, 0, 0, 0, 0, 0 };

        public static double[] storageFullReeferTransshipmentInervalsTotal = { 0, 0, 0, 0, 0, 0 };
        public static double[] storageFullReeferTransshipmentInervalsTotal_2023 = { 0, 0, 0, 0, 0, 0 };
        public static double[] storageFullReeferTransshipmentInervalsTotal_2024 = { 0, 0, 0, 0, 0, 0 };
        public static int[] storageFullReeferTransshipmentInervalsQuantities = { 0, 0, 0, 0, 0, 0 };
        public static int[] storageFullReeferTransshipmentInervalsQuantities_2023 = { 0, 0, 0, 0, 0, 0 };
        public static int[] storageFullReeferTransshipmentInervalsQuantities_2024 = { 0, 0, 0, 0, 0, 0 };

        public static double[] storageFullReeferExportInervalsTotal = { 0, 0, 0, 0 };
        public static double[] storageFullReeferExportInervalsTotal_2023 = { 0, 0, 0, 0 };
        public static double[] storageFullReeferExportInervalsTotal_2024 = { 0, 0, 0, 0 };
        public static int[] storageFullReeferExportInervalsQuantities = { 0, 0, 0, 0 };
        public static int[] storageFullReeferExportInervalsQuantities_2023 = { 0, 0, 0, 0 };
        public static int[] storageFullReeferExportInervalsQuantities_2024 = { 0, 0, 0, 0 };

        public static double[] storageFullReeferImportInervalsTotal = { 0, 0, 0, 0 };
        public static double[] storageFullReeferImportInervalsTotal_2023 = { 0, 0, 0, 0 };
        public static double[] storageFullReeferImportInervalsTotal_2024 = { 0, 0, 0, 0 };
        public static int[] storageFullReeferImportInervalsQuantities = { 0, 0, 0, 0 };
        public static int[] storageFullReeferImportInervalsQuantities_2023 = { 0, 0, 0, 0 };
        public static int[] storageFullReeferImportInervalsQuantities_2024 = { 0, 0, 0, 0 };

        public static double storageOfOOGContainersTotal = 0;
        public static double storageOfOOGContainersTotal_2023 = 0;
        public static double storageOfOOGContainersTotal_2024 = 0;
        public static int storageOfOOGContainersQuantity = 0;
        public static int storageOfOOGContainersQuantity_2023 = 0;
        public static int storageOfOOGContainersQuantity_2024 = 0;

        public static double storageOfTankContainersTotal = 0;
        public static double storageOfTankContainersTotal_2023 = 0;
        public static double storageOfTankContainersTotal_2024 = 0;
        public static int storageOfTankContainersQuantity = 0;
        public static int storageOfTankContainersQuantity_2023 = 0;
        public static int storageOfTankContainersQuantity_2024 = 0;

        public static double[] emptyDepotContainersTotal = { 0, 0, 0, 0 };
        public static double[] emptyDepotContainersTotal_2023 = { 0, 0, 0, 0 };
        public static double[] emptyDepotContainersTotal_2024 = { 0, 0, 0, 0 };
        public static int[] emptyDepotContainersQuantities = { 0, 0, 0, 0 };
        public static int[] emptyDepotContainersQuantities_2023 = { 0, 0, 0, 0 };
        public static int[] emptyDepotContainersQuantities_2024 = { 0, 0, 0, 0 };

        public static double[] emptyExportContainersTotal = { 0, 0, 0, 0 };
        public static double[] emptyExportContainersTotal_2023 = { 0, 0, 0, 0 };
        public static double[] emptyExportContainersTotal_2024 = { 0, 0, 0, 0 };
        public static int[] emptyExportContainersQuantities = { 0, 0, 0, 0 };
        public static int[] emptyExportContainersQuantities_2023 = { 0, 0, 0, 0 };
        public static int[] emptyExportContainersQuantities_2024 = { 0, 0, 0, 0 };

        public static double[] emptyImportContainersTotal = { 0, 0, 0, 0 };
        public static double[] emptyImportContainersTotal_2023 = { 0, 0, 0, 0 };
        public static double[] emptyImportContainersTotal_2024 = { 0, 0, 0, 0 };
        public static int[] emptyImportContainersQuantities = { 0, 0, 0, 0 };
        public static int[] emptyImportContainersQuantities_2023 = { 0, 0, 0, 0 };
        public static int[] emptyImportContainersQuantities_2024 = { 0, 0, 0, 0 };

        public static double[] emptyTransshipmentContainersTotal = { 0, 0, 0, 0, 0, 0 };
        public static double[] emptyTransshipmentContainersTotal_2023 = { 0, 0, 0, 0, 0, 0 };
        public static double[] emptyTransshipmentContainersTotal_2024 = { 0, 0, 0, 0, 0, 0 };
        public static int[] emptyTransshipmentContainersQuantities = { 0, 0, 0, 0, 0, 0 };
        public static int[] emptyTransshipmentContainersQuantities_2023 = { 0, 0, 0, 0, 0, 0 };
        public static int[] emptyTransshipmentContainersQuantities_2024 = { 0, 0, 0, 0, 0, 0 };

        public static List<ContainerStorageDetailsDTO> shippingLinesInvoicingList = new ArrayList<>();

        public static DecimalFormat df = new DecimalFormat("#.00");

        @Override
        public String toString() {
                return "ShippingLinesInvoicing{" +
                                "shippingLine='" + shippingLine + '\'' +
                                ", containerNumber='" + containerNumber + '\'' +
                                ", type='" + type + '\'' +
                                ", length='" + length + '\'' +
                                ", invoiceCategory='" + invoiceCategory + '\'' +
                                ", fullOrEmpty='" + fullOrEmpty + '\'' +
                                ", reef=" + reef +
                                ", imdg=" + imdg +
                                ", oog=" + oog +
                                ", dmg=" + dmg +
                                ", incDate=" + incDate +
                                ", outDate=" + outDate +
                                ", invoiceStorageDuration=" + invoiceStorageDuration +
                                ", reeferSurcharge=" + reeferSurcharge +
                                ", imdgSurcharge=" + imdgSurcharge +
                                ", oogSurcharge=" + oogSurcharge +
                                ", dmgSurcharge=" + dmgSurcharge +
                                ", tankSurcharge=" + tankSurcharge +
                                ", totalSurcharge=" + totalSurcharge +
                                ", storageOfEachIntervalInMonth=" + storageOfEachIntervalInMonth +
                                ", totalStorageOfMonth=" + totalStorageOfMonth +
                                '}';
        }

        public static void setThisShipingLineAllDetailsToFile(String content, String shippingLine, String month,
                        String year) throws IOException {
                if (fw == null)
                        fw = new FileWriter(
                                        "C:\\Users\\fayssal.ourezzouq\\Desktop\\storage reports\\July-2022\\factures\\"
                                                        + shippingLine + "_Monthly_storage_" + month + "-" + year
                                                        + ".csv",
                                        StandardCharsets.ISO_8859_1);
                if (bw == null)
                        bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();

        }

        public static Container convertToContainer(ContainerStorageDetailsDTO containerStorageDetailsDTO) {
                return new Container(null,
                                containerStorageDetailsDTO.shippingLine,
                                containerStorageDetailsDTO.containerNumber,
                                containerStorageDetailsDTO.type,
                                containerStorageDetailsDTO.length,
                                containerStorageDetailsDTO.invoiceCategory,
                                containerStorageDetailsDTO.fullOrEmpty,
                                containerStorageDetailsDTO.reef,
                                containerStorageDetailsDTO.imdg,
                                containerStorageDetailsDTO.oog,
                                containerStorageDetailsDTO.dmg,
                                containerStorageDetailsDTO.incDate,
                                containerStorageDetailsDTO.outDate,
                                containerStorageDetailsDTO.invoiceStorageDuration);
        }

        public static List<Integer> numberOfDaysForEachIntervalInMonth(Container container, List<Interval> intervals,
                        String month, String year) throws ParseException {
                List<Integer> numbers = new ArrayList<>();
                int count = 0;
                String strStartDate = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month) - 1).get("minValue")
                                + "/"
                                + month + "/" + year;
                String strEndDate = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month) - 1).get("maxValue") + "/"
                                + month
                                + "/" + year;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = simpleDateFormat.parse(strStartDate);
                Date endDate = simpleDateFormat.parse(strEndDate);
                for (Map<String, Date> map : getMinAndMAxValueDateOfEachInterval(container, intervals)) {
                        for (LocalDate date = convertToLocalDate(startDate); !date
                                        .isAfter(convertToLocalDate(endDate)); date = date
                                                        .plusDays(1)) {
                                if (!date.isBefore(convertToLocalDate(map.get("minDate")))
                                                && !date.isAfter(convertToLocalDate(map.get("maxDate")))) {
                                        count++;
                                }
                        }
                        numbers.add(count);
                        count = 0;
                }
                return numbers;
        }

        public static List<Map<String, Date>> getMinAndMAxValueDateOfEachInterval(Container container,
                        List<Interval> intervals) {
                List<Map<String, Date>> list = new ArrayList<>();
                List<Integer> numberOfDays = numberOfDaysForEachInterval(container, intervals);
                Map<String, Date> map = new HashMap<>();
                map.put("minDate", container.getIncDate());
                map.put("maxDate", convertToDate(
                                convertToLocalDate(container.getIncDate()).plusDays(numberOfDays.get(0) - 1)));
                list.add(map);
                for (int i = 1; i < numberOfDays.size(); i++) {
                        Map<String, Date> stringDateMap = new HashMap<>();
                        stringDateMap.put("minDate",
                                        convertToDate(convertToLocalDate(list.get(i - 1).get("maxDate")).plusDays(1)));
                        stringDateMap.put("maxDate",
                                        convertToDate(convertToLocalDate(stringDateMap.get("minDate"))
                                                        .plusDays(numberOfDays.get(i) - 1)));
                        list.add(stringDateMap);
                }
                return list;
        }

        public static List<Integer> numberOfDaysForEachInterval(Container container, List<Interval> intervals) {
                List<Integer> days = new ArrayList<>();
                int invoiceStorageDuration = container.getInvoiceStorageDuration();
                int remainingDays = invoiceStorageDuration;
                for (Interval interval : intervals) {
                        if (remainingDays <= 0) {
                                days.add(0);
                        } else if (invoiceStorageDuration <= interval.getMaxValue()) {
                                days.add(remainingDays);
                                remainingDays = 0;
                        } else {
                                remainingDays -= (interval.getMaxValue() - interval.getMinValue() + 1);
                                days.add(interval.getMaxValue() - interval.getMinValue() + 1);
                        }
                }
                return days;
        }

        public static List<Map<String, String>> getMinAndMaxDaysOfMonth(String year) {
                List<Map<String, String>> list = new ArrayList<>();
                Map<String, String> map1 = new HashMap<>();
                map1.put("minValue", "1");
                map1.put("maxValue", "31");
                list.add(map1);
                Map<String, String> map2 = new HashMap<>();
                String februaryMaxValue = Integer.parseInt(year) % 4 == 0 ? "29" : "28";
                map2.put("minValue", "1");
                map2.put("maxValue", februaryMaxValue);
                list.add(map2);
                Map<String, String> map3 = new HashMap<>();
                map3.put("minValue", "1");
                map3.put("maxValue", "31");
                list.add(map3);
                Map<String, String> map4 = new HashMap<>();
                map4.put("minValue", "1");
                map4.put("maxValue", "30");
                list.add(map4);
                Map<String, String> map5 = new HashMap<>();
                map5.put("minValue", "1");
                map5.put("maxValue", "31");
                list.add(map5);
                Map<String, String> map6 = new HashMap<>();
                map6.put("minValue", "1");
                map6.put("maxValue", "30");
                list.add(map6);
                Map<String, String> map7 = new HashMap<>();
                map7.put("minValue", "1");
                map7.put("maxValue", "31");
                list.add(map7);
                Map<String, String> map8 = new HashMap<>();
                map8.put("minValue", "1");
                map8.put("maxValue", "31");
                list.add(map8);
                Map<String, String> map9 = new HashMap<>();
                map9.put("minValue", "1");
                map9.put("maxValue", "30");
                list.add(map9);
                Map<String, String> map10 = new HashMap<>();
                map10.put("minValue", "1");
                map10.put("maxValue", "31");
                list.add(map10);
                Map<String, String> map11 = new HashMap<>();
                map11.put("minValue", "1");
                map11.put("maxValue", "30");
                list.add(map11);
                Map<String, String> map12 = new HashMap<>();
                map12.put("minValue", "1");
                map12.put("maxValue", "31");
                list.add(map12);
                return list;
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

        public static int getContainerTeus(Container container) {
                switch (container.getLength()) {
                        case "45":
                                return 3;
                        case "40":
                                return 2;
                        default:
                                return 1;
                }
        }

        public static List<Interval> getContainerIntervals(Container container) {
                if (container.isReef() && container.getFullOrEmpty().equals("Full")
                                && container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? HLCContainerIntervals.fullReeferTransshipmentIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? HLCContainerIntervals
                                                                                        .fullReeferTransshipmentIntervals2023()
                                                                        : HLCContainerIntervals
                                                                                        .fullReeferTransshipmentIntervals2021())
                                        : HLCContainerIntervals.fullReeferTransshipmentIntervals2024();

                else if (container.getFullOrEmpty().equals("Full")
                                && container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? HLCContainerIntervals.fullTransshipmentIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? HLCContainerIntervals
                                                                                        .fullTransshipmentIntervals2023()
                                                                        : HLCContainerIntervals
                                                                                        .fullTransshipmentIntervals2021())
                                        : HLCContainerIntervals.fullTransshipmentIntervals2024();

                else if (container.isReef() && container.getFullOrEmpty().equals("Full")
                                && !container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? HLCContainerIntervals.fullReeferDirectIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? HLCContainerIntervals
                                                                                        .fullReeferDirectIntervals2023()
                                                                        : HLCContainerIntervals
                                                                                        .fullReeferDirectIntervals2021())
                                        : HLCContainerIntervals.fullReeferDirectIntervals2024();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Import"))
                        return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 122)
                                        ? HLCContainerIntervals.fullImportIntervals()
                                        : (container.getIncDate().getYear() == 123)
                                                        ? HLCContainerIntervals.fullImportIntervals2023()
                                                        : HLCContainerIntervals.fullImportIntervals2021())
                                        : HLCContainerIntervals.fullImportIntervals2024();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Export"))
                        return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 122)
                                        ? HLCContainerIntervals.fullExportIntervals()
                                        : (container.getIncDate().getYear() == 123)
                                                        ? HLCContainerIntervals.fullExportIntervals2023()
                                                        : HLCContainerIntervals.fullExportIntervals2021())
                                        : HLCContainerIntervals.fullExportIntervals2024();
                return null;
        }

        public static List<Interval> getARKContainerIntervals(Container container) {
                if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() == 121)
                                        ? ARKContainerIntervals.fullTransshipmentIntervals2021()
                                        : ARKContainerIntervals.fullTransshipmentIntervals();

                else if (container.isReef() && container.getFullOrEmpty().equals("Full")
                                && !container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ARKContainerIntervals.fullReeferDirectIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ARKContainerIntervals
                                                                                        .fullReeferDirectIntervals2023()
                                                                        : ARKContainerIntervals
                                                                                        .fullReeferDirectIntervals2021())
                                        : ARKContainerIntervals.fullReeferDirectIntervals2024();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Import"))
                        return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 122)
                                        ? ARKContainerIntervals.fullImportIntervals()
                                        : (container.getIncDate().getYear() == 123)
                                                        ? ARKContainerIntervals.fullImportIntervals2023()
                                                        : ARKContainerIntervals.fullImportIntervals2021())
                                        : ARKContainerIntervals.fullImportIntervals2024();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Export"))
                        return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 122)
                                        ? ARKContainerIntervals.fullExportIntervals()
                                        : (container.getIncDate().getYear() == 123)
                                                        ? ARKContainerIntervals.fullExportIntervals2023()
                                                        : ARKContainerIntervals.fullExportIntervals2021())
                                        : ARKContainerIntervals.fullExportIntervals2024();
                return null;
        }

        public static List<Interval> getYMLContainerIntervals(Container container) {
                if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() == 121)
                                        ? YMLContainerIntervals.fullTransshipmentIntervals2021()
                                        : YMLContainerIntervals.fullTransshipmentIntervals();

                else if (container.isReef() && container.getFullOrEmpty().equals("Full")
                                && !container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 121)
                                                        ? YMLContainerIntervals.fullReeferDirectIntervals2021()
                                                        : (container.getIncDate().getYear() == 122)
                                                                        ? YMLContainerIntervals
                                                                                        .fullReeferDirectIntervals()
                                                                        : YMLContainerIntervals
                                                                                        .fullReeferDirectIntervals2023())
                                        : YMLContainerIntervals.fullReeferDirectIntervals2024();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Import"))
                        return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 121)
                                        ? YMLContainerIntervals.fullImportIntervals2021()
                                        : (container.getIncDate().getYear() == 122)
                                                        ? YMLContainerIntervals.fullImportIntervals()
                                                        : YMLContainerIntervals.fullImportIntervals2023())
                                        : YMLContainerIntervals.fullImportIntervals2024();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Export"))
                        return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 121)
                                        ? YMLContainerIntervals.fullExportIntervals2021()
                                        : (container.getIncDate().getYear() == 122)
                                                        ? YMLContainerIntervals.fullExportIntervals()
                                                        : YMLContainerIntervals.fullExportIntervals2023())
                                        : YMLContainerIntervals.fullExportIntervals2024();
                return null;
        }

        public static List<Interval> getONEContainerIntervals(Container container) {
                if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() == 121)
                                        ? ONEContainerIntervals.fullTransshipmentIntervals2021()
                                        : ONEContainerIntervals.fullTransshipmentIntervals();

                else if (container.isReef() && container.getFullOrEmpty().equals("Full")
                                && !container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ONEContainerIntervals.fullReeferDirectIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ONEContainerIntervals
                                                                                        .fullReeferDirectIntervals2023()
                                                                        : ONEContainerIntervals
                                                                                        .fullReeferDirectIntervals2021())
                                        : ONEContainerIntervals.fullReeferDirectIntervals2024();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Import"))
                        return (container.getIncDate().getYear() <= 123) ? ((container.getIncDate().getYear() == 122)
                                        ? ONEContainerIntervals.fullImportIntervals()
                                        : (container.getIncDate().getYear() == 123)
                                                        ? ONEContainerIntervals.fullImportIntervals2023()
                                                        : ONEContainerIntervals.fullImportIntervals2021())
                                        : ONEContainerIntervals.fullImportIntervals2024();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Export"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ONEContainerIntervals.fullIExportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ONEContainerIntervals
                                                                                        .fullIExportIntervals2023()
                                                                        : ONEContainerIntervals
                                                                                        .fullIExportIntervals2021())
                                        : ONEContainerIntervals.fullIExportIntervals2024();
                return null;
        }

        public static List<Interval> getStandardContainerIntervals(Container container) {
                if (container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() <= 122)
                                        ? StandardContainerIntervals.transshipmentIntervals()
                                        : StandardContainerIntervals.transshipmentIntervals2021();

                else if (container.isReef() && container.getFullOrEmpty().equals("Full")
                                && !container.getInvoiceCategory().equals("Transshipment"))
                        return (container.getIncDate().getYear() == 122)
                                        ? StandardContainerIntervals.fullReeferDirectIntervals()
                                        : (container.getIncDate().getYear() == 123)
                                                        ? StandardContainerIntervals.fullReeferDirectIntervals2023()
                                                        : (container.getIncDate().getYear() == 124)
                                                                        ? StandardContainerIntervals
                                                                                        .fullReeferDirectIntervals2024()
                                                                        : StandardContainerIntervals
                                                                                        .fullReeferDirectIntervals2021();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Import"))
                        return (container.getIncDate().getYear() == 122)
                                        ? StandardContainerIntervals.fullImportIntervals()
                                        : (container.getIncDate().getYear() == 123)
                                                        ? StandardContainerIntervals.fullImportIntervals2023()
                                                        : (container.getIncDate().getYear() == 124)
                                                                        ? StandardContainerIntervals
                                                                                        .fullImportIntervals2024()
                                                                        : StandardContainerIntervals
                                                                                        .fullImportIntervals2021();

                else if (container.getFullOrEmpty().equals("Full") && container.getInvoiceCategory().equals("Export"))
                        return (container.getIncDate().getYear() == 122)
                                        ? StandardContainerIntervals.fullExportIntervals()
                                        : (container.getIncDate().getYear() == 123)
                                                        ? StandardContainerIntervals.fullExportIntervals2023()
                                                        : (container.getIncDate().getYear() == 124)
                                                                        ? StandardContainerIntervals
                                                                                        .fullExportIntervals2024()
                                                                        : StandardContainerIntervals
                                                                                        .fullExportIntervals2021();

                else if (container.getFullOrEmpty().equals("Empty") && !container.getInvoiceCategory().equals("Import"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? StandardContainerIntervals.emptyImportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? StandardContainerIntervals
                                                                                        .emptyImportIntervals2023()
                                                                        : StandardContainerIntervals
                                                                                        .emptyImportIntervals2021())
                                        : StandardContainerIntervals.emptyImportIntervals2024();

                else if (container.getFullOrEmpty().equals("Empty") && !container.getInvoiceCategory().equals("Export"))
                        return (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? StandardContainerIntervals.emptyExportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? StandardContainerIntervals
                                                                                        .emptyExportIntervals2023()
                                                                        : StandardContainerIntervals
                                                                                        .emptyExportIntervals2021())
                                        : StandardContainerIntervals.emptyExportIntervals2024();
                return null;
        }
        // --------------------------------------------------------------------------------------------------------------Do

        public static String HLCSumMonthlyStorageDetails(String month, String year) throws ParseException, IOException {
                fw = null;
                bw = null;
                String content = "";
                hazardousImportTotal = 0;
                hazardousImportTotal_2023 = 0;
                hazardousImportTotal_2024 = 0;
                hazardousImportQuantity = 0;
                hazardousImportQuantity_2023 = 0;
                hazardousImportQuantity_2024 = 0;

                hazardousExportTotal = 0;
                hazardousExportTotal_2023 = 0;
                hazardousExportTotal_2024 = 0;
                hazardousExportQuantity = 0;
                hazardousExportQuantity_2023 = 0;
                hazardousExportQuantity_2024 = 0;

                hazardousTranssipmentTotal = 0;
                hazardousTranssipmentTotal_2023 = 0;
                hazardousTranssipmentTotal_2024 = 0;
                hazardousTransshipmentQuantity = 0;
                hazardousTransshipmentQuantity_2023 = 0;
                hazardousTransshipmentQuantity_2024 = 0;
                hazardousPrice = 0;

                reeferConnectionTransshipmentTotal = 0;
                reeferConnectionTransshipmentTotal_2023 = 0;
                reeferConnectionTransshipmentTotal_2024 = 0;
                reeferConnectionTransshipmentQuantity = 0;
                reeferConnectionTransshipmentQuantity_2023 = 0;
                reeferConnectionTransshipmentQuantity_2024 = 0;
                reeferConnectionTransshipmentPrice = 0;

                reeferConnectionImportTotal = 0;
                reeferConnectionImportTotal_2023 = 0;
                reeferConnectionImportTotal_2024 = 0;
                reeferConnectionImportQuantity = 0;
                reeferConnectionImportQuantity_2023 = 0;
                reeferConnectionImportQuantity_2024 = 0;

                reeferConnectionExportTotal = 0;
                reeferConnectionExportTotal_2023 = 0;
                reeferConnectionExportTotal_2024 = 0;
                reeferConnectionExportQuantity = 0;
                reeferConnectionExportQuantity_2023 = 0;
                reeferConnectionExportQuantity_2024 = 0;
                reeferConnectionImportExportPrice = 0;
                reeferConnectionImportExportPrice_2023 = 0;
                reeferConnectionImportExportPrice_2024 = 0;

                storageOfDamagedContainersTotal = 0;
                storageOfDamagedContainersTotal_2023 = 0;
                storageOfDamagedContainersTotal_2024 = 0;
                storageOfDamagedContainersQuantity = 0;
                storageOfDamagedContainersQuantity_2023 = 0;
                storageOfDamagedContainersQuantity_2024 = 0;

                storageFullExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0 };

                storageFullReeferTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0 };

                storageFullReeferExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullReeferImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageOfOOGContainersTotal = 0;
                storageOfOOGContainersTotal_2023 = 0;
                storageOfOOGContainersTotal_2024 = 0;
                storageOfOOGContainersQuantity = 0;
                storageOfOOGContainersQuantity_2023 = 0;
                storageOfOOGContainersQuantity_2024 = 0;

                storageOfTankContainersTotal = 0;
                storageOfTankContainersTotal_2023 = 0;
                storageOfTankContainersTotal_2024 = 0;
                storageOfTankContainersQuantity = 0;
                storageOfTankContainersQuantity_2023 = 0;
                storageOfTankContainersQuantity_2024 = 0;

                emptyDepotContainersTotal = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersTotal_2023 = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersTotal_2024 = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities = new int[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities_2023 = new int[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities_2024 = new int[] { 0, 0, 0, 0 };

                // emptyExportContainersTotal=0;
                // emptyExportContainersQuantity=0;

                // emptyImportContainersTotal=0;
                // emptyImportContainersQuantity=0;

                emptyTransshipmentContainersTotal = new double[] { 0, 0, 0, 0 };
                emptyTransshipmentContainersTotal_2023 = new double[] { 0, 0, 0, 0 };

                List<Interval> fullExportIntervals = new ArrayList<>();
                List<Interval> fullImportIntervals = new ArrayList<>();
                List<Interval> fullTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferDirectIntervals = new ArrayList<>();
                List<Interval> emptyImportOrExportIntervals = new ArrayList<>();

                // emptyImportOrExportIntervals
                emptyImportOrExportIntervals = StandardContainerIntervals.emptyExportIntervals();

                for (ContainerStorageDetailsDTO shippingLinesInvoicing : shippingLinesInvoicingList) {
                        Container container = convertToContainer(shippingLinesInvoicing);
                        int theYear = container.getIncDate().getYear() - 100;
                        hazardousPrice = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122) ? 22.68 : 23.44)
                                        : 23.61;
                        reeferConnectionTransshipmentPrice = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122) ? 31.23 : 32.28)
                                        : 36.50;
                        reeferConnectionImportExportPrice = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122) ? 35.53 : 36.24)
                                        : 36.96;
                        // fullTransshipmentIntervals
                        fullTransshipmentIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? HLCContainerIntervals.fullTransshipmentIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? HLCContainerIntervals
                                                                                        .fullTransshipmentIntervals2023()
                                                                        : HLCContainerIntervals
                                                                                        .fullTransshipmentIntervals2021())
                                        : HLCContainerIntervals.fullTransshipmentIntervals2024();

                        // fullReeferTransshipmentIntervals
                        fullReeferTransshipmentIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? HLCContainerIntervals.fullReeferTransshipmentIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? HLCContainerIntervals
                                                                                        .fullReeferTransshipmentIntervals2023()
                                                                        : HLCContainerIntervals
                                                                                        .fullReeferTransshipmentIntervals2021())
                                        : HLCContainerIntervals.fullReeferTransshipmentIntervals2024();

                        // fullImportIntervals
                        fullImportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? HLCContainerIntervals.fullImportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? HLCContainerIntervals
                                                                                        .fullImportIntervals2023()
                                                                        : HLCContainerIntervals
                                                                                        .fullImportIntervals2021())
                                        : HLCContainerIntervals.fullImportIntervals2024();

                        // fullExportIntervals
                        fullExportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? HLCContainerIntervals.fullExportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? HLCContainerIntervals
                                                                                        .fullExportIntervals2023()
                                                                        : HLCContainerIntervals
                                                                                        .fullExportIntervals2021())
                                        : HLCContainerIntervals.fullExportIntervals2024();

                        // fullReeferDirectIntervals
                        fullReeferDirectIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? HLCContainerIntervals.fullReeferDirectIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? HLCContainerIntervals
                                                                                        .fullReeferDirectIntervals2023()
                                                                        : HLCContainerIntervals
                                                                                        .fullReeferDirectIntervals2021())
                                        : HLCContainerIntervals.fullReeferDirectIntervals2024();
                        /*
                         * System.err.println(container);
                         * System.out.println(shippingLinesInvoicing);
                         */
                        // Hazardous import Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                if (theYear == 24) {
                                        hazardousImportTotal_2024 += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousImportQuantity_2024 += getContainerTeus(container) * numOfDays;
                                } else if (theYear == 23) {
                                        hazardousImportTotal_2023 += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousImportQuantity_2023 += getContainerTeus(container) * numOfDays;
                                } else {
                                        hazardousImportTotal += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousImportQuantity += getContainerTeus(container) * numOfDays;
                                }

                        }
                        // Hazardous Export Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                if (theYear == 24) {
                                        hazardousExportTotal_2024 += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousExportQuantity_2024 += getContainerTeus(container) * numOfDays;
                                }
                                if (theYear == 23) {
                                        hazardousExportTotal_2023 += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousExportQuantity_2023 += getContainerTeus(container) * numOfDays;
                                } else {
                                        hazardousExportTotal += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousExportQuantity += getContainerTeus(container) * numOfDays;
                                }

                        }
                        // Hazardous Transshipment Surcharge
                        if (shippingLinesInvoicing.imdg
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                if (theYear == 24) {
                                        hazardousTranssipmentTotal_2024 += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousTransshipmentQuantity_2024 += getContainerTeus(container) * numOfDays;
                                } else if (theYear == 23) {
                                        hazardousTranssipmentTotal_2023 += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousTransshipmentQuantity_2023 += getContainerTeus(container) * numOfDays;
                                } else {
                                        hazardousTranssipmentTotal += shippingLinesInvoicing.imdgSurcharge;
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        getContainerIntervals(container), month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        hazardousTransshipmentQuantity += getContainerTeus(container) * numOfDays;
                                }

                        }
                        // reeferConnectionTransshipment & Direct Total surchagre
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        fullReeferTransshipmentIntervals, month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        if (container.isReef()
                                                        && container.getInvoiceCategory().equals("Transshipment")) {
                                                reeferConnectionTransshipmentTotal_2024 += numOfDays
                                                                * reeferConnectionTransshipmentPrice;
                                                reeferConnectionTransshipmentQuantity_2024 += numOfDays;
                                        }
                                        if (container.isReef() && container.getInvoiceCategory().equals("Import")) {
                                                reeferConnectionImportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2024 += numOfDays;
                                        }
                                        if (container.isReef() && container.getInvoiceCategory().equals("Export")) {
                                                reeferConnectionExportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2024 += numOfDays;
                                        }
                                }
                                if (theYear == 23) {
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        fullReeferTransshipmentIntervals, month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        if (container.isReef()
                                                        && container.getInvoiceCategory().equals("Transshipment")) {
                                                reeferConnectionTransshipmentTotal_2023 += numOfDays
                                                                * reeferConnectionTransshipmentPrice;
                                                reeferConnectionTransshipmentQuantity_2023 += numOfDays;
                                        }
                                        if (container.isReef() && container.getInvoiceCategory().equals("Import")) {
                                                reeferConnectionImportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2023 += numOfDays;
                                        }
                                        if (container.isReef() && container.getInvoiceCategory().equals("Export")) {
                                                reeferConnectionExportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2023 += numOfDays;
                                        }
                                } else {
                                        int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                        fullReeferTransshipmentIntervals, month,
                                                        year).stream().mapToInt(Integer::intValue).sum();
                                        if (container.isReef()
                                                        && container.getInvoiceCategory().equals("Transshipment")) {
                                                reeferConnectionTransshipmentTotal += numOfDays
                                                                * reeferConnectionTransshipmentPrice;
                                                reeferConnectionTransshipmentQuantity += numOfDays;
                                        }
                                        if (container.isReef() && container.getInvoiceCategory().equals("Import")) {
                                                reeferConnectionImportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity += numOfDays;
                                        }
                                        if (container.isReef() && container.getInvoiceCategory().equals("Export")) {
                                                reeferConnectionExportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity += numOfDays;
                                        }
                                }

                        }
                        // storageOfDamagedContainers surcharge
                        if (shippingLinesInvoicing.dmg) {
                                storageOfDamagedContainersTotal += shippingLinesInvoicing.dmgSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getContainerIntervals(container), month,
                                                year).stream().mapToInt(Integer::intValue).sum();
                                storageOfDamagedContainersQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // storageOfOOGContainers surchagre
                        if (shippingLinesInvoicing.oog) {
                                storageOfOOGContainersTotal += shippingLinesInvoicing.oogSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getContainerIntervals(container), month,
                                                year).stream().mapToInt(Integer::intValue).sum();
                                storageOfOOGContainersQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // storageOfTankContainers surchagre
                        if (shippingLinesInvoicing.type.equals("TK") || shippingLinesInvoicing.type.equals("TO")) {
                                storageOfTankContainersTotal += shippingLinesInvoicing.tankSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getContainerIntervals(container), month,
                                                year).stream().mapToInt(Integer::intValue).sum();
                                storageOfTankContainersQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // storageFullExportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2024[0] += price;
                                        storageFullExportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2024[1] += price;
                                        storageFullExportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2024[2] += price;
                                        storageFullExportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2023[0] += price;
                                        storageFullExportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2023[1] += price;
                                        storageFullExportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2023[2] += price;
                                        storageFullExportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }

                                else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal[0] += price;
                                        storageFullExportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal[1] += price;
                                        storageFullExportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal[2] += price;
                                        storageFullExportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }
                        }

                        // storageFullImportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2024[0] += price;
                                        storageFullImportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2024[1] += price;
                                        storageFullImportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2024[2] += price;
                                        storageFullImportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2023[0] += price;
                                        storageFullImportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2023[1] += price;
                                        storageFullImportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2023[2] += price;
                                        storageFullImportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal[0] += price;
                                        storageFullImportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal[1] += price;
                                        storageFullImportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal[2] += price;
                                        storageFullImportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }

                        }

                        // storageFullTransshipmentInervalsTotal
                        if (!shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullTransshipmentIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(0).getPrice();
                                        storageFullTransshipmentInervalsTotal_2024[0] += price;
                                        storageFullTransshipmentInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(1).getPrice();
                                        storageFullTransshipmentInervalsTotal_2024[1] += price;
                                        storageFullTransshipmentInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(2).getPrice();
                                        storageFullTransshipmentInervalsTotal_2024[2] += price;
                                        storageFullTransshipmentInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(3).getPrice();
                                        storageFullTransshipmentInervalsTotal_2024[3] += price;
                                        storageFullTransshipmentInervalsQuantities_2024[3] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(3)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(4) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(4).getPrice();
                                        storageFullTransshipmentInervalsTotal_2024[4] += price;
                                        storageFullTransshipmentInervalsQuantities_2024[4] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(4)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullTransshipmentIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(0).getPrice();
                                        storageFullTransshipmentInervalsTotal_2023[0] += price;
                                        storageFullTransshipmentInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(1).getPrice();
                                        storageFullTransshipmentInervalsTotal_2023[1] += price;
                                        storageFullTransshipmentInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(2).getPrice();
                                        storageFullTransshipmentInervalsTotal_2023[2] += price;
                                        storageFullTransshipmentInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(3).getPrice();
                                        storageFullTransshipmentInervalsTotal_2023[3] += price;
                                        storageFullTransshipmentInervalsQuantities_2023[3] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(3)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullTransshipmentIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(0).getPrice();
                                        storageFullTransshipmentInervalsTotal[0] += price;
                                        storageFullTransshipmentInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(1).getPrice();
                                        storageFullTransshipmentInervalsTotal[1] += price;
                                        storageFullTransshipmentInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(2).getPrice();
                                        storageFullTransshipmentInervalsTotal[2] += price;
                                        storageFullTransshipmentInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                                                        * fullTransshipmentIntervals.get(3).getPrice();
                                        storageFullTransshipmentInervalsTotal[3] += price;
                                        storageFullTransshipmentInervalsQuantities[3] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(3)
                                                        : 0;
                                }
                        }

                        // storageFullReeferTransshipmentInervals
                        if (shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferTransshipmentIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferTransshipmentIntervals.get(0).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2024[0] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferTransshipmentIntervals.get(1).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2024[1] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferTransshipmentIntervals.get(2).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2024[2] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(2);

                                        price = numberOfDaysForEachIntervalInMonthList.get(3)
                                                        * fullReeferTransshipmentIntervals.get(3).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2024[3] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2024[3] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(3);

                                        price = numberOfDaysForEachIntervalInMonthList.get(4)
                                                        * fullReeferTransshipmentIntervals.get(4).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2024[4] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2024[4] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(4);

                                        price = numberOfDaysForEachIntervalInMonthList.get(5)
                                                        * fullReeferTransshipmentIntervals.get(5).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2024[5] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2024[5] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(5);

                                        price = numberOfDaysForEachIntervalInMonthList.get(5)
                                                        * fullReeferTransshipmentIntervals.get(5).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2024[5] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2024[5] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(5);

                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferTransshipmentIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferTransshipmentIntervals.get(0).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2023[0] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferTransshipmentIntervals.get(1).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2023[1] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferTransshipmentIntervals.get(2).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2023[2] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(2);

                                        price = numberOfDaysForEachIntervalInMonthList.get(3)
                                                        * fullReeferTransshipmentIntervals.get(3).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal_2023[3] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities_2023[3] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(3);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferTransshipmentIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferTransshipmentIntervals.get(0).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities[0] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferTransshipmentIntervals.get(1).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities[1] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferTransshipmentIntervals.get(2).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities[2] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(2);

                                        price = numberOfDaysForEachIntervalInMonthList.get(3)
                                                        * fullReeferTransshipmentIntervals.get(3).getPrice()
                                                        * numberOfTeus;
                                        storageFullReeferTransshipmentInervalsTotal[3] += price;
                                        if (price != 0)
                                                storageFullReeferTransshipmentInervalsQuantities[3] += numberOfTeus
                                                                * numberOfDaysForEachIntervalInMonthList.get(3);
                                }

                        }

                        // storageFullReeferExportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                        // storageFullReeferImportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                }
                content += "Description;Customer;Unit Measure;Price per Unit;Quantity;Tot Amount\n";
                if (hazardousImportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 22.68 EUR" + ";" +
                                        hazardousImportQuantity + ";" + df.format((hazardousImportTotal)) + "\n";
                if (hazardousImportTotal_2023 != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 23.44 EUR (2023)" + ";" +
                                        hazardousImportQuantity_2023 + ";" + df.format((hazardousImportTotal_2023))
                                        + "\n";
                if (hazardousImportTotal_2024 != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 23.61 EUR (2024)" + ";" +
                                        hazardousImportQuantity_2024 + ";" + df.format((hazardousImportTotal_2024))
                                        + "\n";

                if (hazardousExportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + ";"
                                        + "22.68 EUR" + ";" +
                                        hazardousExportQuantity + ";" + df.format(hazardousExportTotal) + "\n";

                if (hazardousExportTotal_2023 != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + ";"
                                        + "23.44 EUR (2023)" + ";" +
                                        hazardousExportQuantity_2023 + ";" + df.format(hazardousExportTotal_2023)
                                        + "\n";

                if (hazardousExportTotal_2024 != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + ";"
                                        + "23.61 EUR (2024)" + ";" +
                                        hazardousExportQuantity_2024 + ";" + df.format(hazardousExportTotal_2024)
                                        + "\n";

                if (hazardousTranssipmentTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" + "22.68 EUR" + ";" +
                                        hazardousTransshipmentQuantity + ";" + df.format(hazardousTranssipmentTotal)
                                        + "\n";
                if (hazardousTranssipmentTotal_2023 != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" + "23.44 EUR (2023)" + ";" +
                                        hazardousTransshipmentQuantity_2023 + ";"
                                        + df.format(hazardousTranssipmentTotal_2023)
                                        + "\n";
                if (hazardousTranssipmentTotal_2024 != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" + "23.61 EUR (2024)" + ";" +
                                        hazardousTransshipmentQuantity_2024 + ";"
                                        + df.format(hazardousTranssipmentTotal_2024)
                                        + "\n";

                if (hazardousImportTotal != 0 || hazardousExportTotal != 0 || hazardousTranssipmentTotal != 0
                                || hazardousImportTotal_2023 != 0 || hazardousExportTotal_2023 != 0
                                || hazardousTranssipmentTotal_2023 != 0 || hazardousImportTotal_2024 != 0
                                || hazardousExportTotal_2024 != 0
                                || hazardousTranssipmentTotal_2024 != 0)
                        content += " ; ; ; ; ; \n";

                if (reeferConnectionTransshipmentTotal != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "31.23  EUR" + ";" +
                                        reeferConnectionTransshipmentQuantity + ";"
                                        + df.format(reeferConnectionTransshipmentTotal) + "\n";

                if (reeferConnectionTransshipmentTotal_2023 != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "32.28  EUR (2023)" + ";" +
                                        reeferConnectionTransshipmentQuantity_2023 + ";"
                                        + df.format(reeferConnectionTransshipmentTotal_2023) + "\n";

                if (reeferConnectionTransshipmentTotal_2024 != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "36.50  EUR (2024)" + ";" +
                                        reeferConnectionTransshipmentQuantity_2024 + ";"
                                        + df.format(reeferConnectionTransshipmentTotal_2024) + "\n";

                if (reeferConnectionImportTotal != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "35.53 EUR" + ";" +
                                        reeferConnectionImportQuantity + ";" + df.format(reeferConnectionImportTotal)
                                        + "\n";
                if (reeferConnectionImportTotal_2023 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023) " + ";" +
                                        reeferConnectionImportQuantity_2023 + ";"
                                        + df.format(reeferConnectionImportTotal_2023)
                                        + "\n";
                if (reeferConnectionImportTotal_2024 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.96 EUR (2024) " + ";" +
                                        reeferConnectionImportQuantity_2024 + ";"
                                        + df.format(reeferConnectionImportTotal_2024)
                                        + "\n";

                if (reeferConnectionExportTotal != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "35.53 EUR" + ";" +
                                        reeferConnectionExportQuantity + ";" + df.format(reeferConnectionExportTotal)
                                        + "\n";
                if (reeferConnectionExportTotal_2023 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023) " + ";" +
                                        reeferConnectionExportQuantity_2023 + ";"
                                        + df.format(reeferConnectionExportTotal_2023)
                                        + "\n";
                if (reeferConnectionExportTotal_2024 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.96 EUR (2024) " + ";" +
                                        reeferConnectionExportQuantity_2024 + ";"
                                        + df.format(reeferConnectionExportTotal_2024)
                                        + "\n";

                if (reeferConnectionTransshipmentTotal != 0 || reeferConnectionImportTotal != 0
                                || reeferConnectionExportTotal != 0 || reeferConnectionTransshipmentTotal_2023 != 0
                                || reeferConnectionImportTotal_2023 != 0
                                || reeferConnectionExportTotal_2023 != 0 || reeferConnectionTransshipmentTotal_2024 != 0
                                || reeferConnectionImportTotal_2024 != 0
                                || reeferConnectionExportTotal_2024 != 0)
                        content += " ; ; ; ; ; \n";

                if (storageOfDamagedContainersTotal != 0 || storageOfDamagedContainersTotal_2023 != 0) {
                        content += "Storage damaged Containers surcharge  " + ";" + "HLC" + ";" + "Quote %200" + ";"
                                        + "" + ";" +
                                        storageOfDamagedContainersQuantity + storageOfDamagedContainersQuantity_2023
                                        + ";"
                                        + df.format(storageOfDamagedContainersTotal
                                                        + storageOfDamagedContainersTotal_2023)
                                        + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullExportInervalsTotal[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR;"
                                        + storageFullExportInervalsQuantities[0] + ";" +
                                        df.format(storageFullExportInervalsTotal[0]) + "\n";
                if (storageFullExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2023) ;"
                                        + storageFullExportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[0]) + "\n";
                if (storageFullExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2024) ;"
                                        + storageFullExportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[0]) + "\n";

                if (storageFullExportInervalsTotal[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 1.43 EUR;"
                                        + storageFullExportInervalsQuantities[1] + ";" +
                                        df.format(storageFullExportInervalsTotal[1]) + "\n";
                if (storageFullExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 1.46 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[1]) + "\n";
                if (storageFullExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 1.49 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[1]) + "\n";

                if (storageFullExportInervalsTotal[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 3.86 EUR;"
                                        + storageFullExportInervalsQuantities[2] + ";" +
                                        df.format(storageFullExportInervalsTotal[2]) + "\n";
                if (storageFullExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 3.94 EUR;"
                                        + storageFullExportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[2]) + "\n";
                if (storageFullExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 4.02 EUR;"
                                        + storageFullExportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[2]) + "\n";

                if (storageFullExportInervalsTotal[0] != 0 || storageFullExportInervalsTotal[1] != 0
                                || storageFullExportInervalsTotal[2] != 0 || storageFullExportInervalsTotal_2023[0] != 0
                                || storageFullExportInervalsTotal_2023[1] != 0
                                || storageFullExportInervalsTotal_2023[2] != 0
                                || storageFullExportInervalsTotal_2024[0] != 0
                                || storageFullExportInervalsTotal_2024[1] != 0
                                || storageFullExportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullImportInervalsTotal[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR ;"
                                        + storageFullImportInervalsQuantities[0] + ";" +
                                        df.format(storageFullImportInervalsTotal[0]) + "\n";
                if (storageFullImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR ;"
                                        + storageFullImportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[0]) + "\n";
                if (storageFullImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR ;"
                                        + storageFullImportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[0]) + "\n";

                if (storageFullImportInervalsTotal[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 1.43 EUR;"
                                        + storageFullImportInervalsQuantities[1] + ";" +
                                        df.format(storageFullImportInervalsTotal[1]) + "\n";
                if (storageFullImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 1.46 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[1]) + "\n";
                if (storageFullImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 1.49 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[1]) + "\n";

                if (storageFullImportInervalsTotal[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 3.86 EUR;"
                                        + storageFullImportInervalsQuantities[2] + ";" +
                                        df.format(storageFullImportInervalsTotal[2]) + "\n";
                if (storageFullImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 3.94 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[2]) + "\n";
                if (storageFullImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Teu/Day"
                                        + "; 4.02 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[2]) + "\n";

                if (storageFullImportInervalsTotal[0] != 0 || storageFullImportInervalsTotal[1] != 0
                                || storageFullImportInervalsTotal[2] != 0 || storageFullImportInervalsTotal_2023[0] != 0
                                || storageFullImportInervalsTotal_2023[1] != 0
                                || storageFullImportInervalsTotal_2023[2] != 0
                                || storageFullImportInervalsTotal_2024[0] != 0
                                || storageFullImportInervalsTotal_2024[1] != 0
                                || storageFullImportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Transshipment First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR;"
                                        + storageFullTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[0]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2023[0] != 0)
                        content += "Storage Full Transshipment First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR;"
                                        + storageFullTransshipmentInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2023[0]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2024[0] != 0)
                        content += "Storage Full Transshipment First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR;"
                                        + storageFullTransshipmentInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2024[0]) + "\n";

                if (storageFullTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Transshipment Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 3.02 EUR;"
                                        + storageFullTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[1]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2023[1] != 0)
                        content += "Storage Full Transshipment Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 3.12 EUR (2023) ;"
                                        + storageFullTransshipmentInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2023[1]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2024[1] != 0)
                        content += "Storage Full Transshipment Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 3.12 EUR (2024) ;"
                                        + storageFullTransshipmentInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2024[1]) + "\n";

                if (storageFullTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Transshipment Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 3.75 EUR;"
                                        + storageFullTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[2]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2023[2] != 0)
                        content += "Storage Full Transshipment Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 3.88 EUR  (2023)  ;"
                                        + storageFullTransshipmentInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2023[2]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2024[2] != 0)
                        content += "Storage Full Transshipment Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 6 EUR  (2023) ;"
                                        + storageFullTransshipmentInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2024[2]) + "\n";

                if (storageFullTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 5.61 EUR;"
                                        + storageFullTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[3]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2023[3] != 0)
                        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 5.80 EUR (2023);"
                                        + storageFullTransshipmentInervalsQuantities_2023[3] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2023[3]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2024[3] != 0)
                        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 15 EUR (2024);"
                                        + storageFullTransshipmentInervalsQuantities_2024[3] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2024[3]) + "\n";
                if (storageFullTransshipmentInervalsTotal_2024[4] != 0)
                        content += "Storage Full Transshipment Fifth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Teu/Day" + "; 15 EUR (2024);"
                                        + storageFullTransshipmentInervalsQuantities_2024[4] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal_2024[4]) + "\n";

                if (storageFullTransshipmentInervalsTotal[0] != 0 || storageFullTransshipmentInervalsTotal[1] != 0
                                || storageFullTransshipmentInervalsTotal[2] != 0
                                || storageFullTransshipmentInervalsTotal[3] != 0
                                || storageFullTransshipmentInervalsTotal_2023[0] != 0
                                || storageFullTransshipmentInervalsTotal_2023[1] != 0
                                || storageFullTransshipmentInervalsTotal_2023[2] != 0
                                || storageFullTransshipmentInervalsTotal_2023[3] != 0
                                || storageFullTransshipmentInervalsTotal_2024[0] != 0
                                || storageFullTransshipmentInervalsTotal_2024[1] != 0
                                || storageFullTransshipmentInervalsTotal_2024[2] != 0
                                || storageFullTransshipmentInervalsTotal_2024[3] != 0
                                || storageFullTransshipmentInervalsTotal_2024[4] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[0]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR (2023);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR (2024);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 4.01 EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[1]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 4.14 EUR (2023);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 3 EUR (2024);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 4.97 EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[2]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 5.14 EUR (2023);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 6 EUR (2024);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 7.58 EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[3]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2023[3] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 7.83 EUR (2023);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2023[3] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2023[3]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2024[3] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 12 EUR (2024);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2024[3] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2024[3]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2024[4] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 18 EUR (2024);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2024[4] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2024[4]) + "\n";
                if (storageFullReeferTransshipmentInervalsTotal_2024[5] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 36 EUR (2024);"
                                        + storageFullReeferTransshipmentInervalsQuantities_2024[5] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal_2024[5]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0
                                || storageFullReeferTransshipmentInervalsTotal[1] != 0
                                || storageFullReeferTransshipmentInervalsTotal[2] != 0
                                || storageFullReeferTransshipmentInervalsTotal[3] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2023[0] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2023[1] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2023[2] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2023[3] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2023[0] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2024[1] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2024[2] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2024[3] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2024[4] != 0
                                || storageFullReeferTransshipmentInervalsTotal_2024[5] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferExportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + storageFullReeferExportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferExportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + storageFullReeferExportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 2.96 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferExportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 7.71 EUR;"
                                        + storageFullReeferExportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 7.86 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 8.02 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferExportInervalsTotal[0] != 0 || storageFullReeferExportInervalsTotal[1] != 0
                                || storageFullReeferExportInervalsTotal[2] != 0
                                || storageFullReeferExportInervalsTotal_2023[0] != 0
                                || storageFullReeferExportInervalsTotal_2023[1] != 0
                                || storageFullReeferExportInervalsTotal_2023[2] != 0
                                || storageFullReeferExportInervalsTotal_2024[0] != 0
                                || storageFullReeferExportInervalsTotal_2024[1] != 0
                                || storageFullReeferExportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferImportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + ";" + " 0 EUR;"
                                        + storageFullReeferImportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + ";" + " 0 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferImportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + storageFullReeferImportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 2.98 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferImportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 7.71 EUR;"
                                        + storageFullReeferImportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 7.86 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "HLC" + ";"
                                        + "Tarif/Unit/Day" + "; 8.02 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferImportInervalsTotal[0] != 0 || storageFullReeferImportInervalsTotal[1] != 0
                                || storageFullReeferImportInervalsTotal[2] != 0
                                || storageFullReeferImportInervalsTotal_2023[0] != 0
                                || storageFullReeferImportInervalsTotal_2023[1] != 0
                                || storageFullReeferImportInervalsTotal_2023[2] != 0
                                || storageFullReeferImportInervalsTotal_2024[0] != 0
                                || storageFullReeferImportInervalsTotal_2024[1] != 0
                                || storageFullReeferImportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfOOGContainersTotal != 0 || storageOfOOGContainersTotal_2023 != 0) {
                        content += "Storage surcharge Non-Standard Container " + ";" + "HLC" + ";" + "Quote %100" + ";"
                                        + "" + ";" +
                                        storageOfOOGContainersQuantity + storageOfOOGContainersQuantity_2023 + ";" +
                                        df.format(storageOfOOGContainersTotal + storageOfOOGContainersTotal_2023)
                                        + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfTankContainersTotal != 0 || storageOfTankContainersTotal_2023 != 0) {
                        content += "Storage tank Containers surcharge " + ";" + "HLC" + ";" + "Quote %100" + ";" + ""
                                        + ";"
                                        + storageOfTankContainersQuantity + storageOfTankContainersQuantity_2023 + ";" +
                                        df.format(storageOfTankContainersTotal + storageOfTankContainersTotal_2023)
                                        + "\n";
                        content += " ; ; ; ; ; \n";
                }

                double total = hazardousImportTotal
                                + hazardousImportTotal_2023
                                + hazardousImportTotal_2024
                                + hazardousExportTotal
                                + hazardousExportTotal_2023
                                + hazardousExportTotal_2024
                                + hazardousTranssipmentTotal
                                + hazardousTranssipmentTotal_2023
                                + hazardousTranssipmentTotal_2024
                                + reeferConnectionTransshipmentTotal
                                + reeferConnectionTransshipmentTotal_2023
                                + reeferConnectionTransshipmentTotal_2024
                                + reeferConnectionImportTotal
                                + reeferConnectionImportTotal_2023
                                + reeferConnectionImportTotal_2024
                                + reeferConnectionExportTotal
                                + reeferConnectionExportTotal_2023
                                + reeferConnectionExportTotal_2024
                                + storageOfDamagedContainersTotal
                                + storageOfDamagedContainersTotal_2023
                                + storageOfDamagedContainersTotal_2024
                                + storageFullExportInervalsTotal[0]
                                + storageFullExportInervalsTotal_2023[0]
                                + storageFullExportInervalsTotal_2024[0]
                                + storageFullExportInervalsTotal[1]
                                + storageFullExportInervalsTotal_2023[1]
                                + storageFullExportInervalsTotal_2024[1]
                                + storageFullExportInervalsTotal[2]
                                + storageFullExportInervalsTotal_2023[2]
                                + storageFullExportInervalsTotal_2024[2]
                                + storageFullImportInervalsTotal[0]
                                + storageFullImportInervalsTotal_2023[0]
                                + storageFullImportInervalsTotal_2024[0]
                                + storageFullImportInervalsTotal[1]
                                + storageFullImportInervalsTotal_2023[1]
                                + storageFullImportInervalsTotal_2024[1]
                                + storageFullImportInervalsTotal[2]
                                + storageFullImportInervalsTotal_2023[2]
                                + storageFullImportInervalsTotal_2024[2]
                                + storageFullTransshipmentInervalsTotal[0]
                                + storageFullTransshipmentInervalsTotal_2023[0]
                                + storageFullTransshipmentInervalsTotal_2024[0]
                                + storageFullTransshipmentInervalsTotal[1]
                                + storageFullTransshipmentInervalsTotal_2023[1]
                                + storageFullTransshipmentInervalsTotal_2024[1]
                                + storageFullTransshipmentInervalsTotal[2]
                                + storageFullTransshipmentInervalsTotal_2023[2]
                                + storageFullTransshipmentInervalsTotal_2024[2]
                                + storageFullTransshipmentInervalsTotal[3]
                                + storageFullTransshipmentInervalsTotal_2023[3]
                                + storageFullTransshipmentInervalsTotal_2024[3]
                                + storageFullTransshipmentInervalsTotal_2024[4]
                                + storageFullReeferTransshipmentInervalsTotal[0]
                                + storageFullReeferTransshipmentInervalsTotal_2023[0]
                                + storageFullReeferTransshipmentInervalsTotal_2024[0]
                                + storageFullReeferTransshipmentInervalsTotal[1]
                                + storageFullReeferTransshipmentInervalsTotal_2023[1]
                                + storageFullReeferTransshipmentInervalsTotal_2024[1]
                                + storageFullReeferTransshipmentInervalsTotal[2]
                                + storageFullReeferTransshipmentInervalsTotal_2023[2]
                                + storageFullReeferTransshipmentInervalsTotal_2024[2]
                                + storageFullReeferTransshipmentInervalsTotal[3]
                                + storageFullReeferTransshipmentInervalsTotal_2023[3]
                                + storageFullReeferTransshipmentInervalsTotal_2024[3]
                                + storageFullReeferTransshipmentInervalsTotal_2024[4]
                                + storageFullReeferTransshipmentInervalsTotal_2024[5]
                                + storageFullReeferExportInervalsTotal[0]
                                + storageFullReeferExportInervalsTotal_2023[0]
                                + storageFullReeferExportInervalsTotal_2024[0]
                                + storageFullReeferExportInervalsTotal[1]
                                + storageFullReeferExportInervalsTotal_2023[1]
                                + storageFullReeferExportInervalsTotal_2024[1]
                                + storageFullReeferExportInervalsTotal[2]
                                + storageFullReeferExportInervalsTotal_2023[2]
                                + storageFullReeferExportInervalsTotal_2024[2]
                                + storageFullReeferImportInervalsTotal[0]
                                + storageFullReeferImportInervalsTotal_2023[0]
                                + storageFullReeferImportInervalsTotal_2024[0]
                                + storageFullReeferImportInervalsTotal[1]
                                + storageFullReeferImportInervalsTotal_2023[1]
                                + storageFullReeferImportInervalsTotal_2024[1]
                                + storageFullReeferImportInervalsTotal[2]
                                + storageFullReeferImportInervalsTotal_2023[2]
                                + storageFullReeferImportInervalsTotal_2024[2]
                                + storageOfOOGContainersTotal
                                + storageOfOOGContainersTotal_2023
                                + storageOfOOGContainersTotal_2024
                                + storageOfTankContainersTotal
                                + storageOfTankContainersTotal_2023
                                + storageOfTankContainersTotal_2024;
                /*
                 * +emptyDepotContainersTotal
                 * +emptyExportContainersTotal
                 * +emptyImportContainersTotal
                 * +emptyTransshipmentContainersTotal[0]
                 * +emptyTransshipmentContainersTotal[1]
                 * +emptyTransshipmentContainersTotal[2]
                 * +emptyTransshipmentContainersTotal[3];
                 */
                content += "Total " + ";" + " " + ";" + " " + ";" + " " + ";" + ";" + df.format(total) + "\n";
                return content;
        }

        public static String ARKSumMonthlyStorageDetails(String month, String year) throws ParseException, IOException {
                fw = null;
                bw = null;
                String content = "";
                hazardousImportTotal = 0;
                hazardousImportTotal_2023 = 0;
                hazardousImportTotal_2024 = 0;
                hazardousImportQuantity = 0;
                hazardousImportQuantity_2023 = 0;
                hazardousImportQuantity_2024 = 0;

                hazardousExportTotal = 0;
                hazardousExportTotal_2023 = 0;
                hazardousExportTotal_2024 = 0;
                hazardousExportQuantity = 0;
                hazardousExportQuantity_2023 = 0;
                hazardousExportQuantity_2024 = 0;

                hazardousTranssipmentTotal = 0;
                hazardousTranssipmentTotal_2023 = 0;
                hazardousTranssipmentTotal_2024 = 0;
                hazardousTransshipmentQuantity = 0;
                hazardousTransshipmentQuantity_2023 = 0;
                hazardousTransshipmentQuantity_2024 = 0;
                hazardousPrice = 0;

                reeferConnectionTransshipmentTotal = 0;
                reeferConnectionTransshipmentTotal_2023 = 0;
                reeferConnectionTransshipmentTotal_2024 = 0;
                reeferConnectionTransshipmentQuantity = 0;
                reeferConnectionTransshipmentQuantity_2023 = 0;
                reeferConnectionTransshipmentQuantity_2024 = 0;
                reeferConnectionTransshipmentPrice = 0;

                reeferConnectionImportTotal = 0;
                reeferConnectionImportTotal_2023 = 0;
                reeferConnectionImportTotal_2024 = 0;
                reeferConnectionImportQuantity = 0;
                reeferConnectionImportQuantity_2023 = 0;
                reeferConnectionImportQuantity_2024 = 0;

                reeferConnectionExportTotal = 0;
                reeferConnectionExportTotal_2023 = 0;
                reeferConnectionExportTotal_2024 = 0;
                reeferConnectionExportQuantity = 0;
                reeferConnectionExportQuantity_2023 = 0;
                reeferConnectionExportQuantity_2024 = 0;
                reeferConnectionImportExportPrice = 0;
                reeferConnectionImportExportPrice_2023 = 0;
                reeferConnectionImportExportPrice_2024 = 0;

                storageOfDamagedContainersTotal = 0;
                storageOfDamagedContainersTotal_2023 = 0;
                storageOfDamagedContainersTotal_2024 = 0;
                storageOfDamagedContainersQuantity = 0;
                storageOfDamagedContainersQuantity_2023 = 0;
                storageOfDamagedContainersQuantity_2024 = 0;

                storageFullExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                storageFullReeferTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                storageFullReeferExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullReeferImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageOfOOGContainersTotal = 0;
                storageOfOOGContainersQuantity = 0;

                storageOfTankContainersTotal = 0;
                storageOfTankContainersQuantity = 0;

                emptyDepotContainersTotal = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities = new int[] { 0, 0, 0, 0 };

                /*
                 * emptyExportContainersTotal=0;
                 * emptyExportContainersQuantity=0;
                 * 
                 * 
                 * emptyImportContainersTotal=0;
                 * emptyImportContainersQuantity=0;
                 */

                emptyTransshipmentContainersTotal = new double[] { 0, 0, 0, 0 };

                List<Interval> fullExportIntervals = new ArrayList<>();
                List<Interval> fullImportIntervals = new ArrayList<>();
                List<Interval> fullTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferDirectIntervals = new ArrayList<>();
                List<Interval> emptyImportOrExportIntervals = new ArrayList<>();

                // emptyImportOrExportIntervals
                emptyImportOrExportIntervals = StandardContainerIntervals.emptyExportIntervals();

                for (ContainerStorageDetailsDTO shippingLinesInvoicing : shippingLinesInvoicingList) {
                        Container container = convertToContainer(shippingLinesInvoicing);
                        int theYear = container.getIncDate().getYear() - 100;

                        hazardousPrice = (container.getIncDate().getYear() == 123) ? 23.61 : 23.61;
                        reeferConnectionTransshipmentPrice = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 123) ? 45 : 41)
                                        : 50;
                        reeferConnectionImportExportPrice = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 123) ? 36.24 : 35.53)
                                        : 36.96;

                        // fullTransshipmentIntervals
                        fullTransshipmentIntervals = (container.getIncDate().getYear() == 121)
                                        ? ARKContainerIntervals.fullTransshipmentIntervals2021()
                                        : ARKContainerIntervals.fullTransshipmentIntervals();

                        // fullReeferTransshipmentIntervals
                        fullReeferTransshipmentIntervals = (container.getIncDate().getYear() == 121)
                                        ? ARKContainerIntervals.fullTransshipmentIntervals2021()
                                        : ARKContainerIntervals.fullTransshipmentIntervals();

                        // fullImportIntervals
                        fullImportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ARKContainerIntervals.fullImportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ARKContainerIntervals
                                                                                        .fullImportIntervals2023()
                                                                        : ARKContainerIntervals
                                                                                        .fullImportIntervals2021())
                                        : ARKContainerIntervals.fullImportIntervals2024();

                        // fullExportIntervals
                        fullExportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ARKContainerIntervals.fullExportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ARKContainerIntervals
                                                                                        .fullExportIntervals2023()
                                                                        : ARKContainerIntervals
                                                                                        .fullExportIntervals2021())
                                        : ARKContainerIntervals.fullExportIntervals2024();

                        // fullReeferDirectIntervals
                        fullReeferDirectIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ARKContainerIntervals.fullReeferDirectIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ARKContainerIntervals
                                                                                        .fullReeferDirectIntervals2023()
                                                                        : ARKContainerIntervals
                                                                                        .fullReeferDirectIntervals2021())
                                        : ARKContainerIntervals.fullReeferDirectIntervals2024();

                        // Hazardous import Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                hazardousImportTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getARKContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousImportQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // Hazardous Export Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                hazardousExportTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getARKContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousExportQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // Hazardous Transshipment Surcharge
                        if (shippingLinesInvoicing.imdg
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                hazardousTranssipmentTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getARKContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousTransshipmentQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // reeferConnectionTransshipment & Direct Total surchagre
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {

                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                fullReeferTransshipmentIntervals, month,
                                                year).stream().mapToInt(Integer::intValue).sum();
                                if (container.isReef() && container.getInvoiceCategory().equals("Transshipment")) {
                                        if (theYear == 24) {
                                                reeferConnectionTransshipmentTotal_2024 += numOfDays
                                                                * reeferConnectionTransshipmentPrice;
                                                reeferConnectionTransshipmentQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionTransshipmentTotal_2023 += numOfDays
                                                                * reeferConnectionTransshipmentPrice;
                                                reeferConnectionTransshipmentQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionTransshipmentTotal += numOfDays
                                                                * reeferConnectionTransshipmentPrice;
                                                reeferConnectionTransshipmentQuantity += numOfDays;
                                        }

                                }
                                if (container.isReef() && container.getInvoiceCategory().equals("Import")) {
                                        if (theYear == 24) {
                                                reeferConnectionImportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionImportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionImportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity += numOfDays;
                                        }

                                }
                                if (container.isReef() && container.getInvoiceCategory().equals("Export")) {
                                        if (theYear == 24) {
                                                reeferConnectionExportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionExportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionExportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity += numOfDays;
                                        }

                                }
                        }
                        // storageOfDamagedContainers surcharge
                        if (shippingLinesInvoicing.dmg) {
                                storageOfDamagedContainersTotal += shippingLinesInvoicing.dmgSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getARKContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfDamagedContainersQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // storageOfOOGContainers surchagre
                        if (shippingLinesInvoicing.oog) {
                                storageOfOOGContainersTotal += shippingLinesInvoicing.oogSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getARKContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfOOGContainersQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // storageOfTankContainers surchagre
                        if (shippingLinesInvoicing.type.equals("TK") || shippingLinesInvoicing.type.equals("TO")) {
                                storageOfTankContainersTotal += shippingLinesInvoicing.tankSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getARKContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfTankContainersQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // storageFullExportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2024[0] += price;
                                        storageFullExportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2024[1] += price;
                                        storageFullExportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2024[2] += price;
                                        storageFullExportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2023[0] += price;
                                        storageFullExportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2023[1] += price;
                                        storageFullExportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2023[2] += price;
                                        storageFullExportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal[0] += price;
                                        storageFullExportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal[1] += price;
                                        storageFullExportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal[2] += price;
                                        storageFullExportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }
                        }
                        // storageFullImportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2024[0] += price;
                                        storageFullImportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2024[1] += price;
                                        storageFullImportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2024[2] += price;
                                        storageFullImportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2023[0] += price;
                                        storageFullImportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2023[1] += price;
                                        storageFullImportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2023[2] += price;
                                        storageFullImportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal[0] += price;
                                        storageFullImportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal[1] += price;
                                        storageFullImportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal[2] += price;
                                        storageFullImportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }

                        }
                        // storageFullTransshipmentInervalsTotal
                        if (!shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                * fullTransshipmentIntervals.get(0).getPrice();
                                storageFullTransshipmentInervalsTotal[0] += price;
                                storageFullTransshipmentInervalsQuantities[0] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                * fullTransshipmentIntervals.get(1).getPrice();
                                storageFullTransshipmentInervalsTotal[1] += price;
                                storageFullTransshipmentInervalsQuantities[1] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                * fullTransshipmentIntervals.get(2).getPrice();
                                storageFullTransshipmentInervalsTotal[2] += price;
                                storageFullTransshipmentInervalsQuantities[2] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                                                * fullTransshipmentIntervals.get(3).getPrice();
                                storageFullTransshipmentInervalsTotal[3] += price;
                                storageFullTransshipmentInervalsQuantities[3] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(3)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(4) * numberOfTeus
                                                * fullTransshipmentIntervals.get(4).getPrice();
                                storageFullTransshipmentInervalsTotal[4] += price;
                                storageFullTransshipmentInervalsQuantities[4] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(4)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(5) * numberOfTeus
                                                * fullTransshipmentIntervals.get(5).getPrice();
                                storageFullTransshipmentInervalsTotal[5] += price;
                                storageFullTransshipmentInervalsQuantities[5] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(5)
                                                : 0;
                        }
                        // storageFullReeferTransshipmentInervals
                        if (shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullReeferTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                * fullReeferTransshipmentIntervals.get(0).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[0] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[0] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(0);

                                price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                * fullReeferTransshipmentIntervals.get(1).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[1] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[1] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(1);

                                price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                * fullReeferTransshipmentIntervals.get(2).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[2] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[2] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(2);

                                price = numberOfDaysForEachIntervalInMonthList.get(3)
                                                * fullReeferTransshipmentIntervals.get(3).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[3] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[3] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(3);

                                price = numberOfDaysForEachIntervalInMonthList.get(4)
                                                * fullReeferTransshipmentIntervals.get(4).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[4] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[4] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(4);

                                price = numberOfDaysForEachIntervalInMonthList.get(5)
                                                * fullReeferTransshipmentIntervals.get(5).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[5] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[5] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(5);

                        }

                        // storageFullReeferExportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                        // storageFullReeferImportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                }
                content += "Description;Customer;Unit Measure;Price per Unit;Quantity;Tot Amount\n";
                if (hazardousImportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day" + ";"
                                        + "23.61 EUR" + ";" +
                                        hazardousImportQuantity + ";" + df.format((hazardousImportTotal)) + "\n";

                if (hazardousExportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day" + ";"
                                        + "23.61 EUR" + ";" +
                                        hazardousExportQuantity + ";" + df.format(hazardousExportTotal) + "\n";

                if (hazardousTranssipmentTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" + "23.61 EUR" + ";" +
                                        hazardousTransshipmentQuantity + ";" + df.format(hazardousTranssipmentTotal)
                                        + "\n";

                if (hazardousImportTotal != 0 || hazardousExportTotal != 0 || hazardousTranssipmentTotal != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (reeferConnectionTransshipmentTotal != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "41 EUR" + ";" +
                                        reeferConnectionTransshipmentQuantity + ";"
                                        + df.format(reeferConnectionTransshipmentTotal) + "\n";

                if (reeferConnectionTransshipmentTotal_2023 != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "45 EUR (2023)" + ";" +
                                        reeferConnectionTransshipmentQuantity + ";"
                                        + df.format(reeferConnectionTransshipmentTotal_2023) + "\n";
                if (reeferConnectionTransshipmentTotal_2024 != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "50 EUR (2024)" + ";" +
                                        reeferConnectionTransshipmentQuantity + ";"
                                        + df.format(reeferConnectionTransshipmentTotal_2024) + "\n";

                if (reeferConnectionImportTotal != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "35.53 EUR" + ";" +
                                        reeferConnectionImportQuantity + ";" + df.format(reeferConnectionImportTotal)
                                        + "\n";
                if (reeferConnectionImportTotal_2023 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionImportQuantity_2023 + ";"
                                        + df.format(reeferConnectionImportTotal_2023)
                                        + "\n";
                if (reeferConnectionImportTotal_2024 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.96 EUR (2024)" + ";" +
                                        reeferConnectionImportQuantity_2024 + ";"
                                        + df.format(reeferConnectionImportTotal_2024)
                                        + "\n";

                if (reeferConnectionExportTotal != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "35.53 EUR" + ";" +
                                        reeferConnectionExportQuantity + ";" + df.format(reeferConnectionExportTotal)
                                        + "\n";
                if (reeferConnectionExportTotal_2023 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionExportQuantity_2023 + ";"
                                        + df.format(reeferConnectionExportTotal_2023)
                                        + "\n";
                if (reeferConnectionExportTotal_2023 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionExportQuantity_2024 + ";"
                                        + df.format(reeferConnectionExportTotal_2024)
                                        + "\n";

                if (reeferConnectionTransshipmentTotal != 0 ||
                                reeferConnectionTransshipmentTotal_2023 != 0 ||
                                reeferConnectionTransshipmentTotal_2024 != 0 ||
                                reeferConnectionImportTotal != 0 ||
                                reeferConnectionImportTotal_2023 != 0 ||
                                reeferConnectionImportTotal_2024 != 0 ||
                                reeferConnectionExportTotal != 0 ||
                                reeferConnectionExportTotal_2023 != 0 ||
                                reeferConnectionExportTotal_2024 != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfDamagedContainersTotal != 0 || storageOfDamagedContainersTotal_2023 != 0) {
                        content += "Storage damaged Containers surcharge  " + ";" + "ARK" + ";" + "Quote %200" + ";"
                                        + "" + ";" +
                                        storageOfDamagedContainersQuantity + storageOfDamagedContainersQuantity_2023
                                        + ";"
                                        + df.format(storageOfDamagedContainersTotal
                                                        + storageOfDamagedContainersTotal_2023)
                                        + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullExportInervalsTotal[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR;"
                                        + storageFullExportInervalsQuantities[0] + ";" +
                                        df.format(storageFullExportInervalsTotal[0]) + "\n";
                if (storageFullExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[0]) + "\n";
                if (storageFullExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[0]) + "\n";

                if (storageFullExportInervalsTotal[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 1.43 EUR;"
                                        + storageFullExportInervalsQuantities[1] + ";" +
                                        df.format(storageFullExportInervalsTotal[1]) + "\n";
                if (storageFullExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 1.46 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[1]) + "\n";
                if (storageFullExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 1.49 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[1]) + "\n";

                if (storageFullExportInervalsTotal[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 3.86 EUR;"
                                        + storageFullExportInervalsQuantities[2] + ";" +
                                        df.format(storageFullExportInervalsTotal[2]) + "\n";
                if (storageFullExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 3.94 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[2]) + "\n";
                if (storageFullExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 4.02 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[2]) + "\n";

                if (storageFullExportInervalsTotal[0] != 0 || storageFullExportInervalsTotal[1] != 0
                                || storageFullExportInervalsTotal[2] != 0 || storageFullExportInervalsTotal_2023[0] != 0
                                || storageFullExportInervalsTotal_2023[1] != 0
                                || storageFullExportInervalsTotal_2023[2] != 0
                                || storageFullExportInervalsTotal_2024[0] != 0
                                || storageFullExportInervalsTotal_2024[1] != 0
                                || storageFullExportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullImportInervalsTotal[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR;"
                                        + storageFullImportInervalsQuantities[0] + ";" +
                                        df.format(storageFullImportInervalsTotal[0]) + "\n";
                if (storageFullImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[0]) + "\n";
                if (storageFullImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[0]) + "\n";

                if (storageFullImportInervalsTotal[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 1.43 EUR;"
                                        + storageFullImportInervalsQuantities[1] + ";" +
                                        df.format(storageFullImportInervalsTotal[1]) + "\n";
                if (storageFullImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 1.46 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[1]) + "\n";
                if (storageFullImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 1.49 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[1]) + "\n";

                if (storageFullImportInervalsTotal[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 3.86 EUR;"
                                        + storageFullImportInervalsQuantities[2] + ";" +
                                        df.format(storageFullImportInervalsTotal[2]) + "\n";
                if (storageFullImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 3.94 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[2]) + "\n";
                if (storageFullImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Teu/Day"
                                        + "; 4.02 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[2]) + "\n";

                if (storageFullImportInervalsTotal[0] != 0 || storageFullImportInervalsTotal[1] != 0
                                || storageFullImportInervalsTotal[2] != 0 || storageFullImportInervalsTotal_2023[0] != 0
                                || storageFullImportInervalsTotal_2023[1] != 0
                                || storageFullImportInervalsTotal_2023[2] != 0
                                || storageFullImportInervalsTotal_2024[0] != 0
                                || storageFullImportInervalsTotal_2024[1] != 0
                                || storageFullImportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Transshipment First Period Revenue  " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[0]) + "\n";

                if (storageFullTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Transshipment Second Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[1]) + "\n";

                if (storageFullTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Transshipment Third Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[2]) + "\n";

                if (storageFullTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[3]) + "\n";

                if (storageFullTransshipmentInervalsTotal[4] != 0)
                        content += "Storage Full Transshipment Fifth Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[4] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[4]) + "\n";

                if (storageFullTransshipmentInervalsTotal[5] != 0)
                        content += "Storage Full Transshipment Sixth Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[5] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[5]) + "\n";

                if (storageFullTransshipmentInervalsTotal[0] != 0 ||
                                storageFullTransshipmentInervalsTotal[1] != 0 ||
                                storageFullTransshipmentInervalsTotal[2] != 0 ||
                                storageFullTransshipmentInervalsTotal[3] != 0 ||
                                storageFullTransshipmentInervalsTotal[4] != 0 ||
                                storageFullTransshipmentInervalsTotal[5] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[0]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[1]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[2]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[3]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[4] != 0)
                        content += "Storage Full Reefer Transshipment Fifth Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[4] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[4]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[5] != 0)
                        content += "Storage Full Reefer Transshipment Sixth Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[5] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[5]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[1] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[2] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[3] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[4] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[5] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferExportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + storageFullReeferExportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferExportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + storageFullReeferExportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 2.98 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferExportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 7.71 EUR;"
                                        + storageFullReeferExportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 7.86 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 8.02 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferExportInervalsTotal[0] != 0 ||
                                storageFullReeferExportInervalsTotal[1] != 0 ||
                                storageFullReeferExportInervalsTotal[2] != 0
                                || storageFullReeferExportInervalsTotal_2023[0] != 0 ||
                                storageFullReeferExportInervalsTotal_2023[1] != 0 ||
                                storageFullReeferExportInervalsTotal_2023[2] != 0
                                || storageFullReeferExportInervalsTotal_2024[0] != 0 ||
                                storageFullReeferExportInervalsTotal_2024[1] != 0 ||
                                storageFullReeferExportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferImportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + storageFullReeferImportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferImportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + storageFullReeferImportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferImportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + ";  7.71 EUR;"
                                        + storageFullReeferImportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 7.86 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "ARK" + ";"
                                        + "Tarif/Unit/Day" + "; 8.02 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferImportInervalsTotal[0] != 0 ||
                                storageFullReeferImportInervalsTotal[1] != 0 ||
                                storageFullReeferImportInervalsTotal[2] != 0
                                || storageFullReeferImportInervalsTotal_2023[0] != 0 ||
                                storageFullReeferImportInervalsTotal_2023[1] != 0 ||
                                storageFullReeferImportInervalsTotal_2023[2] != 0
                                || storageFullReeferImportInervalsTotal_2024[0] != 0 ||
                                storageFullReeferImportInervalsTotal_2024[1] != 0 ||
                                storageFullReeferImportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfOOGContainersTotal != 0 || storageOfOOGContainersTotal_2023 != 0) {
                        content += "Storage surcharge Non-Standard Container " + ";" + "ARK" + ";" + "Quote %100" + ";"
                                        + "" + ";" +
                                        storageOfOOGContainersQuantity + ";" +
                                        df.format(storageOfOOGContainersTotal) + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfTankContainersTotal != 0 || storageOfTankContainersTotal_2023 != 0) {
                        content += "Storage tank Containers surcharge " + ";" + "ARK" + ";" + "Quote %100" + ";" + ""
                                        + ";"
                                        + storageOfTankContainersQuantity + storageOfTankContainersQuantity_2023 + ";" +
                                        df.format(storageOfTankContainersTotal + storageOfTankContainersTotal_2023)
                                        + "\n";
                        content += " ; ; ; ; ; \n";
                }

                double total = hazardousImportTotal
                                + hazardousImportTotal_2023
                                + hazardousImportTotal_2024
                                + hazardousExportTotal
                                + hazardousTranssipmentTotal
                                + reeferConnectionTransshipmentTotal
                                + reeferConnectionImportTotal
                                + reeferConnectionExportTotal
                                + reeferConnectionImportTotal_2023
                                + reeferConnectionImportTotal_2024
                                + reeferConnectionExportTotal_2023
                                + reeferConnectionExportTotal_2024
                                + storageOfDamagedContainersTotal
                                + storageFullExportInervalsTotal[0]
                                + storageFullExportInervalsTotal[1]
                                + storageFullExportInervalsTotal[2]
                                + storageFullImportInervalsTotal[0]
                                + storageFullImportInervalsTotal[1]
                                + storageFullImportInervalsTotal[2]
                                + storageFullExportInervalsTotal_2023[0]
                                + storageFullExportInervalsTotal_2024[0]
                                + storageFullExportInervalsTotal_2023[1]
                                + storageFullExportInervalsTotal_2024[1]
                                + storageFullExportInervalsTotal_2023[2]
                                + storageFullExportInervalsTotal_2024[2]
                                + storageFullImportInervalsTotal_2023[0]
                                + storageFullImportInervalsTotal_2024[0]
                                + storageFullImportInervalsTotal_2023[1]
                                + storageFullImportInervalsTotal_2024[1]
                                + storageFullImportInervalsTotal_2023[2]
                                + storageFullImportInervalsTotal_2024[2]
                                + storageFullTransshipmentInervalsTotal[0]
                                + storageFullTransshipmentInervalsTotal[1]
                                + storageFullTransshipmentInervalsTotal[2]
                                + storageFullTransshipmentInervalsTotal[3]
                                + storageFullTransshipmentInervalsTotal[4]
                                + storageFullTransshipmentInervalsTotal[5]
                                + storageFullReeferTransshipmentInervalsTotal[0]
                                + storageFullReeferTransshipmentInervalsTotal[1]
                                + storageFullReeferTransshipmentInervalsTotal[2]
                                + storageFullReeferTransshipmentInervalsTotal[3]
                                + storageFullReeferTransshipmentInervalsTotal[4]
                                + storageFullReeferTransshipmentInervalsTotal[5]
                                + storageFullReeferExportInervalsTotal[0]
                                + storageFullReeferExportInervalsTotal[1]
                                + storageFullReeferExportInervalsTotal[2]
                                + storageFullReeferImportInervalsTotal[0]
                                + storageFullReeferImportInervalsTotal[1]
                                + storageFullReeferImportInervalsTotal[2]
                                + storageFullReeferExportInervalsTotal_2023[0]
                                + storageFullReeferExportInervalsTotal_2024[0]
                                + storageFullReeferExportInervalsTotal_2023[1]
                                + storageFullReeferExportInervalsTotal_2024[1]
                                + storageFullReeferExportInervalsTotal_2023[2]
                                + storageFullReeferExportInervalsTotal_2024[2]
                                + storageFullReeferImportInervalsTotal_2023[0]
                                + storageFullReeferImportInervalsTotal_2024[0]
                                + storageFullReeferImportInervalsTotal_2023[1]
                                + storageFullReeferImportInervalsTotal_2024[1]
                                + storageFullReeferImportInervalsTotal_2023[2]
                                + storageFullReeferImportInervalsTotal_2024[2]
                                + storageOfOOGContainersTotal
                                + storageOfOOGContainersTotal_2023
                                + storageOfOOGContainersTotal_2024
                                + storageOfTankContainersTotal
                                + storageOfTankContainersTotal_2023
                                + storageOfTankContainersTotal_2024;
                /*
                 * +emptyDepotContainersTotal
                 * +emptyExportContainersTotal
                 * +emptyImportContainersTotal
                 * +emptyTransshipmentContainersTotal[0]
                 * +emptyTransshipmentContainersTotal[1]
                 * +emptyTransshipmentContainersTotal[2]
                 * +emptyTransshipmentContainersTotal[3];
                 */
                content += "Total " + ";" + " " + ";" + " " + ";" + " " + ";" + ";" + df.format(total) + "\n";

                return content;
        }

        public static String YMLSumMonthlyStorageDetails(String month, String year) throws ParseException, IOException {
                fw = null;
                bw = null;
                String content = "";
                hazardousImportTotal = 0;
                hazardousImportQuantity = 0;

                hazardousExportTotal = 0;
                hazardousExportQuantity = 0;

                hazardousTranssipmentTotal = 0;
                hazardousTransshipmentQuantity = 0;
                hazardousPrice = 0;

                reeferConnectionTransshipmentTotal = 0;
                reeferConnectionTransshipmentQuantity = 0;
                reeferConnectionTransshipmentPrice = 0;

                reeferConnectionImportTotal = 0;
                reeferConnectionImportQuantity = 0;
                reeferConnectionImportTotal_2023 = 0;
                reeferConnectionImportTotal_2024 = 0;
                reeferConnectionImportQuantity_2023 = 0;
                reeferConnectionImportQuantity_2024 = 0;

                reeferConnectionExportTotal = 0;
                reeferConnectionExportQuantity = 0;
                reeferConnectionExportTotal_2023 = 0;
                reeferConnectionExportTotal_2024 = 0;
                reeferConnectionExportQuantity_2023 = 0;
                reeferConnectionExportQuantity_2024 = 0;
                reeferConnectionImportExportPrice = 0;

                storageOfDamagedContainersTotal = 0;
                storageOfDamagedContainersQuantity = 0;
                storageOfDamagedContainersTotal_2023 = 0;
                storageOfDamagedContainersTotal_2024 = 0;
                storageOfDamagedContainersQuantity_2023 = 0;
                storageOfDamagedContainersQuantity_2024 = 0;

                storageFullExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                storageFullReeferTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                storageFullReeferExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullReeferImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageOfOOGContainersTotal = 0;
                storageOfOOGContainersQuantity = 0;
                storageOfOOGContainersTotal_2023 = 0;
                storageOfOOGContainersTotal_2024 = 0;
                storageOfOOGContainersQuantity_2023 = 0;
                storageOfOOGContainersQuantity_2024 = 0;

                storageOfTankContainersTotal = 0;
                storageOfTankContainersQuantity = 0;
                storageOfTankContainersTotal_2023 = 0;
                storageOfTankContainersTotal_2024 = 0;
                storageOfTankContainersQuantity_2023 = 0;
                storageOfTankContainersQuantity_2024 = 0;

                emptyDepotContainersTotal = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities = new int[] { 0, 0, 0, 0 };

                emptyTransshipmentContainersTotal = new double[] { 0, 0, 0, 0 };

                List<Interval> fullExportIntervals = new ArrayList<>();
                List<Interval> fullImportIntervals = new ArrayList<>();
                List<Interval> fullTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferDirectIntervals = new ArrayList<>();
                List<Interval> emptyImportOrExportIntervals = new ArrayList<>();

                // emptyImportOrExportIntervals
                emptyImportOrExportIntervals = StandardContainerIntervals.emptyExportIntervals();

                for (ContainerStorageDetailsDTO shippingLinesInvoicing : shippingLinesInvoicingList) {
                        Container container = convertToContainer(shippingLinesInvoicing);
                        int theYear = container.getIncDate().getYear() - 100;
                        reeferConnectionImportExportPrice = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 123) ? 36.24 : 35.53)
                                        : 36.96;

                        // fullTransshipmentIntervals
                        fullTransshipmentIntervals = (container.getIncDate().getYear() == 121)
                                        ? YMLContainerIntervals.fullTransshipmentIntervals2021()
                                        : YMLContainerIntervals.fullTransshipmentIntervals();

                        // fullReeferTransshipmentIntervals
                        fullReeferTransshipmentIntervals = (container.getIncDate().getYear() == 121)
                                        ? YMLContainerIntervals.fullTransshipmentIntervals2021()
                                        : YMLContainerIntervals.fullTransshipmentIntervals();

                        // fullImportIntervals
                        fullImportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? YMLContainerIntervals.fullImportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? YMLContainerIntervals
                                                                                        .fullImportIntervals2023()
                                                                        : YMLContainerIntervals
                                                                                        .fullImportIntervals2021())
                                        : YMLContainerIntervals.fullImportIntervals2024();

                        // fullExportIntervals
                        fullExportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? YMLContainerIntervals.fullExportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? YMLContainerIntervals
                                                                                        .fullExportIntervals2023()
                                                                        : YMLContainerIntervals
                                                                                        .fullExportIntervals2021())
                                        : YMLContainerIntervals.fullExportIntervals2024();

                        // fullReeferDirectIntervals
                        fullReeferDirectIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? YMLContainerIntervals.fullReeferDirectIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? YMLContainerIntervals
                                                                                        .fullReeferDirectIntervals2023()
                                                                        : YMLContainerIntervals
                                                                                        .fullReeferDirectIntervals2021())
                                        : YMLContainerIntervals.fullReeferDirectIntervals2024();

                        // Hazardous import Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                hazardousImportTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getYMLContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousImportQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // Hazardous Export Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                hazardousExportTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getYMLContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousExportQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // Hazardous Transshipment Surcharge
                        if (shippingLinesInvoicing.imdg
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                hazardousTranssipmentTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getYMLContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousTransshipmentQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // reeferConnectionTransshipment & Direct Total surchagre
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {

                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                fullReeferTransshipmentIntervals, month,
                                                year).stream().mapToInt(Integer::intValue).sum();
                                if (container.isReef() && container.getInvoiceCategory().equals("Transshipment")) {
                                        if (theYear == 24) {
                                                reeferConnectionTransshipmentTotal_2024 += numOfDays * 55;
                                                reeferConnectionTransshipmentQuantity_2024 += numOfDays;
                                        } else {
                                                reeferConnectionTransshipmentTotal += numOfDays * 45;
                                                reeferConnectionTransshipmentQuantity += numOfDays;
                                        }

                                }
                                if (container.isReef() && container.getInvoiceCategory().equals("Import")) {
                                        if (theYear == 24) {
                                                reeferConnectionImportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionImportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionImportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity += numOfDays;
                                        }

                                }
                                if (container.isReef() && container.getInvoiceCategory().equals("Export")) {
                                        if (theYear == 24) {
                                                reeferConnectionExportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionExportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionExportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity += numOfDays;
                                        }

                                }
                        }
                        // storageOfDamagedContainers surcharge
                        if (shippingLinesInvoicing.dmg) {
                                storageOfDamagedContainersTotal += shippingLinesInvoicing.dmgSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getYMLContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfDamagedContainersQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // storageOfOOGContainers surchagre
                        if (shippingLinesInvoicing.oog) {
                                storageOfOOGContainersTotal += shippingLinesInvoicing.oogSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getYMLContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfOOGContainersQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // storageOfTankContainers surchagre
                        if (shippingLinesInvoicing.type.equals("TK") || shippingLinesInvoicing.type.equals("TO")) {
                                storageOfTankContainersTotal += shippingLinesInvoicing.tankSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getYMLContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfTankContainersQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // storageFullExportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2024[0] += price;
                                        storageFullExportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2024[1] += price;
                                        storageFullExportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2024[2] += price;
                                        storageFullExportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2023[0] += price;
                                        storageFullExportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2023[1] += price;
                                        storageFullExportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2023[2] += price;
                                        storageFullExportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal[0] += price;
                                        storageFullExportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal[1] += price;
                                        storageFullExportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal[2] += price;
                                        storageFullExportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }

                        }

                        // storageFullImportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2024[0] += price;
                                        storageFullImportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2024[1] += price;
                                        storageFullImportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2024[2] += price;
                                        storageFullImportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2023[0] += price;
                                        storageFullImportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2023[1] += price;
                                        storageFullImportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2023[2] += price;
                                        storageFullImportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal[0] += price;
                                        storageFullImportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal[1] += price;
                                        storageFullImportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal[2] += price;
                                        storageFullImportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }

                        }

                        // storageFullTransshipmentInervalsTotal
                        if (!shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                * fullTransshipmentIntervals.get(0).getPrice();
                                storageFullTransshipmentInervalsTotal[0] += price;
                                storageFullTransshipmentInervalsQuantities[0] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                * fullTransshipmentIntervals.get(1).getPrice();
                                storageFullTransshipmentInervalsTotal[1] += price;
                                storageFullTransshipmentInervalsQuantities[1] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                * fullTransshipmentIntervals.get(2).getPrice();
                                storageFullTransshipmentInervalsTotal[2] += price;
                                storageFullTransshipmentInervalsQuantities[2] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                                                * fullTransshipmentIntervals.get(3).getPrice();
                                storageFullTransshipmentInervalsTotal[3] += price;
                                storageFullTransshipmentInervalsQuantities[3] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(3)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(4) * numberOfTeus
                                                * fullTransshipmentIntervals.get(4).getPrice();
                                storageFullTransshipmentInervalsTotal[4] += price;
                                storageFullTransshipmentInervalsQuantities[4] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(4)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(5) * numberOfTeus
                                                * fullTransshipmentIntervals.get(5).getPrice();
                                storageFullTransshipmentInervalsTotal[5] += price;
                                storageFullTransshipmentInervalsQuantities[5] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(5)
                                                : 0;
                        }

                        // storageFullReeferTransshipmentInervals
                        if (shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullReeferTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                * fullReeferTransshipmentIntervals.get(0).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[0] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[0] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(0);

                                price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                * fullReeferTransshipmentIntervals.get(1).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[1] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[1] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(1);

                                price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                * fullReeferTransshipmentIntervals.get(2).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[2] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[2] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(2);

                                price = numberOfDaysForEachIntervalInMonthList.get(3)
                                                * fullReeferTransshipmentIntervals.get(3).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[3] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[3] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(3);

                                price = numberOfDaysForEachIntervalInMonthList.get(4)
                                                * fullReeferTransshipmentIntervals.get(4).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[4] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[4] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(4);

                                price = numberOfDaysForEachIntervalInMonthList.get(5)
                                                * fullReeferTransshipmentIntervals.get(5).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[5] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[5] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(5);

                        }

                        // storageFullReeferExportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                                else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                        // storageFullReeferImportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                }
                content += "Description;Customer;Unit Measure;Price per Unit;Quantity;Tot Amount\n";
                if (hazardousImportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "23.61 EUR" + ";" +
                                        hazardousImportQuantity + ";" + df.format((hazardousImportTotal)) + "\n";

                if (hazardousExportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "23.61 EUR" + ";" +
                                        hazardousExportQuantity + ";" + df.format(hazardousExportTotal) + "\n";

                if (hazardousTranssipmentTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "23.61 EUR" + ";" +
                                        hazardousTransshipmentQuantity + ";" + df.format(hazardousTranssipmentTotal)
                                        + "\n";

                if (hazardousImportTotal != 0 || hazardousExportTotal != 0 || hazardousTranssipmentTotal != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (reeferConnectionTransshipmentTotal != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "45 EUR (2023)" + ";" +
                                        reeferConnectionTransshipmentQuantity + ";"
                                        + df.format(reeferConnectionTransshipmentTotal) + "\n";
                if (reeferConnectionTransshipmentTotal_2024 != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "55 EUR (2024)" + ";" +
                                        reeferConnectionTransshipmentQuantity_2024 + ";"
                                        + df.format(reeferConnectionTransshipmentTotal_2024) + "\n";

                if (reeferConnectionImportTotal != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "35.53 EUR" + ";" +
                                        reeferConnectionImportQuantity + ";" + df.format(reeferConnectionImportTotal)
                                        + "\n";

                if (reeferConnectionImportTotal_2023 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionImportQuantity_2023 + ";"
                                        + df.format(reeferConnectionImportTotal_2023)
                                        + "\n";
                if (reeferConnectionImportTotal_2024 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.96 EUR (2024)" + ";" +
                                        reeferConnectionImportQuantity_2024 + ";"
                                        + df.format(reeferConnectionImportTotal_2024)
                                        + "\n";

                if (reeferConnectionExportTotal != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "35.53 EUR" + ";" +
                                        reeferConnectionExportQuantity + ";" + df.format(reeferConnectionExportTotal)
                                        + "\n";

                if (reeferConnectionExportTotal_2023 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionExportQuantity_2023 + ";"
                                        + df.format(reeferConnectionExportTotal_2023)
                                        + "\n";
                if (reeferConnectionExportTotal_2024 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.96 EUR (2024)" + ";" +
                                        reeferConnectionExportQuantity_2024 + ";"
                                        + df.format(reeferConnectionExportTotal_2024)
                                        + "\n";

                if (reeferConnectionTransshipmentTotal != 0 ||
                                reeferConnectionTransshipmentTotal_2024 != 0 ||
                                reeferConnectionImportTotal != 0 ||
                                reeferConnectionExportTotal != 0 ||
                                reeferConnectionImportTotal_2023 != 0 ||
                                reeferConnectionExportTotal_2023 != 0 ||
                                reeferConnectionImportTotal_2024 != 0 ||
                                reeferConnectionExportTotal_2024 != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfDamagedContainersTotal != 0) {
                        content += "Storage damaged Containers surcharge  " + ";" + "YML" + ";" + "Quote %200" + ";"
                                        + "" + ";" +
                                        storageOfDamagedContainersQuantity + ";"
                                        + df.format(storageOfDamagedContainersTotal) + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullExportInervalsTotal[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 0 EUR;"
                                        + storageFullExportInervalsQuantities[0] + ";" +
                                        df.format(storageFullExportInervalsTotal[0]) + "\n";

                if (storageFullExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 0 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[0]) + "\n";
                if (storageFullExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 0 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[0]) + "\n";

                if (storageFullExportInervalsTotal[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 1.43 EUR;"
                                        + storageFullExportInervalsQuantities[1] + ";" +
                                        df.format(storageFullExportInervalsTotal[1]) + "\n";
                if (storageFullExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 1.46 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[1]) + "\n";
                if (storageFullExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 1.49 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[1]) + "\n";

                if (storageFullExportInervalsTotal[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 3.86 EUR;"
                                        + storageFullExportInervalsQuantities[2] + ";" +
                                        df.format(storageFullExportInervalsTotal[2]) + "\n";
                if (storageFullExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 3.94 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[2]) + "\n";
                if (storageFullExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 4.02 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[2]) + "\n";

                if (storageFullExportInervalsTotal[0] != 0 || storageFullExportInervalsTotal[1] != 0
                                || storageFullExportInervalsTotal[2] != 0 || storageFullExportInervalsTotal_2023[0] != 0
                                || storageFullExportInervalsTotal_2023[1] != 0
                                || storageFullExportInervalsTotal_2023[2] != 0
                                || storageFullExportInervalsTotal_2024[0] != 0
                                || storageFullExportInervalsTotal_2024[1] != 0
                                || storageFullExportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullImportInervalsTotal[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 0 EUR;"
                                        + storageFullImportInervalsQuantities[0] + ";" +
                                        df.format(storageFullImportInervalsTotal[0]) + "\n";
                if (storageFullImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 0 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[0]) + "\n";
                if (storageFullImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 0 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[0]) + "\n";

                if (storageFullImportInervalsTotal[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 1.43 EUR;"
                                        + storageFullImportInervalsQuantities[1] + ";" +
                                        df.format(storageFullImportInervalsTotal[1]) + "\n";
                if (storageFullImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 1.46 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[1]) + "\n";
                if (storageFullImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 1.49 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[1]) + "\n";

                if (storageFullImportInervalsTotal[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 3.86 EUR;"
                                        + storageFullImportInervalsQuantities[2] + ";" +
                                        df.format(storageFullImportInervalsTotal[2]) + "\n";
                if (storageFullImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 3.94 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[2]) + "\n";
                if (storageFullImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "YML" + ";" + "Tarif/Unit/Day"
                                        + "; 8.02 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[2]) + "\n";

                if (storageFullImportInervalsTotal[0] != 0 || storageFullImportInervalsTotal[1] != 0
                                || storageFullImportInervalsTotal[2] != 0 || storageFullImportInervalsTotal_2023[0] != 0
                                || storageFullImportInervalsTotal_2023[1] != 0
                                || storageFullImportInervalsTotal_2023[2] != 0
                                || storageFullImportInervalsTotal_2024[0] != 0
                                || storageFullImportInervalsTotal_2024[1] != 0
                                || storageFullImportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Transshipment First Period Revenue  " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        +
                                        fullTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[0]) + "\n";

                if (storageFullTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Transshipment Second Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        +
                                        fullTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[1]) + "\n";

                if (storageFullTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Transshipment Third Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";" +
                                        fullTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[2]) + "\n";

                if (storageFullTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        +
                                        fullTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[3]) + "\n";

                if (storageFullTransshipmentInervalsTotal[4] != 0)
                        content += "Storage Full Transshipment Fifth Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";" +
                                        fullTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[4] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[4]) + "\n";

                if (storageFullTransshipmentInervalsTotal[5] != 0)
                        content += "Storage Full Transshipment Sixth Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + ";" +
                                        fullTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[5] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[5]) + "\n";

                if (storageFullTransshipmentInervalsTotal[0] != 0 ||
                                storageFullTransshipmentInervalsTotal[1] != 0 ||
                                storageFullTransshipmentInervalsTotal[2] != 0 ||
                                storageFullTransshipmentInervalsTotal[3] != 0 ||
                                storageFullTransshipmentInervalsTotal[4] != 0 ||
                                storageFullTransshipmentInervalsTotal[5] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[0]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[1]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[2]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[3]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[4] != 0)
                        content += "Storage Full Reefer Transshipment Fifth Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[4] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[4]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[5] != 0)
                        content += "Storage Full Reefer Transshipment Sixth Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[5] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[5]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[1] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[2] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[3] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[4] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[5] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferExportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + storageFullReeferExportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferExportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + storageFullReeferExportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 2.96 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferExportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 7.71 EUR;"
                                        + storageFullReeferExportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[2]) + "\n";

                if (storageFullReeferExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 7.86 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 8.02 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferExportInervalsTotal[0] != 0 ||
                                storageFullReeferExportInervalsTotal[1] != 0 ||
                                storageFullReeferExportInervalsTotal[2] != 0
                                || storageFullReeferExportInervalsTotal_2023[0] != 0 ||
                                storageFullReeferExportInervalsTotal_2023[1] != 0 ||
                                storageFullReeferExportInervalsTotal_2023[2] != 0
                                || storageFullReeferExportInervalsTotal_2024[0] != 0 ||
                                storageFullReeferExportInervalsTotal_2024[1] != 0 ||
                                storageFullReeferExportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferImportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + storageFullReeferImportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferImportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + storageFullReeferImportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 2.96 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferImportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 7.71 EUR;"
                                        + storageFullReeferImportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 7.86 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "YML" + ";"
                                        + "Tarif/Unit/Day" + "; 8.02 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferImportInervalsTotal[0] != 0 ||
                                storageFullReeferImportInervalsTotal[1] != 0 ||
                                storageFullReeferImportInervalsTotal[2] != 0
                                || storageFullReeferImportInervalsTotal_2023[0] != 0 ||
                                storageFullReeferImportInervalsTotal_2023[1] != 0 ||
                                storageFullReeferImportInervalsTotal_2023[2] != 0
                                || storageFullReeferImportInervalsTotal_2024[0] != 0 ||
                                storageFullReeferImportInervalsTotal_2024[1] != 0 ||
                                storageFullReeferImportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfOOGContainersTotal != 0) {
                        content += "Storage surcharge Non-Standard Container " + ";" + "YML" + ";" + "Quote %100" + ";"
                                        + "" + ";" +
                                        storageOfOOGContainersQuantity + ";" +
                                        df.format(storageOfOOGContainersTotal) + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfTankContainersTotal != 0) {
                        content += "Storage tank Containers surcharge " + ";" + "YML" + ";" + "Quote %100" + ";" + ""
                                        + ";"
                                        + storageOfTankContainersQuantity + ";" +
                                        df.format(storageOfTankContainersTotal) + "\n";
                        content += " ; ; ; ; ; \n";
                }

                double total = hazardousImportTotal
                                + hazardousImportTotal_2023
                                + hazardousImportTotal_2024
                                + hazardousExportTotal
                                + hazardousTranssipmentTotal
                                + reeferConnectionTransshipmentTotal
                                + reeferConnectionTransshipmentTotal_2024
                                + reeferConnectionImportTotal
                                + reeferConnectionExportTotal
                                + reeferConnectionImportTotal_2023
                                + reeferConnectionImportTotal_2024
                                + reeferConnectionExportTotal_2023
                                + reeferConnectionExportTotal_2024
                                + storageOfDamagedContainersTotal
                                + storageFullExportInervalsTotal[0]
                                + storageFullExportInervalsTotal[1]
                                + storageFullExportInervalsTotal[2]
                                + storageFullImportInervalsTotal[0]
                                + storageFullImportInervalsTotal[1]
                                + storageFullImportInervalsTotal[2]
                                + storageFullExportInervalsTotal_2023[0]
                                + storageFullExportInervalsTotal_2024[0]
                                + storageFullExportInervalsTotal_2023[1]
                                + storageFullExportInervalsTotal_2024[1]
                                + storageFullExportInervalsTotal_2023[2]
                                + storageFullExportInervalsTotal_2024[2]
                                + storageFullImportInervalsTotal_2023[0]
                                + storageFullImportInervalsTotal_2024[0]
                                + storageFullImportInervalsTotal_2023[1]
                                + storageFullImportInervalsTotal_2024[1]
                                + storageFullImportInervalsTotal_2023[2]
                                + storageFullImportInervalsTotal_2024[2]
                                + storageFullTransshipmentInervalsTotal[0]
                                + storageFullTransshipmentInervalsTotal[1]
                                + storageFullTransshipmentInervalsTotal[2]
                                + storageFullTransshipmentInervalsTotal[3]
                                + storageFullTransshipmentInervalsTotal[4]
                                + storageFullTransshipmentInervalsTotal[5]
                                + storageFullReeferTransshipmentInervalsTotal[0]
                                + storageFullReeferTransshipmentInervalsTotal[1]
                                + storageFullReeferTransshipmentInervalsTotal[2]
                                + storageFullReeferTransshipmentInervalsTotal[3]
                                + storageFullReeferTransshipmentInervalsTotal[4]
                                + storageFullReeferTransshipmentInervalsTotal[5]
                                + storageFullReeferExportInervalsTotal[0]
                                + storageFullReeferExportInervalsTotal[1]
                                + storageFullReeferExportInervalsTotal[2]
                                + storageFullReeferImportInervalsTotal[0]
                                + storageFullReeferImportInervalsTotal[1]
                                + storageFullReeferImportInervalsTotal[2]
                                + storageFullReeferExportInervalsTotal_2023[0]
                                + storageFullReeferExportInervalsTotal_2024[0]
                                + storageFullReeferExportInervalsTotal_2023[1]
                                + storageFullReeferExportInervalsTotal_2024[1]
                                + storageFullReeferExportInervalsTotal_2023[2]
                                + storageFullReeferExportInervalsTotal_2024[2]
                                + storageFullReeferImportInervalsTotal_2023[0]
                                + storageFullReeferImportInervalsTotal_2024[0]
                                + storageFullReeferImportInervalsTotal_2023[1]
                                + storageFullReeferImportInervalsTotal_2024[1]
                                + storageFullReeferImportInervalsTotal_2023[2]
                                + storageFullReeferImportInervalsTotal_2024[2]
                                + storageOfOOGContainersTotal
                                + storageOfOOGContainersTotal_2023
                                + storageOfOOGContainersTotal_2024
                                + storageOfTankContainersTotal
                                + storageOfTankContainersTotal_2023
                                + storageOfTankContainersTotal_2024;
                /*
                 * +emptyDepotContainersTotal
                 * +emptyExportContainersTotal
                 * +emptyImportContainersTotal
                 * +emptyTransshipmentContainersTotal[0]
                 * +emptyTransshipmentContainersTotal[1]
                 * +emptyTransshipmentContainersTotal[2]
                 * +emptyTransshipmentContainersTotal[3];
                 */
                content += "Total " + ";" + " " + ";" + " " + ";" + " " + ";" + ";" + df.format(total) + "\n";

                return content;

        }

        public static String ONESumMonthlyStorageDetails(String month, String year) throws ParseException, IOException {
                fw = null;
                bw = null;
                String content = "";
                hazardousImportTotal = 0;
                hazardousImportTotal_2023 = 0;
                hazardousImportTotal_2024 = 0;
                hazardousImportQuantity = 0;
                hazardousImportQuantity_2023 = 0;
                hazardousImportQuantity_2024 = 0;

                hazardousExportTotal = 0;
                hazardousExportQuantity = 0;
                hazardousExportTotal_2023 = 0;
                hazardousExportTotal_2024 = 0;
                hazardousExportQuantity_2023 = 0;
                hazardousExportQuantity_2024 = 0;

                hazardousTranssipmentTotal = 0;
                hazardousTransshipmentQuantity = 0;
                hazardousTranssipmentTotal_2023 = 0;
                hazardousTranssipmentTotal_2024 = 0;
                hazardousTransshipmentQuantity_2023 = 0;
                hazardousTransshipmentQuantity_2024 = 0;

                reeferConnectionTransshipmentTotal = 0;
                reeferConnectionTransshipmentQuantity = 0;
                reeferConnectionTransshipmentTotal_2023 = 0;
                reeferConnectionTransshipmentTotal_2024 = 0;
                reeferConnectionTransshipmentQuantity_2023 = 0;
                reeferConnectionTransshipmentQuantity_2024 = 0;

                reeferConnectionImportTotal = 0;
                reeferConnectionImportQuantity = 0;
                reeferConnectionImportTotal_2023 = 0;
                reeferConnectionImportTotal_2024 = 0;
                reeferConnectionImportQuantity_2023 = 0;
                reeferConnectionImportQuantity_2024 = 0;

                reeferConnectionExportTotal = 0;
                reeferConnectionExportQuantity = 0;
                reeferConnectionImportExportPrice = 0;
                reeferConnectionExportTotal_2023 = 0;
                reeferConnectionExportTotal_2024 = 0;
                reeferConnectionExportQuantity_2023 = 0;
                reeferConnectionExportQuantity_2024 = 0;
                reeferConnectionImportExportPrice_2023 = 0;
                reeferConnectionImportExportPrice_2024 = 0;

                storageOfDamagedContainersTotal = 0;
                storageOfDamagedContainersQuantity = 0;
                storageOfDamagedContainersTotal_2023 = 0;
                storageOfDamagedContainersTotal_2024 = 0;
                storageOfDamagedContainersQuantity_2023 = 0;
                storageOfDamagedContainersQuantity_2024 = 0;

                storageFullExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                storageFullReeferTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                storageFullReeferExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullReeferImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageOfOOGContainersTotal = 0;
                storageOfOOGContainersQuantity = 0;

                storageOfTankContainersTotal = 0;
                storageOfTankContainersQuantity = 0;

                emptyDepotContainersTotal = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities = new int[] { 0, 0, 0, 0 };

                /*
                 * emptyExportContainersTotal=0;
                 * emptyExportContainersQuantity=0;
                 * 
                 * 
                 * emptyImportContainersTotal=0;
                 * emptyImportContainersQuantity=0;
                 */

                emptyTransshipmentContainersTotal = new double[] { 0, 0, 0, 0 };

                List<Interval> fullExportIntervals = new ArrayList<>();
                List<Interval> fullImportIntervals = new ArrayList<>();
                List<Interval> fullTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferDirectIntervals = new ArrayList<>();
                List<Interval> emptyImportOrExportIntervals = new ArrayList<>();

                // fullTransshipmentIntervals
                fullTransshipmentIntervals = ONEContainerIntervals.fullTransshipmentIntervals();

                // fullReeferTransshipmentIntervals
                fullReeferTransshipmentIntervals = ONEContainerIntervals.fullTransshipmentIntervals();

                // fullImportIntervals
                fullImportIntervals = ONEContainerIntervals.fullImportIntervals();

                // fullExportIntervals
                fullExportIntervals = ONEContainerIntervals.fullIExportIntervals();

                // fullReeferDirectIntervals
                fullReeferDirectIntervals = ONEContainerIntervals.fullReeferDirectIntervals();

                // emptyImportOrExportIntervals
                emptyImportOrExportIntervals = StandardContainerIntervals.emptyExportIntervals();

                for (ContainerStorageDetailsDTO shippingLinesInvoicing : shippingLinesInvoicingList) {
                        Container container = convertToContainer(shippingLinesInvoicing);
                        int theYear = container.getIncDate().getYear() - 100;

                        reeferConnectionImportExportPrice = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 123) ? 36.24 : 35.53)
                                        : 36.96;

                        // fullTransshipmentIntervals
                        fullTransshipmentIntervals = (container.getIncDate().getYear() == 121)
                                        ? ONEContainerIntervals.fullTransshipmentIntervals2021()
                                        : ONEContainerIntervals.fullTransshipmentIntervals();

                        // fullReeferTransshipmentIntervals
                        fullReeferTransshipmentIntervals = (container.getIncDate().getYear() == 121)
                                        ? ONEContainerIntervals.fullTransshipmentIntervals2021()
                                        : ONEContainerIntervals.fullTransshipmentIntervals();

                        // fullImportIntervals
                        fullImportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ONEContainerIntervals.fullImportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ONEContainerIntervals
                                                                                        .fullImportIntervals2023()
                                                                        : ONEContainerIntervals
                                                                                        .fullImportIntervals2021())
                                        : ONEContainerIntervals.fullImportIntervals2024();

                        // fullExportIntervals
                        fullExportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ONEContainerIntervals.fullIExportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ONEContainerIntervals
                                                                                        .fullIExportIntervals2023()
                                                                        : ONEContainerIntervals
                                                                                        .fullIExportIntervals2021())
                                        : ONEContainerIntervals.fullIExportIntervals2024();

                        // fullReeferDirectIntervals
                        fullReeferDirectIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? ONEContainerIntervals.fullReeferDirectIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? ONEContainerIntervals
                                                                                        .fullReeferDirectIntervals2023()
                                                                        : ONEContainerIntervals
                                                                                        .fullReeferDirectIntervals2021())
                                        : ONEContainerIntervals.fullReeferDirectIntervals2024();

                        // Hazardous import Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                hazardousImportTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getONEContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousImportQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // Hazardous Export Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                hazardousExportTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getONEContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousExportQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // Hazardous Transshipment Surcharge
                        if (shippingLinesInvoicing.imdg
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                hazardousTranssipmentTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getONEContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousTransshipmentQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // reeferConnectionTransshipment & Direct Total surchagre
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {

                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                fullReeferTransshipmentIntervals, month,
                                                year).stream().mapToInt(Integer::intValue).sum();
                                if (container.isReef() && container.getInvoiceCategory().equals("Transshipment")) {
                                        if (theYear == 24) {

                                                reeferConnectionTransshipmentTotal_2024 += numOfDays * 50;
                                                reeferConnectionTransshipmentQuantity_2024 += numOfDays;
                                        } else {

                                                reeferConnectionTransshipmentTotal += numOfDays * 45;
                                                reeferConnectionTransshipmentQuantity += numOfDays;
                                        }

                                }
                                if (container.isReef() && container.getInvoiceCategory().equals("Import")) {
                                        if (theYear == 24) {
                                                reeferConnectionImportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionImportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionImportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity += numOfDays;
                                        }

                                }
                                if (container.isReef() && container.getInvoiceCategory().equals("Export")) {
                                        if (theYear == 24) {
                                                reeferConnectionExportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionExportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionExportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity += numOfDays;
                                        }

                                }
                        }
                        // storageOfDamagedContainers surcharge
                        if (shippingLinesInvoicing.dmg) {
                                storageOfDamagedContainersTotal += shippingLinesInvoicing.dmgSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getONEContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfDamagedContainersQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // storageOfOOGContainers surchagre
                        if (shippingLinesInvoicing.oog) {
                                storageOfOOGContainersTotal += shippingLinesInvoicing.oogSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getONEContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfOOGContainersQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // storageOfTankContainers surchagre
                        if (shippingLinesInvoicing.type.equals("TK") || shippingLinesInvoicing.type.equals("TO")) {
                                storageOfTankContainersTotal += shippingLinesInvoicing.tankSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getONEContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfTankContainersQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // storageFullExportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2024[0] += price;
                                        storageFullExportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2024[1] += price;
                                        storageFullExportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2024[2] += price;
                                        storageFullExportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2023[0] += price;
                                        storageFullExportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2023[1] += price;
                                        storageFullExportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2023[2] += price;
                                        storageFullExportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal[0] += price;
                                        storageFullExportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal[1] += price;
                                        storageFullExportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal[2] += price;
                                        storageFullExportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }
                        }

                        // storageFullImportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2024[0] += price;
                                        storageFullImportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2024[1] += price;
                                        storageFullImportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2024[2] += price;
                                        storageFullImportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }
                                if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2023[0] += price;
                                        storageFullImportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2023[1] += price;
                                        storageFullImportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2023[2] += price;
                                        storageFullImportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal[0] += price;
                                        storageFullImportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal[1] += price;
                                        storageFullImportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal[2] += price;
                                        storageFullImportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }

                        }

                        // storageFullTransshipmentInervalsTotal
                        if (!shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                * fullTransshipmentIntervals.get(0).getPrice();
                                storageFullTransshipmentInervalsTotal[0] += price;
                                storageFullTransshipmentInervalsQuantities[0] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                * fullTransshipmentIntervals.get(1).getPrice();
                                storageFullTransshipmentInervalsTotal[1] += price;
                                storageFullTransshipmentInervalsQuantities[1] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                * fullTransshipmentIntervals.get(2).getPrice();
                                storageFullTransshipmentInervalsTotal[2] += price;
                                storageFullTransshipmentInervalsQuantities[2] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                                                * fullTransshipmentIntervals.get(3).getPrice();
                                storageFullTransshipmentInervalsTotal[3] += price;
                                storageFullTransshipmentInervalsQuantities[3] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(3)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(4) * numberOfTeus
                                                * fullTransshipmentIntervals.get(4).getPrice();
                                storageFullTransshipmentInervalsTotal[4] += price;
                                storageFullTransshipmentInervalsQuantities[4] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(4)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(5) * numberOfTeus
                                                * fullTransshipmentIntervals.get(5).getPrice();
                                storageFullTransshipmentInervalsTotal[5] += price;
                                storageFullTransshipmentInervalsQuantities[5] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(5)
                                                : 0;
                        }

                        // storageFullReeferTransshipmentInervals
                        if (shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullReeferTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                * fullReeferTransshipmentIntervals.get(0).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[0] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[0] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(0);

                                price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                * fullReeferTransshipmentIntervals.get(1).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[1] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[1] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(1);

                                price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                * fullReeferTransshipmentIntervals.get(2).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[2] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[2] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(2);

                                price = numberOfDaysForEachIntervalInMonthList.get(3)
                                                * fullReeferTransshipmentIntervals.get(3).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[3] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[3] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(3);

                                price = numberOfDaysForEachIntervalInMonthList.get(4)
                                                * fullReeferTransshipmentIntervals.get(4).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[4] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[4] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(4);

                                price = numberOfDaysForEachIntervalInMonthList.get(5)
                                                * fullReeferTransshipmentIntervals.get(5).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[5] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[5] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(5);

                        }

                        // storageFullReeferExportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                        // storageFullReeferImportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                }

                content += "Description;Customer;Unit Measure;Price per Unit;Quantity;Tot Amount\n";
                if (hazardousImportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day" + ";"
                                        + "23.61 EUR" + ";" +
                                        hazardousImportQuantity + ";" + df.format((hazardousImportTotal)) + "\n";

                if (hazardousExportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day" + ";"
                                        + "23.61 EUR" + ";" +
                                        hazardousExportQuantity + ";" + df.format(hazardousExportTotal) + "\n";

                if (hazardousTranssipmentTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" + "23.61 EUR" + ";" +
                                        hazardousTransshipmentQuantity + ";" + df.format(hazardousTranssipmentTotal)
                                        + "\n";

                if (hazardousImportTotal != 0 || hazardousExportTotal != 0 || hazardousTranssipmentTotal != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (reeferConnectionTransshipmentTotal != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "45 EUR (2023)" + ";" +
                                        reeferConnectionTransshipmentQuantity + ";"
                                        + df.format(reeferConnectionTransshipmentTotal) + "\n";
                if (reeferConnectionTransshipmentTotal_2024 != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "50 EUR (2024)" + ";" +
                                        reeferConnectionTransshipmentQuantity_2024 + ";"
                                        + df.format(reeferConnectionTransshipmentTotal_2024) + "\n";

                if (reeferConnectionImportTotal != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "35.53 EUR" + ";" +
                                        reeferConnectionImportQuantity + ";" + df.format(reeferConnectionImportTotal)
                                        + "\n";
                if (reeferConnectionImportTotal_2023 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionImportQuantity_2023 + ";"
                                        + df.format(reeferConnectionImportTotal_2023)
                                        + "\n";
                if (reeferConnectionImportTotal_2024 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.96 EUR (2024)" + ";" +
                                        reeferConnectionImportQuantity_2024 + ";"
                                        + df.format(reeferConnectionImportTotal_2024)
                                        + "\n";

                if (reeferConnectionExportTotal != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "35.53 EUR" + ";" +
                                        reeferConnectionExportQuantity + ";" + df.format(reeferConnectionExportTotal)
                                        + "\n";
                if (reeferConnectionExportTotal_2023 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionExportQuantity_2023 + ";"
                                        + df.format(reeferConnectionExportTotal_2023)
                                        + "\n";
                if (reeferConnectionExportTotal_2024 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + ";"
                                        + "36.96 EUR (2024)" + ";" +
                                        reeferConnectionExportQuantity_2024 + ";"
                                        + df.format(reeferConnectionExportTotal_2024)
                                        + "\n";

                if (reeferConnectionTransshipmentTotal != 0 ||
                                reeferConnectionTransshipmentTotal_2024 != 0 ||
                                reeferConnectionImportTotal != 0 ||
                                reeferConnectionExportTotal != 0 ||
                                reeferConnectionImportTotal_2023 != 0 ||
                                reeferConnectionExportTotal_2023 != 0 ||
                                reeferConnectionImportTotal_2024 != 0 ||
                                reeferConnectionExportTotal_2024 != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfDamagedContainersTotal != 0)
                        content += "Storage damaged Containers surcharge  " + ";" + "ONE" + ";" + "Quote %200" + ";"
                                        + "" + ";" +
                                        storageOfDamagedContainersQuantity + ";"
                                        + df.format(storageOfDamagedContainersTotal) + "\n";

                if (storageFullExportInervalsTotal[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR;"
                                        + storageFullExportInervalsQuantities[0] + ";" +
                                        df.format(storageFullExportInervalsTotal[0]) + "\n";
                if (storageFullExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[0]) + "\n";
                if (storageFullExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[0]) + "\n";

                if (storageFullExportInervalsTotal[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 1.43 EUR;"
                                        + storageFullExportInervalsQuantities[1] + ";" +
                                        df.format(storageFullExportInervalsTotal[1]) + "\n";
                if (storageFullExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 1.46 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[1]) + "\n";
                if (storageFullExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 1.49 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[1]) + "\n";

                if (storageFullExportInervalsTotal[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 3.86 EUR;"
                                        + storageFullExportInervalsQuantities[2] + ";" +
                                        df.format(storageFullExportInervalsTotal[2]) + "\n";
                if (storageFullExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 3.94 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[2]) + "\n";
                if (storageFullExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 4.02 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[2]) + "\n";

                if (storageFullExportInervalsTotal[0] != 0 || storageFullExportInervalsTotal[1] != 0
                                || storageFullExportInervalsTotal[2] != 0 || storageFullExportInervalsTotal_2023[0] != 0
                                || storageFullExportInervalsTotal_2023[1] != 0
                                || storageFullExportInervalsTotal_2023[2] != 0
                                || storageFullExportInervalsTotal_2024[0] != 0
                                || storageFullExportInervalsTotal_2024[1] != 0
                                || storageFullExportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullImportInervalsTotal[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR;"
                                        + storageFullImportInervalsQuantities[0] + ";" +
                                        df.format(storageFullImportInervalsTotal[0]) + "\n";
                if (storageFullImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[0]) + "\n";
                if (storageFullImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 0 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[0]) + "\n";

                if (storageFullImportInervalsTotal[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 1.43 EUR;"
                                        + storageFullImportInervalsQuantities[1] + ";" +
                                        df.format(storageFullImportInervalsTotal[1]) + "\n";
                if (storageFullImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 1.46 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[1]) + "\n";
                if (storageFullImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 1.49 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[1]) + "\n";

                if (storageFullImportInervalsTotal[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 3.86 EUR;"
                                        + storageFullImportInervalsQuantities[2] + ";" +
                                        df.format(storageFullImportInervalsTotal[2]) + "\n";
                if (storageFullImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 3.94 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[2]) + "\n";
                if (storageFullImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + "ONE" + ";" + "Tarif/Teu/Day"
                                        + "; 4.02 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[2]) + "\n";

                if (storageFullImportInervalsTotal[0] != 0 || storageFullImportInervalsTotal[1] != 0
                                || storageFullImportInervalsTotal[2] != 0 || storageFullImportInervalsTotal_2023[0] != 0
                                || storageFullImportInervalsTotal_2023[1] != 0
                                || storageFullImportInervalsTotal_2023[2] != 0
                                || storageFullImportInervalsTotal_2024[0] != 0
                                || storageFullImportInervalsTotal_2024[1] != 0
                                || storageFullImportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Transshipment First Period Revenue  " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[0]) + "\n";

                if (storageFullTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Transshipment Second Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[1]) + "\n";

                if (storageFullTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Transshipment Third Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[2]) + "\n";

                if (storageFullTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[3]) + "\n";

                if (storageFullTransshipmentInervalsTotal[4] != 0)
                        content += "Storage Full Transshipment Fifth Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[4] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[4]) + "\n";

                if (storageFullTransshipmentInervalsTotal[5] != 0)
                        content += "Storage Full Transshipment Sixth Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[5] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[5]) + "\n";

                if (storageFullTransshipmentInervalsTotal[0] != 0 ||
                                storageFullTransshipmentInervalsTotal[1] != 0 ||
                                storageFullTransshipmentInervalsTotal[2] != 0 ||
                                storageFullTransshipmentInervalsTotal[3] != 0 ||
                                storageFullTransshipmentInervalsTotal[4] != 0 ||
                                storageFullTransshipmentInervalsTotal[5] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[0]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[1]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[2]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[3]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[4] != 0)
                        content += "Storage Full Reefer Transshipment Fifth Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[4] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[4]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[5] != 0)
                        content += "Storage Full Reefer Transshipment Sixth Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullReeferTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[5] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[5]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[1] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[2] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[3] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[4] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[5] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferExportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + storageFullReeferExportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferExportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + storageFullReeferExportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 2.98 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferExportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 7.71 EUR;"
                                        + storageFullReeferExportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 7.86 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 8.02 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferExportInervalsTotal[0] != 0 ||
                                storageFullReeferExportInervalsTotal[1] != 0 ||
                                storageFullReeferExportInervalsTotal[2] != 0
                                || storageFullReeferExportInervalsTotal_2023[0] != 0 ||
                                storageFullReeferExportInervalsTotal_2023[1] != 0 ||
                                storageFullReeferExportInervalsTotal_2023[2] != 0
                                || storageFullReeferExportInervalsTotal_2024[0] != 0 ||
                                storageFullReeferExportInervalsTotal_2024[1] != 0 ||
                                storageFullReeferExportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullReeferImportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + storageFullReeferImportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[0]) + "\n";

                if (storageFullReeferImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferImportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + storageFullReeferImportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 2.98 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferImportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 7.71 EUR;"
                                        + storageFullReeferImportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 7.86 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "ONE" + ";"
                                        + "Tarif/Unit/Day" + "; 8.02 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferImportInervalsTotal[0] != 0 ||
                                storageFullReeferImportInervalsTotal[1] != 0 ||
                                storageFullReeferImportInervalsTotal[2] != 0
                                || storageFullReeferImportInervalsTotal_2023[0] != 0 ||
                                storageFullReeferImportInervalsTotal_2023[1] != 0 ||
                                storageFullReeferImportInervalsTotal_2023[2] != 0
                                || storageFullReeferImportInervalsTotal_2024[0] != 0 ||
                                storageFullReeferImportInervalsTotal_2024[1] != 0 ||
                                storageFullReeferImportInervalsTotal_2024[2] != 0) {
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfOOGContainersTotal != 0) {
                        content += "Storage surcharge Non-Standard Container " + ";" + "ONE" + ";" + "Quote %100" + ";"
                                        + "" + ";" +
                                        storageOfOOGContainersQuantity + ";" +
                                        df.format(storageOfOOGContainersTotal) + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfTankContainersTotal != 0) {
                        content += "Storage tank Containers surcharge " + ";" + "ONE" + ";" + "Quote %100" + ";" + ""
                                        + ";"
                                        + storageOfTankContainersQuantity + ";" +
                                        df.format(storageOfTankContainersTotal) + "\n";
                        content += " ; ; ; ; ; \n";
                }

                double total = hazardousImportTotal
                                + hazardousImportTotal_2023
                                + hazardousImportTotal_2024
                                + hazardousExportTotal
                                + hazardousTranssipmentTotal
                                + reeferConnectionTransshipmentTotal
                                + reeferConnectionTransshipmentTotal_2024
                                + reeferConnectionImportTotal
                                + reeferConnectionExportTotal
                                + reeferConnectionImportTotal_2023
                                + reeferConnectionImportTotal_2024
                                + reeferConnectionExportTotal_2023
                                + reeferConnectionExportTotal_2024
                                + storageOfDamagedContainersTotal
                                + storageFullExportInervalsTotal[0]
                                + storageFullExportInervalsTotal[1]
                                + storageFullExportInervalsTotal[2]
                                + storageFullImportInervalsTotal[0]
                                + storageFullImportInervalsTotal[1]
                                + storageFullImportInervalsTotal[2]
                                + storageFullExportInervalsTotal_2023[0]
                                + storageFullExportInervalsTotal_2024[0]
                                + storageFullExportInervalsTotal_2023[1]
                                + storageFullExportInervalsTotal_2024[1]
                                + storageFullExportInervalsTotal_2023[2]
                                + storageFullExportInervalsTotal_2024[2]
                                + storageFullImportInervalsTotal_2023[0]
                                + storageFullImportInervalsTotal_2024[0]
                                + storageFullImportInervalsTotal_2023[1]
                                + storageFullImportInervalsTotal_2024[1]
                                + storageFullImportInervalsTotal_2023[2]
                                + storageFullImportInervalsTotal_2024[2]
                                + storageFullTransshipmentInervalsTotal[0]
                                + storageFullTransshipmentInervalsTotal[1]
                                + storageFullTransshipmentInervalsTotal[2]
                                + storageFullTransshipmentInervalsTotal[3]
                                + storageFullTransshipmentInervalsTotal[4]
                                + storageFullTransshipmentInervalsTotal[5]
                                + storageFullReeferTransshipmentInervalsTotal[0]
                                + storageFullReeferTransshipmentInervalsTotal[1]
                                + storageFullReeferTransshipmentInervalsTotal[2]
                                + storageFullReeferTransshipmentInervalsTotal[3]
                                + storageFullReeferTransshipmentInervalsTotal[4]
                                + storageFullReeferTransshipmentInervalsTotal[5]
                                + storageFullReeferExportInervalsTotal[0]
                                + storageFullReeferExportInervalsTotal[1]
                                + storageFullReeferExportInervalsTotal[2]
                                + storageFullReeferImportInervalsTotal[0]
                                + storageFullReeferImportInervalsTotal[1]
                                + storageFullReeferImportInervalsTotal[2]
                                + storageFullReeferExportInervalsTotal_2023[0]
                                + storageFullReeferExportInervalsTotal_2024[0]
                                + storageFullReeferExportInervalsTotal_2023[1]
                                + storageFullReeferExportInervalsTotal_2024[1]
                                + storageFullReeferExportInervalsTotal_2023[2]
                                + storageFullReeferExportInervalsTotal_2024[2]
                                + storageFullReeferImportInervalsTotal_2023[0]
                                + storageFullReeferImportInervalsTotal_2024[0]
                                + storageFullReeferImportInervalsTotal_2023[1]
                                + storageFullReeferImportInervalsTotal_2024[1]
                                + storageFullReeferImportInervalsTotal_2023[2]
                                + storageFullReeferImportInervalsTotal_2024[2]
                                + storageOfOOGContainersTotal
                                + storageOfOOGContainersTotal_2023
                                + storageOfOOGContainersTotal_2024
                                + storageOfTankContainersTotal
                                + storageOfTankContainersTotal_2023
                                + storageOfTankContainersTotal_2024;
                /*
                 * +emptyDepotContainersTotal
                 * +emptyExportContainersTotal
                 * +emptyImportContainersTotal
                 * +emptyTransshipmentContainersTotal[0]
                 * +emptyTransshipmentContainersTotal[1]
                 * +emptyTransshipmentContainersTotal[2]
                 * +emptyTransshipmentContainersTotal[3];
                 */
                content += "Total " + ";" + " " + ";" + " " + ";" + " " + ";" + ";" + df.format(total) + "\n";

                return content;
        }

        public static String StandardSumMonthlyStorageDetails(String shippingLine, String month, String year)
                        throws ParseException, IOException {
                fw = null;
                bw = null;
                String content = "";
                hazardousPrice = 0;
                hazardousPrice_2023 = 0;
                hazardousImportTotal = 0;
                hazardousImportTotal_2023 = 0;
                hazardousImportTotal_2024 = 0;
                hazardousImportQuantity = 0;
                hazardousImportQuantity_2023 = 0;
                hazardousImportQuantity_2024 = 0;

                hazardousExportTotal = 0;
                hazardousExportTotal_2023 = 0;
                hazardousExportTotal_2024 = 0;
                hazardousExportQuantity = 0;
                hazardousExportQuantity_2023 = 0;
                hazardousExportQuantity_2024 = 0;

                hazardousTranssipmentTotal = 0;
                hazardousTranssipmentTotal_2023 = 0;
                hazardousTranssipmentTotal_2024 = 0;
                hazardousTransshipmentQuantity = 0;
                hazardousTransshipmentQuantity_2023 = 0;
                hazardousTransshipmentQuantity_2024 = 0;

                reeferConnectionTransshipmentPrice = 0;
                reeferConnectionTransshipmentPrice_2023 = 0;
                reeferConnectionTransshipmentPrice_2024 = 0;
                reeferConnectionTransshipmentTotal = 0;
                reeferConnectionTransshipmentTotal_2023 = 0;
                reeferConnectionTransshipmentTotal_2024 = 0;
                reeferConnectionTransshipmentQuantity = 0;
                reeferConnectionTransshipmentQuantity_2023 = 0;
                reeferConnectionTransshipmentQuantity_2024 = 0;

                reeferConnectionImportExportPrice = 0;
                reeferConnectionImportExportPrice_2023 = 0;
                reeferConnectionImportExportPrice_2024 = 0;
                reeferConnectionImportTotal = 0;
                reeferConnectionImportTotal_2023 = 0;
                reeferConnectionImportTotal_2024 = 0;
                reeferConnectionImportQuantity = 0;
                reeferConnectionImportQuantity_2023 = 0;
                reeferConnectionImportQuantity_2024 = 0;

                reeferConnectionExportTotal = 0;
                reeferConnectionExportTotal_2023 = 0;
                reeferConnectionExportTotal_2024 = 0;
                reeferConnectionExportQuantity = 0;
                reeferConnectionExportQuantity_2023 = 0;
                reeferConnectionExportQuantity_2024 = 0;

                storageOfDamagedContainersTotal = 0;
                storageOfDamagedContainersTotal_2023 = 0;
                storageOfDamagedContainersTotal_2024 = 0;
                storageOfDamagedContainersQuantity = 0;
                storageOfDamagedContainersQuantity_2023 = 0;
                storageOfDamagedContainersQuantity_2024 = 0;

                storageFullExportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullExportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullExportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullExportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullImportInervalsTotal = new double[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2023 = new double[] { 0, 0, 0 };
                storageFullImportInervalsTotal_2024 = new double[] { 0, 0, 0 };
                storageFullImportInervalsQuantities = new int[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2023 = new int[] { 0, 0, 0 };
                storageFullImportInervalsQuantities_2024 = new int[] { 0, 0, 0 };

                storageFullTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                storageFullReeferTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                storageFullReeferTransshipmentInervalsQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                storageFullReeferExportInervalsTotal = new double[] { 0, 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2023 = new double[] { 0, 0, 0, 0 };
                storageFullReeferExportInervalsTotal_2024 = new double[] { 0, 0, 0, 0 };
                storageFullReeferExportInervalsQuantities = new int[] { 0, 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2023 = new int[] { 0, 0, 0, 0 };
                storageFullReeferExportInervalsQuantities_2024 = new int[] { 0, 0, 0, 0 };

                storageFullReeferImportInervalsTotal = new double[] { 0, 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2023 = new double[] { 0, 0, 0, 0 };
                storageFullReeferImportInervalsTotal_2024 = new double[] { 0, 0, 0, 0 };
                storageFullReeferImportInervalsQuantities = new int[] { 0, 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2023 = new int[] { 0, 0, 0, 0 };
                storageFullReeferImportInervalsQuantities_2024 = new int[] { 0, 0, 0, 0 };

                storageOfOOGContainersTotal = 0;
                storageOfOOGContainersTotal_2023 = 0;
                storageOfOOGContainersTotal_2024 = 0;
                storageOfOOGContainersQuantity = 0;
                storageOfOOGContainersQuantity_2023 = 0;
                storageOfOOGContainersQuantity_2024 = 0;

                storageOfTankContainersTotal = 0;
                storageOfTankContainersTotal_2023 = 0;
                storageOfTankContainersTotal_2024 = 0;
                storageOfTankContainersQuantity = 0;
                storageOfTankContainersQuantity_2023 = 0;
                storageOfTankContainersQuantity_2024 = 0;

                emptyDepotContainersTotal = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersTotal_2023 = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersTotal_2024 = new double[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities = new int[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities_2023 = new int[] { 0, 0, 0, 0 };
                emptyDepotContainersQuantities_2024 = new int[] { 0, 0, 0, 0 };

                emptyExportContainersTotal = new double[] { 0, 0, 0, 0 };
                emptyExportContainersTotal_2023 = new double[] { 0, 0, 0, 0 };
                emptyExportContainersTotal_2024 = new double[] { 0, 0, 0, 0 };
                emptyExportContainersQuantities = new int[] { 0, 0, 0, 0 };
                emptyExportContainersQuantities_2023 = new int[] { 0, 0, 0, 0 };
                emptyExportContainersQuantities_2024 = new int[] { 0, 0, 0, 0 };

                emptyImportContainersTotal = new double[] { 0, 0, 0, 0 };
                emptyImportContainersTotal_2023 = new double[] { 0, 0, 0, 0 };
                emptyImportContainersTotal_2024 = new double[] { 0, 0, 0, 0 };
                emptyImportContainersQuantities = new int[] { 0, 0, 0, 0 };
                emptyImportContainersQuantities_2023 = new int[] { 0, 0, 0, 0 };
                emptyImportContainersQuantities_2024 = new int[] { 0, 0, 0, 0 };

                emptyTransshipmentContainersTotal = new double[] { 0, 0, 0, 0, 0, 0 };
                emptyTransshipmentContainersTotal_2023 = new double[] { 0, 0, 0, 0, 0, 0 };
                emptyTransshipmentContainersTotal_2024 = new double[] { 0, 0, 0, 0, 0, 0 };
                emptyTransshipmentContainersQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
                emptyTransshipmentContainersQuantities_2023 = new int[] { 0, 0, 0, 0, 0, 0 };
                emptyTransshipmentContainersQuantities_2024 = new int[] { 0, 0, 0, 0, 0, 0 };

                List<Interval> fullExportIntervals = new ArrayList<>();
                List<Interval> fullImportIntervals = new ArrayList<>();
                List<Interval> fullTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferTransshipmentIntervals = new ArrayList<>();
                List<Interval> fullReeferDirectIntervals = new ArrayList<>();
                List<Interval> emptyExportIntervals = new ArrayList<>();
                List<Interval> emptyImportIntervals = new ArrayList<>();
                List<Interval> emptyDepotIntervals = new ArrayList<>();

                for (ContainerStorageDetailsDTO shippingLinesInvoicing : shippingLinesInvoicingList) {
                        Container container = convertToContainer(shippingLinesInvoicing);
                        int theYear = container.getIncDate().getYear() - 100;
                        reeferConnectionImportExportPrice = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122) ? 35.53 : 36.24)
                                        : 36.96;

                        // fullTransshipmentIntervals
                        fullTransshipmentIntervals = (container.getIncDate().getYear() == 121)
                                        ? StandardContainerIntervals.transshipmentIntervals2021()
                                        : StandardContainerIntervals.transshipmentIntervals();

                        // fullReeferTransshipmentIntervals
                        fullReeferTransshipmentIntervals = (container.getIncDate().getYear() == 121)
                                        ? StandardContainerIntervals.transshipmentIntervals2021()
                                        : StandardContainerIntervals.transshipmentIntervals();

                        // fullImportIntervals
                        fullImportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? StandardContainerIntervals.fullImportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? StandardContainerIntervals
                                                                                        .fullImportIntervals2023()
                                                                        : StandardContainerIntervals
                                                                                        .fullImportIntervals2021())
                                        : StandardContainerIntervals.fullImportIntervals2024();

                        // fullExportIntervals
                        fullExportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? StandardContainerIntervals.fullExportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? StandardContainerIntervals
                                                                                        .fullExportIntervals2023()
                                                                        : StandardContainerIntervals
                                                                                        .fullExportIntervals2021())
                                        : StandardContainerIntervals.fullExportIntervals2024();

                        // fullReeferDirectIntervals
                        fullReeferDirectIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? StandardContainerIntervals.fullReeferDirectIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? StandardContainerIntervals
                                                                                        .fullReeferDirectIntervals2023()
                                                                        : StandardContainerIntervals
                                                                                        .fullReeferDirectIntervals2021())
                                        : StandardContainerIntervals.fullReeferDirectIntervals2024();

                        // emptyDepotIntervals
                        emptyDepotIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 121)
                                                        ? StandardContainerIntervals.emptyDepotIntervals2021()
                                                        : (container.getIncDate().getYear() == 122)
                                                                        ? StandardContainerIntervals
                                                                                        .emptyDepotIntervals()
                                                                        : StandardContainerIntervals
                                                                                        .emptyDepotIntervals2023())
                                        : StandardContainerIntervals.emptyDepotIntervals2024();

                        // emptyExportIntervals
                        emptyExportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? StandardContainerIntervals.emptyExportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? StandardContainerIntervals
                                                                                        .emptyExportIntervals2023()
                                                                        : StandardContainerIntervals
                                                                                        .emptyExportIntervals2021())
                                        : StandardContainerIntervals.emptyExportIntervals2024();

                        // emptyImportIntervals
                        emptyImportIntervals = (container.getIncDate().getYear() <= 123)
                                        ? ((container.getIncDate().getYear() == 122)
                                                        ? StandardContainerIntervals.emptyImportIntervals()
                                                        : (container.getIncDate().getYear() == 123)
                                                                        ? StandardContainerIntervals
                                                                                        .emptyImportIntervals2023()
                                                                        : StandardContainerIntervals
                                                                                        .emptyImportIntervals2021())
                                        : StandardContainerIntervals.emptyImportIntervals2024();

                        // Hazardous import Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                hazardousImportTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getStandardContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousImportQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // Hazardous Export Surcharge
                        if (shippingLinesInvoicing.imdg && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                hazardousExportTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getStandardContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousExportQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // Hazardous Transshipment Surcharge
                        if (shippingLinesInvoicing.imdg
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                hazardousTranssipmentTotal += shippingLinesInvoicing.imdgSurcharge;
                                Date firstDate = container.getIncDate();
                                Date secondDate = convertToDate(convertToLocalDate(firstDate).plusDays(1));
                                int freeDays = 0;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 2;
                                if (dateIsIncludedInMonth(firstDate, month, year)
                                                && !dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                if (!dateIsIncludedInMonth(firstDate, month, year)
                                                && dateIsIncludedInMonth(secondDate, month, year))
                                        freeDays = 1;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getStandardContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                while (numOfDays < freeDays) {
                                        freeDays--;
                                }
                                numOfDays -= freeDays;
                                hazardousTransshipmentQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // reeferConnectionTransshipment & Direct Total surchagre
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {

                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                fullReeferTransshipmentIntervals, month,
                                                year).stream().mapToInt(Integer::intValue).sum();
                                if (container.isReef() && container.getInvoiceCategory().equals("Transshipment")) {

                                        if (theYear == 24) {
                                                reeferConnectionTransshipmentTotal += numOfDays * 55;
                                                reeferConnectionTransshipmentQuantity += numOfDays;
                                        } else {
                                                reeferConnectionTransshipmentTotal += numOfDays * 45;
                                                reeferConnectionTransshipmentQuantity += numOfDays;
                                        }

                                }
                                if (container.isReef() && container.getInvoiceCategory().equals("Import")) {
                                        if (theYear == 24) {
                                                reeferConnectionImportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionImportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionImportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionImportQuantity += numOfDays;
                                        }

                                }
                                if (container.isReef() && container.getInvoiceCategory().equals("Export")) {
                                        if (theYear == 24) {
                                                reeferConnectionExportTotal_2024 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2024 += numOfDays;
                                        } else if (theYear == 23) {
                                                reeferConnectionExportTotal_2023 += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity_2023 += numOfDays;
                                        } else {
                                                reeferConnectionExportTotal += numOfDays
                                                                * reeferConnectionImportExportPrice;
                                                reeferConnectionExportQuantity += numOfDays;
                                        }

                                }
                        }
                        // storageOfDamagedContainers surcharge
                        if (shippingLinesInvoicing.dmg) {
                                storageOfDamagedContainersTotal += shippingLinesInvoicing.dmgSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getStandardContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfDamagedContainersQuantity += getContainerTeus(container) * numOfDays;
                        }
                        // storageOfOOGContainers surchagre
                        if (shippingLinesInvoicing.oog) {
                                storageOfOOGContainersTotal += shippingLinesInvoicing.oogSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getStandardContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfOOGContainersQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // storageOfTankContainers surchagre
                        if (shippingLinesInvoicing.type.equals("TK") || shippingLinesInvoicing.type.equals("TO")) {
                                storageOfTankContainersTotal += shippingLinesInvoicing.tankSurcharge;
                                int numOfDays = numberOfDaysForEachIntervalInMonth(container,
                                                getStandardContainerIntervals(container),
                                                month, year).stream().mapToInt(Integer::intValue).sum();
                                storageOfTankContainersQuantity += getContainerTeus(container) * numOfDays;
                        }

                        // storageFullExportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2024[0] += price;
                                        storageFullExportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2024[1] += price;
                                        storageFullExportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2024[2] += price;
                                        storageFullExportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal_2023[0] += price;
                                        storageFullExportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal_2023[1] += price;
                                        storageFullExportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal_2023[2] += price;
                                        storageFullExportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullExportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);
                                        double price = 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullExportIntervals.get(0).getPrice();
                                        storageFullExportInervalsTotal[0] += price;
                                        storageFullExportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullExportIntervals.get(1).getPrice();
                                        storageFullExportInervalsTotal[1] += price;
                                        storageFullExportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullExportIntervals.get(2).getPrice();
                                        storageFullExportInervalsTotal[2] += price;
                                        storageFullExportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }

                        }

                        // storageFullImportInervalsTotal
                        if (!shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2024[0] += price;
                                        storageFullImportInervalsQuantities_2024[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2024[1] += price;
                                        storageFullImportInervalsQuantities_2024[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2024[2] += price;
                                        storageFullImportInervalsQuantities_2024[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal_2023[0] += price;
                                        storageFullImportInervalsQuantities_2023[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal_2023[1] += price;
                                        storageFullImportInervalsQuantities_2023[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal_2023[2] += price;
                                        storageFullImportInervalsQuantities_2023[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullImportIntervals, month, year);
                                        int numberOfTeus = getContainerTeus(container);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                        * fullImportIntervals.get(0).getPrice();
                                        storageFullImportInervalsTotal[0] += price;
                                        storageFullImportInervalsQuantities[0] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                        * fullImportIntervals.get(1).getPrice();
                                        storageFullImportInervalsTotal[1] += price;
                                        storageFullImportInervalsQuantities[1] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                        : 0;

                                        price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                        * fullImportIntervals.get(2).getPrice();
                                        storageFullImportInervalsTotal[2] += price;
                                        storageFullImportInervalsQuantities[2] += (price != 0)
                                                        ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                        : 0;
                                }

                        }

                        // storageFullTransshipmentInervalsTotal
                        if (!shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")
                                        && shippingLinesInvoicing.fullOrEmpty.equals("Full")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                * fullTransshipmentIntervals.get(0).getPrice();
                                storageFullTransshipmentInervalsTotal[0] += price;
                                storageFullTransshipmentInervalsQuantities[0] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                * fullTransshipmentIntervals.get(1).getPrice();
                                storageFullTransshipmentInervalsTotal[1] += price;
                                storageFullTransshipmentInervalsQuantities[1] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                * fullTransshipmentIntervals.get(2).getPrice();
                                storageFullTransshipmentInervalsTotal[2] += price;
                                storageFullTransshipmentInervalsQuantities[2] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                                                * fullTransshipmentIntervals.get(3).getPrice();
                                storageFullTransshipmentInervalsTotal[3] += price;
                                storageFullTransshipmentInervalsQuantities[3] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(3)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(4) * numberOfTeus
                                                * fullTransshipmentIntervals.get(4).getPrice();
                                storageFullTransshipmentInervalsTotal[4] += price;
                                storageFullTransshipmentInervalsQuantities[4] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(4)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(5) * numberOfTeus
                                                * fullTransshipmentIntervals.get(5).getPrice();
                                storageFullTransshipmentInervalsTotal[5] += price;
                                storageFullTransshipmentInervalsQuantities[5] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(5)
                                                : 0;
                        }

                        // storageFullReeferTransshipmentInervals
                        if (shippingLinesInvoicing.reef
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullReeferTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                * fullReeferTransshipmentIntervals.get(0).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[0] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[0] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(0);

                                price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                * fullReeferTransshipmentIntervals.get(1).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[1] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[1] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(1);

                                price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                * fullReeferTransshipmentIntervals.get(2).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[2] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[2] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(2);

                                price = numberOfDaysForEachIntervalInMonthList.get(3)
                                                * fullReeferTransshipmentIntervals.get(3).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[3] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[3] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(3);

                                price = numberOfDaysForEachIntervalInMonthList.get(4)
                                                * fullReeferTransshipmentIntervals.get(4).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[4] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[4] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(4);

                                price = numberOfDaysForEachIntervalInMonthList.get(5)
                                                * fullReeferTransshipmentIntervals.get(5).getPrice() * numberOfTeus;
                                storageFullReeferTransshipmentInervalsTotal[5] += price;
                                if (price != 0)
                                        storageFullReeferTransshipmentInervalsQuantities[5] += numberOfTeus
                                                        * numberOfDaysForEachIntervalInMonthList.get(5);

                        }

                        // storageFullReeferExportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Export")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferExportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferExportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferExportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferExportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);

                                }

                        }

                        // storageFullReeferImportInervals
                        if (shippingLinesInvoicing.reef && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                if (theYear == 24) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2024[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2024[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else if (theYear == 23) {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal_2023[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities_2023[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                } else {
                                        List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                        container,
                                                        fullReeferDirectIntervals, month, year);

                                        double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                        * fullReeferDirectIntervals.get(0).getPrice();
                                        storageFullReeferImportInervalsTotal[0] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[0] += numberOfDaysForEachIntervalInMonthList
                                                                .get(0);

                                        price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                        * fullReeferDirectIntervals.get(1).getPrice();
                                        storageFullReeferImportInervalsTotal[1] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[1] += numberOfDaysForEachIntervalInMonthList
                                                                .get(1);

                                        price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                        * fullReeferDirectIntervals.get(2).getPrice();
                                        storageFullReeferImportInervalsTotal[2] += price;
                                        if (price != 0)
                                                storageFullReeferImportInervalsQuantities[2] += numberOfDaysForEachIntervalInMonthList
                                                                .get(2);
                                }

                        }

                        // emptyDepotContainersTotal
                        if (shippingLinesInvoicing.fullOrEmpty.equals("Empty")
                                        && shippingLinesInvoicing.invoiceCategory.equals("Depot")) {

                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                emptyDepotIntervals, month, year);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                * emptyDepotIntervals.get(0).getPrice();
                                emptyDepotContainersTotal[0] += price;
                                emptyDepotContainersQuantities[0] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(0)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                * emptyDepotIntervals.get(1).getPrice();
                                emptyDepotContainersTotal[1] += price;
                                emptyDepotContainersQuantities[1] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(1)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                * emptyDepotIntervals.get(2).getPrice();
                                emptyDepotContainersTotal[2] += price;
                                emptyDepotContainersQuantities[2] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(2)
                                                : 0;

                        }

                        // emptyExportContainersTotal
                        if (shippingLinesInvoicing.fullOrEmpty.equals("Empty")
                                        && shippingLinesInvoicing.invoiceCategory.equals("Export")) {

                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                emptyExportIntervals, month, year);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                * emptyExportIntervals.get(0).getPrice();
                                emptyExportContainersTotal[0] += price;
                                emptyExportContainersQuantities[0] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(0)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                * emptyExportIntervals.get(1).getPrice();
                                emptyExportContainersTotal[1] += price;
                                emptyExportContainersQuantities[1] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(1)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                * emptyExportIntervals.get(2).getPrice();
                                emptyExportContainersTotal[2] += price;
                                emptyExportContainersQuantities[2] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(2)
                                                : 0;

                        }

                        // emptyImportContainersTotal
                        if (shippingLinesInvoicing.fullOrEmpty.equals("Empty")
                                        && shippingLinesInvoicing.invoiceCategory.equals("Import")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                emptyImportIntervals, month, year);
                                if (container.getContainerNumber().equals("HMMU6043057")) {
                                        System.err.println("daakhaaal");
                                }

                                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                                                * emptyImportIntervals.get(0).getPrice();
                                emptyImportContainersTotal[0] += price;
                                emptyImportContainersQuantities[0] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(0)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(1)
                                                * emptyImportIntervals.get(1).getPrice();
                                emptyImportContainersTotal[1] += price;
                                emptyImportContainersQuantities[1] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(1)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(2)
                                                * emptyImportIntervals.get(2).getPrice();
                                emptyImportContainersTotal[2] += price;
                                emptyImportContainersQuantities[2] += (price != 0)
                                                ? numberOfDaysForEachIntervalInMonthList.get(2)
                                                : 0;

                        }

                        // storageEmptyTransshipmentInervalsTotal
                        if (shippingLinesInvoicing.fullOrEmpty.equals("Empty")
                                        && shippingLinesInvoicing.invoiceCategory.equals("Transshipment")) {
                                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(
                                                container,
                                                fullTransshipmentIntervals, month, year);
                                int numberOfTeus = getContainerTeus(container);

                                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                                                * fullTransshipmentIntervals.get(0).getPrice();
                                emptyTransshipmentContainersTotal[0] += price;
                                emptyTransshipmentContainersQuantities[0] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(0)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                                                * fullTransshipmentIntervals.get(1).getPrice();
                                emptyTransshipmentContainersTotal[1] += price;
                                emptyTransshipmentContainersQuantities[1] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(1)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                                                * fullTransshipmentIntervals.get(2).getPrice();
                                emptyTransshipmentContainersTotal[2] += price;
                                emptyTransshipmentContainersQuantities[2] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(2)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                                                * fullTransshipmentIntervals.get(3).getPrice();
                                emptyTransshipmentContainersTotal[3] += price;
                                emptyTransshipmentContainersQuantities[3] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(3)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(4) * numberOfTeus
                                                * fullTransshipmentIntervals.get(4).getPrice();
                                emptyTransshipmentContainersTotal[4] += price;
                                emptyTransshipmentContainersQuantities[4] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(4)
                                                : 0;

                                price = numberOfDaysForEachIntervalInMonthList.get(5) * numberOfTeus
                                                * fullTransshipmentIntervals.get(5).getPrice();
                                emptyTransshipmentContainersTotal[5] += price;
                                emptyTransshipmentContainersQuantities[5] += (price != 0)
                                                ? numberOfTeus * numberOfDaysForEachIntervalInMonthList.get(5)
                                                : 0;
                        }

                }
                content += "Description;Customer;Unit Measure;Price per Unit;Quantity;Tot Amount\n";
                if (hazardousImportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" + "23.61 EUR" + ";" +
                                        hazardousImportQuantity + ";" + df.format((hazardousImportTotal)) + "\n";

                if (hazardousExportTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" + "23.61 EUR" + ";" +
                                        hazardousExportQuantity + ";" + df.format(hazardousExportTotal) + "\n";

                if (hazardousTranssipmentTotal != 0)
                        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + ";" + "23.61 EUR" + ";" +
                                        hazardousTransshipmentQuantity + ";" + df.format(hazardousTranssipmentTotal)
                                        + "\n";

                if (hazardousImportTotal != 0 || hazardousExportTotal != 0 || hazardousTranssipmentTotal != 0)
                        content += " ; ; ; ; ; \n";

                if (reeferConnectionTransshipmentTotal != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + ";" + "45 EUR (2023)" + ";" +
                                        reeferConnectionTransshipmentQuantity + ";"
                                        + df.format(reeferConnectionTransshipmentTotal) + "\n";
                if (reeferConnectionTransshipmentTotal_2024 != 0)
                        content += "Reefer connection Transhipment (storage not included) " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + ";" + "45 EUR (2024)" + ";" +
                                        reeferConnectionTransshipmentQuantity_2024 + ";"
                                        + df.format(reeferConnectionTransshipmentTotal_2024) + "\n";

                if (reeferConnectionImportTotal != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "35.53 EUR" + ";" +
                                        reeferConnectionImportQuantity + ";" + df.format(reeferConnectionImportTotal)
                                        + "\n";
                if (reeferConnectionImportTotal_2023 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionImportQuantity_2023 + ";"
                                        + df.format(reeferConnectionImportTotal)
                                        + "\n";
                if (reeferConnectionImportTotal_2024 != 0)
                        content += "Reefer connection Import (storage not included) " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "36.96 EUR (2024)" + ";" +
                                        reeferConnectionImportQuantity_2024 + ";"
                                        + df.format(reeferConnectionImportTotal)
                                        + "\n";

                if (reeferConnectionExportTotal != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "35.53 EUR" + ";" +
                                        reeferConnectionExportQuantity + ";" + df.format(reeferConnectionExportTotal)
                                        + "\n";
                if (reeferConnectionExportTotal_2023 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "36.24 EUR (2023)" + ";" +
                                        reeferConnectionExportQuantity_2023 + ";"
                                        + df.format(reeferConnectionExportTotal_2023)
                                        + "\n";
                if (reeferConnectionExportTotal_2024 != 0)
                        content += "Reefer connection Export (storage not included) " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + ";" + "36.96 EUR (2024)" + ";" +
                                        reeferConnectionExportQuantity_2024 + ";"
                                        + df.format(reeferConnectionExportTotal_2024)
                                        + "\n";

                if (reeferConnectionTransshipmentTotal != 0 || reeferConnectionTransshipmentTotal_2024 != 0
                                || reeferConnectionImportTotal != 0
                                || reeferConnectionImportTotal_2023 != 0
                                || reeferConnectionImportTotal_2024 != 0
                                || reeferConnectionExportTotal != 0 || reeferConnectionExportTotal_2023 != 0
                                || reeferConnectionExportTotal_2024 != 0)
                        content += " ; ; ; ; ; \n";

                if (storageOfDamagedContainersTotal != 0) {
                        content += "Storage damaged Containers surcharge  " + ";" + shippingLine + ";" + "Quote %200"
                                        + ";" + ""
                                        + ";" +
                                        storageOfDamagedContainersQuantity + ";"
                                        + df.format(storageOfDamagedContainersTotal) + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageFullExportInervalsTotal[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR;"
                                        + storageFullExportInervalsQuantities[0] + ";" +
                                        df.format(storageFullExportInervalsTotal[0]) + "\n";
                if (storageFullExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[0]) + "\n";
                if (storageFullExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Export First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[0]) + "\n";

                if (storageFullExportInervalsTotal[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 1.43 EUR;"
                                        + storageFullExportInervalsQuantities[1] + ";" +
                                        df.format(storageFullExportInervalsTotal[1]) + "\n";
                if (storageFullExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 1.46 EUR (2023);"
                                        + storageFullExportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[1]) + "\n";
                if (storageFullExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Export Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 1.49 EUR (2024);"
                                        + storageFullExportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[1]) + "\n";

                if (storageFullExportInervalsTotal[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 3.86 EUR;"
                                        + storageFullExportInervalsQuantities[2] + ";" +
                                        df.format(storageFullExportInervalsTotal[2]) + "\n";
                if (storageFullExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 3.94 EUR;"
                                        + storageFullExportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2023[2]) + "\n";
                if (storageFullExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Export Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 4.02 EUR;"
                                        + storageFullExportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullExportInervalsTotal_2024[2]) + "\n";

                if (storageFullExportInervalsTotal[0] != 0 || storageFullExportInervalsTotal[1] != 0
                                || storageFullExportInervalsTotal[2] != 0 || storageFullExportInervalsTotal_2023[0] != 0
                                || storageFullExportInervalsTotal_2023[1] != 0
                                || storageFullExportInervalsTotal_2023[2] != 0
                                || storageFullExportInervalsTotal_2024[0] != 0
                                || storageFullExportInervalsTotal_2024[1] != 0
                                || storageFullExportInervalsTotal_2024[2] != 0)
                        content += " ; ; ; ; ; \n";

                if (storageFullImportInervalsTotal[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR;"
                                        + storageFullImportInervalsQuantities[0] + ";" +
                                        df.format(storageFullImportInervalsTotal[0]) + "\n";
                if (storageFullImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[0]) + "\n";
                if (storageFullImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Import First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 0 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2024[0] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[0]) + "\n";

                if (storageFullImportInervalsTotal[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 1.43 EUR;"
                                        + storageFullImportInervalsQuantities[1] + ";" +
                                        df.format(storageFullImportInervalsTotal[1]) + "\n";
                if (storageFullImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 1.46 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[1]) + "\n";
                if (storageFullImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Import Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 1.49 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[1] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[1]) + "\n";

                if (storageFullImportInervalsTotal[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 3.86 EUR;"
                                        + storageFullImportInervalsQuantities[2] + ";" +
                                        df.format(storageFullImportInervalsTotal[2]) + "\n";
                if (storageFullImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 3.94 EUR (2023);"
                                        + storageFullImportInervalsQuantities_2023[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2023[2]) + "\n";
                if (storageFullImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Import Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + "; 4.02 EUR (2024);"
                                        + storageFullImportInervalsQuantities_2024[2] + ";" +
                                        df.format(storageFullImportInervalsTotal_2024[2]) + "\n";

                if (storageFullImportInervalsTotal[0] != 0 || storageFullImportInervalsTotal[1] != 0
                                || storageFullImportInervalsTotal[2] != 0 || storageFullImportInervalsTotal_2023[0] != 0
                                || storageFullImportInervalsTotal_2023[1] != 0
                                || storageFullImportInervalsTotal_2023[2] != 0
                                || storageFullImportInervalsTotal_2024[0] != 0
                                || storageFullImportInervalsTotal_2024[1] != 0
                                || storageFullImportInervalsTotal_2024[2] != 0)
                        content += " ; ; ; ; ; \n";

                if (storageFullTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Transshipment First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[0]) + "\n";

                if (storageFullTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Transshipment Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[1]) + "\n";

                if (storageFullTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Transshipment Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[2]) + "\n";

                if (storageFullTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[3]) + "\n";

                if (storageFullTransshipmentInervalsTotal[4] != 0)
                        content += "Storage Full Transshipment Fifth Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[4] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[4]) + "\n";

                if (storageFullTransshipmentInervalsTotal[5] != 0)
                        content += "Storage Full Transshipment Sixth Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day"
                                        + ";" +
                                        fullTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + storageFullTransshipmentInervalsQuantities[5] + ";" +
                                        df.format(storageFullTransshipmentInervalsTotal[5]) + "\n";

                if (storageFullTransshipmentInervalsTotal[0] != 0 ||
                                storageFullTransshipmentInervalsTotal[1] != 0 ||
                                storageFullTransshipmentInervalsTotal[2] != 0 ||
                                storageFullTransshipmentInervalsTotal[3] != 0 ||
                                storageFullTransshipmentInervalsTotal[4] != 0 ||
                                storageFullTransshipmentInervalsTotal[5] != 0)
                        content += " ; ; ; ; ; \n";

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullReeferTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[0] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[0]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullReeferTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[1] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[1]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullReeferTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[2] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[2]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[3] != 0)
                        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullReeferTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[3] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[3]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[4] != 0)
                        content += "Storage Full Reefer Transshipment Fifth Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullReeferTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[4] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[4]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[5] != 0)
                        content += "Storage Full Reefer Transshipment Sixth Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullReeferTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + storageFullReeferTransshipmentInervalsQuantities[5] + ";" +
                                        df.format(storageFullReeferTransshipmentInervalsTotal[5]) + "\n";

                if (storageFullReeferTransshipmentInervalsTotal[0] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[1] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[2] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[3] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[4] != 0 ||
                                storageFullReeferTransshipmentInervalsTotal[5] != 0)
                        content += " ; ; ; ; ; \n";

                if (storageFullReeferExportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR;"
                                        + storageFullReeferExportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Export First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferExportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 2.86 EUR;"
                                        + storageFullReeferExportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 2.92 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Export Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 2.98 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferExportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 7.71 EUR;"
                                        + storageFullReeferExportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 7.86 EUR (2023);"
                                        + storageFullReeferExportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferExportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Export Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 8.02 EUR (2024);"
                                        + storageFullReeferExportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferExportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferExportInervalsTotal[0] != 0 ||
                                storageFullReeferExportInervalsTotal[1] != 0 ||
                                storageFullReeferExportInervalsTotal[2] != 0
                                || storageFullReeferExportInervalsTotal_2023[0] != 0 ||
                                storageFullReeferExportInervalsTotal_2023[1] != 0 ||
                                storageFullReeferExportInervalsTotal_2023[2] != 0
                                || storageFullReeferExportInervalsTotal_2024[0] != 0 ||
                                storageFullReeferExportInervalsTotal_2024[1] != 0 ||
                                storageFullReeferExportInervalsTotal_2024[2] != 0)
                        content += " ; ; ; ; ; \n";

                if (storageFullReeferImportInervalsTotal[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR;"
                                        + storageFullReeferImportInervalsQuantities[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR;"
                                        + storageFullReeferImportInervalsQuantities_2023[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[0]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[0] != 0)
                        content += "Storage Full Reefer Import First Period Revenue  " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 0 EUR;"
                                        + storageFullReeferImportInervalsQuantities_2024[0]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[0]) + "\n";

                if (storageFullReeferImportInervalsTotal[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 2.86 EUR;"
                                        + storageFullReeferImportInervalsQuantities[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 2.92 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[1]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[1] != 0)
                        content += "Storage Full Reefer Import Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 2.96 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[1]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[1]) + "\n";

                if (storageFullReeferImportInervalsTotal[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 7.71 EUR;"
                                        + storageFullReeferImportInervalsQuantities[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2023[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 7.86 EUR (2023);"
                                        + storageFullReeferImportInervalsQuantities_2023[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2023[2]) + "\n";
                if (storageFullReeferImportInervalsTotal_2024[2] != 0)
                        content += "Storage Full Reefer Import Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day"
                                        + "; 8.02 EUR (2024);"
                                        + storageFullReeferImportInervalsQuantities_2024[2]
                                        + ";" +
                                        df.format(storageFullReeferImportInervalsTotal_2024[2]) + "\n";

                if (storageFullReeferImportInervalsTotal[0] != 0 ||
                                storageFullReeferImportInervalsTotal[1] != 0 ||
                                storageFullReeferImportInervalsTotal[2] != 0
                                || storageFullReeferImportInervalsTotal_2023[0] != 0 ||
                                storageFullReeferImportInervalsTotal_2023[1] != 0 ||
                                storageFullReeferImportInervalsTotal_2023[2] != 0
                                || storageFullReeferImportInervalsTotal_2024[0] != 0 ||
                                storageFullReeferImportInervalsTotal_2024[1] != 0 ||
                                storageFullReeferImportInervalsTotal_2024[2] != 0)
                        content += " ; ; ; ; ; \n";

                if (storageOfOOGContainersTotal != 0) {
                        content += "Storage surcharge Non-Standard Container " + ";" + shippingLine + ";" + "Quote %100"
                                        + ";" + ""
                                        + ";" +
                                        storageOfOOGContainersQuantity + ";" +
                                        df.format(storageOfOOGContainersTotal) + "\n";
                        content += " ; ; ; ; ; \n";
                }

                if (storageOfTankContainersTotal != 0) {
                        content += "Storage tank Containers surcharge " + ";" + shippingLine + ";" + "Quote %100" + ";"
                                        + "" + ";"
                                        + storageOfTankContainersQuantity + ";" +
                                        df.format(storageOfTankContainersTotal) + "\n";
                        content += "; ; ; ; ; \n";
                }

                if (emptyDepotContainersTotal[0] != 0) {
                        content += "Storage Empty Depot First Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + emptyDepotContainersQuantities[0] + ";" +
                                        df.format(emptyDepotContainersTotal[0]) + "\n";
                }
                if (emptyDepotContainersTotal_2023[0] != 0) {
                        content += "Storage Empty Depot First Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + emptyDepotContainersQuantities_2023[0] + ";" +
                                        df.format(emptyDepotContainersTotal_2023[0]) + "\n";
                }

                if (emptyDepotContainersTotal[1] != 0) {
                        content += "Storage Empty Depot Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 1.07 EUR;"
                                        + emptyDepotContainersQuantities[1] + ";" +
                                        df.format(emptyDepotContainersTotal[1]) + "\n";
                }
                if (emptyDepotContainersTotal_2023[1] != 0) {
                        content += "Storage Empty Depot Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 1.09 EUR;"
                                        + emptyDepotContainersQuantities_2023[1] + ";" +
                                        df.format(emptyDepotContainersTotal_2023[1]) + "\n";
                }

                if (emptyDepotContainersTotal[2] != 0) {
                        content += "Storage Empty Depot Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + emptyDepotContainersQuantities[2] + ";" +
                                        df.format(emptyDepotContainersTotal[2]) + "\n";
                }
                if (emptyDepotContainersTotal_2023[2] != 0) {
                        content += "Storage Empty Depot Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR;"
                                        + emptyDepotContainersQuantities_2023[2] + ";" +
                                        df.format(emptyDepotContainersTotal_2023[2]) + "\n";
                }

                if (emptyDepotContainersTotal[0] != 0 ||
                                emptyDepotContainersTotal[1] != 0 ||
                                emptyDepotContainersTotal[2] != 0 || emptyDepotContainersTotal_2023[0] != 0 ||
                                emptyDepotContainersTotal_2023[1] != 0 ||
                                emptyDepotContainersTotal_2023[2] != 0)
                        content += " ; ; ; ; ; \n";

                if (emptyExportContainersTotal[0] != 0) {
                        content += "Storage Empty Export First Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + emptyExportContainersQuantities[0] + ";" +
                                        df.format(emptyExportContainersTotal[0]) + "\n";
                }
                if (emptyExportContainersTotal_2023[0] != 0) {
                        content += "Storage Empty Export First Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + emptyExportContainersQuantities_2023[0] + ";" +
                                        df.format(emptyExportContainersTotal_2023[0]) + "\n";
                }

                if (emptyExportContainersTotal[1] != 0) {
                        content += "Storage Empty Export Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 1.07 EUR;"
                                        + emptyExportContainersQuantities[1] + ";" +
                                        df.format(emptyExportContainersTotal[1]) + "\n";
                }
                if (emptyExportContainersTotal_2023[1] != 0) {
                        content += "Storage Empty Export Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 1.09 EUR (2023);"
                                        + emptyExportContainersQuantities_2023[1] + ";" +
                                        df.format(emptyExportContainersTotal_2023[1]) + "\n";
                }

                if (emptyExportContainersTotal[2] != 0) {
                        content += "Storage Empty Export Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + emptyExportContainersQuantities[2] + ";" +
                                        df.format(emptyExportContainersTotal[2]) + "\n";
                }
                if (emptyExportContainersTotal_2023[2] != 0) {
                        content += "Storage Empty Export Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + emptyExportContainersQuantities_2023[2] + ";" +
                                        df.format(emptyExportContainersTotal_2023[2]) + "\n";
                }

                if (emptyExportContainersTotal[0] != 0 ||
                                emptyExportContainersTotal[1] != 0 ||
                                emptyExportContainersTotal[2] != 0 || emptyExportContainersTotal_2023[0] != 0 ||
                                emptyExportContainersTotal_2023[1] != 0 ||
                                emptyExportContainersTotal_2023[2] != 0)
                        content += " ; ; ; ; ; \n";

                if (emptyImportContainersTotal[0] != 0) {
                        content += "Storage Empty Import First Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR;"
                                        + emptyImportContainersQuantities[0] + ";" +
                                        df.format(emptyImportContainersTotal[0]) + "\n";
                }
                if (emptyImportContainersTotal_2023[0] != 0) {
                        content += "Storage Empty Import First Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 0 EUR (2023);"
                                        + emptyImportContainersQuantities_2023[0] + ";" +
                                        df.format(emptyImportContainersTotal_2023[0]) + "\n";
                }

                if (emptyImportContainersTotal[1] != 0) {
                        content += "Storage Empty Import Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 1.07 EUR;"
                                        + emptyImportContainersQuantities[1] + ";" +
                                        df.format(emptyImportContainersTotal[1]) + "\n";
                }
                if (emptyImportContainersTotal_2023[1] != 0) {
                        content += "Storage Empty Import Second Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 1.09 EUR (2023);"
                                        + emptyImportContainersQuantities_2023[1] + ";" +
                                        df.format(emptyImportContainersTotal_2023[1]) + "\n";
                }

                if (emptyImportContainersTotal[2] != 0) {
                        content += "Storage Empty Import Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 2.86 EUR;"
                                        + emptyImportContainersQuantities[2] + ";" +
                                        df.format(emptyImportContainersTotal[2]) + "\n";
                }
                if (emptyImportContainersTotal_2023[2] != 0) {
                        content += "Storage Empty Import Third Period Revenue " + ";" + shippingLine + ";"
                                        + "Tarif/Unit/Day" + "; 2.92 EUR (2023);"
                                        + emptyImportContainersQuantities_2023[2] + ";" +
                                        df.format(emptyImportContainersTotal_2023[2]) + "\n";
                }

                if (emptyImportContainersTotal[0] != 0 ||
                                emptyImportContainersTotal[1] != 0 ||
                                emptyImportContainersTotal[2] != 0 || emptyImportContainersTotal_2023[0] != 0 ||
                                emptyImportContainersTotal_2023[1] != 0 ||
                                emptyImportContainersTotal_2023[2] != 0)
                        content += " ; ; ; ; ; \n";

                if (emptyTransshipmentContainersTotal[0] != 0)
                        content += "Storage Empty Container Transshipment First Period Revenue  " + ";" + shippingLine
                                        + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(0).getPrice() + " EUR;"
                                        + emptyTransshipmentContainersQuantities[0]
                                        + ";" +
                                        df.format(emptyTransshipmentContainersTotal[0]) + "\n";

                if (emptyTransshipmentContainersTotal[1] != 0)
                        content += "Storage Empty Container Transshipment Second Period Revenue  " + ";" + shippingLine
                                        + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(1).getPrice() + " EUR;"
                                        + emptyTransshipmentContainersQuantities[1]
                                        + ";" +
                                        df.format(emptyTransshipmentContainersTotal[1]) + "\n";

                if (emptyTransshipmentContainersTotal[2] != 0)
                        content += "Storage Empty Container Transshipment Third Period Revenue  " + ";" + shippingLine
                                        + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(2).getPrice() + " EUR;"
                                        + emptyTransshipmentContainersQuantities[2]
                                        + ";" +
                                        df.format(emptyTransshipmentContainersTotal[2]) + "\n";

                if (emptyTransshipmentContainersTotal[3] != 0)
                        content += "Storage Empty Container Transshipment Fourth Period Revenue  " + ";" + shippingLine
                                        + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(3).getPrice() + " EUR;"
                                        + emptyTransshipmentContainersQuantities[3]
                                        + ";" +
                                        df.format(emptyTransshipmentContainersTotal[3]) + "\n";

                if (emptyTransshipmentContainersTotal[4] != 0)
                        content += "Storage Empty Container Transshipment Fifth Period Revenue  " + ";" + shippingLine
                                        + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(4).getPrice() + " EUR;"
                                        + emptyTransshipmentContainersQuantities[4]
                                        + ";" +
                                        df.format(emptyTransshipmentContainersTotal[4]) + "\n";

                if (emptyTransshipmentContainersTotal[5] != 0)
                        content += "Storage Empty Container Transshipment Sixth Period Revenue  " + ";" + shippingLine
                                        + ";"
                                        + "Tarif/Teu/Day" + ";" +
                                        fullTransshipmentIntervals.get(5).getPrice() + " EUR;"
                                        + emptyTransshipmentContainersQuantities[5]
                                        + ";" +
                                        df.format(emptyTransshipmentContainersTotal[5]) + "\n";

                if (emptyTransshipmentContainersTotal[0] != 0 ||
                                emptyTransshipmentContainersTotal[1] != 0 ||
                                emptyTransshipmentContainersTotal[2] != 0 ||
                                emptyTransshipmentContainersTotal[3] != 0 ||
                                emptyTransshipmentContainersTotal[4] != 0 ||
                                emptyTransshipmentContainersTotal[5] != 0)
                        content += " ; ; ; ; ; \n";

                double total = hazardousImportTotal
                                + hazardousImportTotal_2023
                                + hazardousImportTotal_2024
                                + hazardousExportTotal
                                + hazardousTranssipmentTotal
                                + reeferConnectionTransshipmentTotal
                                + reeferConnectionTransshipmentTotal_2024
                                + reeferConnectionImportTotal
                                + reeferConnectionExportTotal
                                + reeferConnectionImportTotal_2023
                                + reeferConnectionImportTotal_2024
                                + reeferConnectionExportTotal_2023
                                + reeferConnectionExportTotal_2024
                                + storageOfDamagedContainersTotal
                                + storageFullExportInervalsTotal[0]
                                + storageFullExportInervalsTotal[1]
                                + storageFullExportInervalsTotal[2]
                                + storageFullImportInervalsTotal[0]
                                + storageFullImportInervalsTotal[1]
                                + storageFullImportInervalsTotal[2]
                                + storageFullExportInervalsTotal_2023[0]
                                + storageFullExportInervalsTotal_2024[0]
                                + storageFullExportInervalsTotal_2023[1]
                                + storageFullExportInervalsTotal_2024[1]
                                + storageFullExportInervalsTotal_2023[2]
                                + storageFullExportInervalsTotal_2024[2]
                                + storageFullImportInervalsTotal_2023[0]
                                + storageFullImportInervalsTotal_2024[0]
                                + storageFullImportInervalsTotal_2023[1]
                                + storageFullImportInervalsTotal_2024[1]
                                + storageFullImportInervalsTotal_2023[2]
                                + storageFullImportInervalsTotal_2024[2]
                                + storageFullTransshipmentInervalsTotal[0]
                                + storageFullTransshipmentInervalsTotal[1]
                                + storageFullTransshipmentInervalsTotal[2]
                                + storageFullTransshipmentInervalsTotal[3]
                                + storageFullTransshipmentInervalsTotal[4]
                                + storageFullTransshipmentInervalsTotal[5]
                                + storageFullReeferTransshipmentInervalsTotal[0]
                                + storageFullReeferTransshipmentInervalsTotal[1]
                                + storageFullReeferTransshipmentInervalsTotal[2]
                                + storageFullReeferTransshipmentInervalsTotal[3]
                                + storageFullReeferTransshipmentInervalsTotal[4]
                                + storageFullReeferTransshipmentInervalsTotal[5]
                                + storageFullReeferExportInervalsTotal[0]
                                + storageFullReeferExportInervalsTotal[1]
                                + storageFullReeferExportInervalsTotal[2]
                                + storageFullReeferImportInervalsTotal[0]
                                + storageFullReeferImportInervalsTotal[1]
                                + storageFullReeferImportInervalsTotal[2]
                                + storageFullReeferExportInervalsTotal_2023[0]
                                + storageFullReeferExportInervalsTotal_2024[0]
                                + storageFullReeferExportInervalsTotal_2023[1]
                                + storageFullReeferExportInervalsTotal_2024[1]
                                + storageFullReeferExportInervalsTotal_2023[2]
                                + storageFullReeferExportInervalsTotal_2024[2]
                                + storageFullReeferImportInervalsTotal_2023[0]
                                + storageFullReeferImportInervalsTotal_2024[0]
                                + storageFullReeferImportInervalsTotal_2023[1]
                                + storageFullReeferImportInervalsTotal_2024[1]
                                + storageFullReeferImportInervalsTotal_2023[2]
                                + storageFullReeferImportInervalsTotal_2024[2]
                                + storageOfOOGContainersTotal
                                + storageOfOOGContainersTotal_2023
                                + storageOfOOGContainersTotal_2024
                                + storageOfTankContainersTotal
                                + storageOfTankContainersTotal_2023
                                + storageOfTankContainersTotal_2024
                                + emptyExportContainersTotal[0]
                                + emptyExportContainersTotal[1]
                                + emptyExportContainersTotal[2]
                                + emptyExportContainersTotal_2023[0]
                                + emptyExportContainersTotal_2024[0]
                                + emptyExportContainersTotal_2023[1]
                                + emptyExportContainersTotal_2024[1]
                                + emptyExportContainersTotal_2023[2]
                                + emptyExportContainersTotal_2024[2]
                                + emptyDepotContainersTotal[0]
                                + emptyDepotContainersTotal[1]
                                + emptyDepotContainersTotal[2]
                                + emptyDepotContainersTotal_2023[0]
                                + emptyDepotContainersTotal_2024[0]
                                + emptyDepotContainersTotal_2023[1]
                                + emptyDepotContainersTotal_2024[1]
                                + emptyDepotContainersTotal_2023[2]
                                + emptyDepotContainersTotal_2024[2]
                                + emptyImportContainersTotal[0]
                                + emptyImportContainersTotal[1]
                                + emptyImportContainersTotal[2]
                                + emptyImportContainersTotal_2023[0]
                                + emptyImportContainersTotal_2024[0]
                                + emptyImportContainersTotal_2023[1]
                                + emptyImportContainersTotal_2024[1]
                                + emptyImportContainersTotal_2023[2]
                                + emptyImportContainersTotal_2024[2]
                                + emptyTransshipmentContainersTotal[0]
                                + emptyTransshipmentContainersTotal[1]
                                + emptyTransshipmentContainersTotal[2]
                                + emptyTransshipmentContainersTotal[3]
                                + emptyTransshipmentContainersTotal[4]
                                + emptyTransshipmentContainersTotal[5];
                content += "Total " + ";" + " " + ";" + " " + ";" + " " + ";" + ";" + df.format(total) + "\n";

                return content;
        }

}