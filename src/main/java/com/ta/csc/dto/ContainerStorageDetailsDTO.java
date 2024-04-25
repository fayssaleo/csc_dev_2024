package com.ta.csc.dto;

import com.ta.csc.domain.Container;
import com.ta.csc.domain.Interval;
import org.decimal4j.util.DoubleRounder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.ta.csc.helper.Constants.getMinAndMaxDaysOfMonth;
import static com.ta.csc.helper.Fixtures.numberOfDaysForEachIntervalInMonth;

public class ContainerStorageDetailsDTO {

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

    public static double hazardousImportTotal = 0;
    public static int hazardousImportQuantity = 0;

    public static double hazardousExportTotal = 0;
    public static int hazardousExportQuantity = 0;

    public static double hazardousTranssipmentTotal = 0;
    public static int hazardousTransshipmentQuantity = 0;

    public static double reeferConnectionTransshipmentTotal = 0;
    public static int reeferConnectionTransshipmentQuantity = 0;

    public static double reeferConnectionImportTotal = 0;
    public static int reeferConnectionImportQuantity = 0;

    public static double reeferConnectionExportTotal = 0;
    public static int reeferConnectionExportQuantity = 0;

    public static double storageOfDamagedContainersTotal = 0;
    public static int storageOfDamagedContainersQuantity = 0;

    public static double[] storageFullExportInervalsTotal = { 0, 0, 0 };
    public static int[] storageFullExportInervalsQuantities = { 0, 0, 0 };

    public static double[] storageFullImportInervalsTotal = { 0, 0, 0 };
    public static int[] storageFullImportInervalsQuantities = { 0, 0, 0 };

    public static double[] storageFullTransshipmentInervalsTotal = { 0, 0, 0, 0, 0, 0 };
    public static int[] storageFullTransshipmentInervalsQuantities = { 0, 0, 0, 0, 0, 0 };

    public static double[] storageFullReeferTransshipmentInervalsTotal = { 0, 0, 0, 0, 0, 0 };
    public static int[] storageFullReeferTransshipmentInervalsQuantities = { 0, 0, 0, 0, 0, 0 };

    public static double[] storageFullReeferExportInervalsTotal = { 0, 0, 0, 0 };
    public static int[] storageFullReeferExportInervalsQuantities = { 0, 0, 0, 0 };

    public static double[] storageFullReeferImportInervalsTotal = { 0, 0, 0, 0 };
    public static int[] storageFullReeferImportInervalsQuantities = { 0, 0, 0, 0 };

    public static double storageOfOOGContainersTotal = 0;
    public static int storageOfOOGContainersQuantity = 0;

    public static double storageOfTankContainersTotal = 0;
    public static int storageOfTankContainersQuantity = 0;

    public static double emptyDepotContainersTotal = 0;
    public static int emptyDepotContainersQuantity = 0;

    public static double emptyExportContainersTotal = 0;
    public static int emptyExportContainersQuantity = 0;

    public static double emptyImportContainersTotal = 0;
    public static int emptyImportContainersQuantity = 0;

    public static double[] emptyTransshipmentContainersTotal = { 0, 0, 0, 0 };
    public static List<ContainerStorageDetailsDTO> containerStorageDetailsDTOList = new ArrayList<>();

    public static void setThisShipingLineAllDetailsToFile(String content, String shippingLine) throws IOException {
        if (fw == null)
            fw = new FileWriter("C:\\Users\\fayssal.ourezzouq\\Desktop\\storage reports\\June - 2022\\factures\\"
                    + shippingLine + "_Monthly_storage.csv", StandardCharsets.ISO_8859_1);
        if (bw == null)
            bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();

    }

    @Override
    public String toString() {
        return "ContainerStorageDetailsDTO{" +
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

    public ContainerStorageDetailsDTO(String shippingLine, String containerNumber, String type, String length,
            String invoiceCategory, String fullOrEmpty, boolean reef, boolean imdg, boolean oog, boolean dmg,
            Date incDate, Date outDate, int invoiceStorageDuration, double reeferSurcharge, double imdgSurcharge,
            double oogSurcharge, double dmgSurcharge, double tankSurcharge, double totalSurcharge,
            List<Double> storageOfEachIntervalInMonth, double totalStorageOfMonth) throws IOException {
        this.shippingLine = shippingLine;
        this.containerNumber = containerNumber;
        this.type = type;
        this.length = length;
        this.invoiceCategory = invoiceCategory;
        this.fullOrEmpty = fullOrEmpty;
        this.reef = reef;
        this.imdg = imdg;
        this.oog = oog;
        this.dmg = dmg;
        this.incDate = incDate;
        this.outDate = outDate;
        this.invoiceStorageDuration = invoiceStorageDuration;
        this.reeferSurcharge = reeferSurcharge;
        this.imdgSurcharge = imdgSurcharge;
        this.oogSurcharge = oogSurcharge;
        this.dmgSurcharge = dmgSurcharge;
        this.tankSurcharge = tankSurcharge;
        this.totalSurcharge = totalSurcharge;
        this.storageOfEachIntervalInMonth = storageOfEachIntervalInMonth;
        this.totalStorageOfMonth = totalStorageOfMonth;
        containerStorageDetailsDTOList.add(this);
    }

    public ContainerStorageDetailsDTO(String shippingLine, String containerNumber, String type, String length,
            String invoiceCategory, String fullOrEmpty, boolean reef, boolean imdg, boolean oog, boolean dmg,
            Date incDate, Date outDate, int invoiceStorageDuration, double reeferSurcharge, double imdgSurcharge,
            double oogSurcharge, double dmgSurcharge, double tankSurcharge, double totalSurcharge,
            List<Double> storageOfEachIntervalInMonth, double totalStorageOfMonth, boolean stillInYard)
            throws IOException {
        this.shippingLine = shippingLine;
        this.containerNumber = containerNumber;
        this.type = type;
        this.length = length;
        this.invoiceCategory = invoiceCategory;
        this.fullOrEmpty = fullOrEmpty;
        this.reef = reef;
        this.imdg = imdg;
        this.oog = oog;
        this.dmg = dmg;
        this.incDate = incDate;
        this.outDate = outDate;
        this.invoiceStorageDuration = invoiceStorageDuration;
        this.reeferSurcharge = reeferSurcharge;
        this.imdgSurcharge = imdgSurcharge;
        this.oogSurcharge = oogSurcharge;
        this.dmgSurcharge = dmgSurcharge;
        this.tankSurcharge = tankSurcharge;
        this.totalSurcharge = totalSurcharge;
        this.storageOfEachIntervalInMonth = storageOfEachIntervalInMonth;
        this.totalStorageOfMonth = totalStorageOfMonth;
        this.stillInYard = stillInYard;
        containerStorageDetailsDTOList.add(this);
    }

    public static void HLCSumMonthlyStorageDetails() throws ParseException, IOException {
        String content = "";
        hazardousImportTotal = 0;
        hazardousImportQuantity = 0;
        hazardousExportTotal = 0;
        hazardousExportQuantity = 0;
        hazardousTranssipmentTotal = 0;
        hazardousTransshipmentQuantity = 0;
        reeferConnectionTransshipmentTotal = 0;
        reeferConnectionTransshipmentQuantity = 0;
        reeferConnectionImportTotal = 0;
        reeferConnectionImportQuantity = 0;
        reeferConnectionExportTotal = 0;
        reeferConnectionExportQuantity = 0;
        storageOfDamagedContainersTotal = 0;
        storageOfDamagedContainersQuantity = 0;
        storageFullExportInervalsTotal = new double[] { 0, 0, 0 };
        storageFullExportInervalsQuantities = new int[] { 0, 0, 0 };
        storageFullImportInervalsTotal = new double[] { 0, 0, 0 };
        storageFullImportInervalsQuantities = new int[] { 0, 0, 0 };
        storageFullTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0 };
        storageFullTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0 };
        storageFullReeferTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0 };
        storageFullReeferTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0 };
        storageFullReeferExportInervalsTotal = new double[] { 0, 0, 0, 0 };
        storageFullReeferExportInervalsQuantities = new int[] { 0, 0, 0, 0 };
        storageFullReeferImportInervalsTotal = new double[] { 0, 0, 0, 0 };
        storageFullReeferImportInervalsQuantities = new int[] { 0, 0, 0, 0 };
        storageOfOOGContainersTotal = 0;
        storageOfOOGContainersQuantity = 0;
        storageOfTankContainersTotal = 0;
        storageOfTankContainersQuantity = 0;
        emptyDepotContainersTotal = 0;
        emptyDepotContainersQuantity = 0;
        emptyExportContainersTotal = 0;
        emptyExportContainersQuantity = 0;
        emptyImportContainersTotal = 0;
        emptyImportContainersQuantity = 0;
        emptyTransshipmentContainersTotal = new double[] { 0, 0, 0, 0 };
        List<Interval> fullExportIntervals = new ArrayList<>();
        List<Interval> fullImportIntervals = new ArrayList<>();
        List<Interval> fullTransshipmentIntervals = new ArrayList<>();
        List<Interval> fullReeferTransshipmentIntervals = new ArrayList<>();
        List<Interval> fullReeferDirectIntervals = new ArrayList<>();
        List<Interval> emptyImportOrExportIntervals = new ArrayList<>();
        fullReeferTransshipmentIntervals.add(new Interval(1, 5, 0));
        fullReeferTransshipmentIntervals.add(new Interval(6, 15, 3.92));
        fullReeferTransshipmentIntervals.add(new Interval(16, 30, 4.85));
        fullReeferTransshipmentIntervals.add(new Interval(31, 1000000, 7.40));
        fullExportIntervals.add(new Interval(1, 2, 0));
        fullExportIntervals.add(new Interval(3, 7, 1.4));
        fullExportIntervals.add(new Interval(8, 1000000, 3.78));
        fullImportIntervals.add(new Interval(1, 5, 0));
        fullImportIntervals.add(new Interval(6, 7, 1.4));
        fullImportIntervals.add(new Interval(8, 1000000, 3.78));
        fullReeferDirectIntervals.add(new Interval(1, 2, 0));
        fullReeferDirectIntervals.add(new Interval(3, 7, 2.8));
        fullReeferDirectIntervals.add(new Interval(8, 1000000, 7.56));
        fullTransshipmentIntervals.add(new Interval(1, 8, 0));
        fullTransshipmentIntervals.add(new Interval(9, 15, 2.95));
        fullTransshipmentIntervals.add(new Interval(16, 28, 3.66));
        fullTransshipmentIntervals.add(new Interval(29, 1000000, 5.48));
        emptyImportOrExportIntervals.add(new Interval(1, 5, 0));
        emptyImportOrExportIntervals.add(new Interval(3, 7, 1.05));
        emptyImportOrExportIntervals.add(new Interval(8, 1000000, 2.8));
        for (ContainerStorageDetailsDTO containerStorageDetailsDTO : containerStorageDetailsDTOList) {
            Container container = convertToContainer(containerStorageDetailsDTO);
            /*
             * System.err.println(container);
             * System.out.println(containerStorageDetailsDTO);
             */
            // Hazardous import Surcharge
            if (containerStorageDetailsDTO.imdg && containerStorageDetailsDTO.invoiceCategory.equals("Import")) {
                hazardousImportTotal += containerStorageDetailsDTO.imdgSurcharge;
                hazardousImportQuantity += getContainerTeus(container);
            }
            // Hazardous Export Surcharge
            if (containerStorageDetailsDTO.imdg && containerStorageDetailsDTO.invoiceCategory.equals("Export")) {
                hazardousExportTotal += containerStorageDetailsDTO.imdgSurcharge;
                hazardousExportQuantity += getContainerTeus(container);
            }
            // Hazardous Transshipment Surcharge
            if (containerStorageDetailsDTO.imdg && containerStorageDetailsDTO.invoiceCategory.equals("Transshipment")) {
                hazardousTranssipmentTotal += containerStorageDetailsDTO.imdgSurcharge;
                hazardousTransshipmentQuantity += getContainerTeus(container);
            }
            // reeferConnectionTransshipment & Direct Total surchagre
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.fullOrEmpty.equals("Full")
                    && containerStorageDetailsDTO.invoiceCategory.equals("Transshipment")) {

                int numOfDays = numberOfDaysForEachIntervalInMonth(container, fullReeferTransshipmentIntervals, "10",
                        "2021").stream().mapToInt(Integer::intValue).sum();
                if (container.isReef() && container.getInvoiceCategory().equals("Transshipment")) {
                    reeferConnectionTransshipmentTotal += DoubleRounder.round((numOfDays * 30.49), 2);
                    reeferConnectionTransshipmentQuantity++;
                }
                if (container.isReef() && container.getInvoiceCategory().equals("Import")) {
                    reeferConnectionImportTotal += DoubleRounder.round(numOfDays * 34.83, 2);
                    reeferConnectionImportQuantity++;
                }
                if (container.isReef() && container.getInvoiceCategory().equals("Export")) {
                    reeferConnectionExportTotal += DoubleRounder.round(numOfDays * 34.83, 2);
                    reeferConnectionExportQuantity++;
                }
            }
            // storageOfDamagedContainers surcharge
            if (containerStorageDetailsDTO.imdg) {
                storageOfDamagedContainersTotal += containerStorageDetailsDTO.dmgSurcharge;
                storageOfDamagedContainersQuantity += getContainerTeus(container);
            }
            // storageOfOOGContainers surchagre
            if (containerStorageDetailsDTO.oog) {
                storageOfOOGContainersTotal += containerStorageDetailsDTO.oogSurcharge;
                storageOfOOGContainersQuantity += getContainerTeus(container);
            }
            // storageOfTankContainers surchagre
            if (containerStorageDetailsDTO.type.equals("TK") || containerStorageDetailsDTO.type.equals("TO")) {
                storageOfTankContainersTotal += containerStorageDetailsDTO.tankSurcharge;
                storageOfTankContainersQuantity += getContainerTeus(container);
            }

            // storageFullExportInervalsTotal
            if (!containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Export")
                    && containerStorageDetailsDTO.fullOrEmpty.equals("Full")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullExportIntervals, "10", "2021");
                int numberOfTeus = getContainerTeus(container);
                double price = 0;
                price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                        * fullExportIntervals.get(0).getPrice();
                storageFullExportInervalsTotal[0] += price;
                storageFullExportInervalsQuantities[0] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                        * fullExportIntervals.get(1).getPrice();
                storageFullExportInervalsTotal[1] += price;
                storageFullExportInervalsQuantities[1] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                        * fullExportIntervals.get(2).getPrice();
                storageFullExportInervalsTotal[2] += price;
                storageFullExportInervalsQuantities[2] += (price != 0) ? numberOfTeus : 0;
            }
            // storageFullImportInervalsTotal
            if (!containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Import")
                    && containerStorageDetailsDTO.fullOrEmpty.equals("Full")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullImportIntervals, "10", "2021");
                int numberOfTeus = getContainerTeus(container);

                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                        * fullImportIntervals.get(0).getPrice();
                storageFullImportInervalsTotal[0] += price;
                storageFullImportInervalsQuantities[0] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                        * fullImportIntervals.get(1).getPrice();
                storageFullImportInervalsTotal[1] += price;
                storageFullImportInervalsQuantities[1] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                        * fullImportIntervals.get(2).getPrice();
                storageFullImportInervalsTotal[2] += price;
                storageFullImportInervalsQuantities[2] += (price != 0) ? numberOfTeus : 0;

            }
            // storageFullTransshipmentInervalsTotal
            if (!containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Transshipment")
                    && containerStorageDetailsDTO.fullOrEmpty.equals("Full")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullTransshipmentIntervals, "10", "2021");
                int numberOfTeus = getContainerTeus(container);

                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                        * fullTransshipmentIntervals.get(0).getPrice();
                storageFullTransshipmentInervalsTotal[0] += price;
                storageFullTransshipmentInervalsQuantities[0] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                        * fullTransshipmentIntervals.get(1).getPrice();
                storageFullTransshipmentInervalsTotal[1] += price;
                storageFullTransshipmentInervalsQuantities[1] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                        * fullTransshipmentIntervals.get(2).getPrice();
                storageFullTransshipmentInervalsTotal[2] += price;
                storageFullTransshipmentInervalsQuantities[2] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                        * fullTransshipmentIntervals.get(3).getPrice();
                storageFullTransshipmentInervalsTotal[3] += price;
                storageFullTransshipmentInervalsQuantities[3] += (price != 0) ? numberOfTeus : 0;
            }
            // storageFullReeferTransshipmentInervals
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Transshipment")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullReeferTransshipmentIntervals, "10", "2021");

                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                        * fullReeferTransshipmentIntervals.get(0).getPrice();
                storageFullReeferTransshipmentInervalsTotal[0] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[0]++;

                price = numberOfDaysForEachIntervalInMonthList.get(1)
                        * fullReeferTransshipmentIntervals.get(1).getPrice();
                storageFullReeferTransshipmentInervalsTotal[1] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[1]++;

                price = numberOfDaysForEachIntervalInMonthList.get(2)
                        * fullReeferTransshipmentIntervals.get(2).getPrice();
                storageFullReeferTransshipmentInervalsTotal[2] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[2]++;

                price = numberOfDaysForEachIntervalInMonthList.get(3)
                        * fullReeferTransshipmentIntervals.get(3).getPrice();
                storageFullReeferTransshipmentInervalsTotal[3] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[3]++;

            }
            // storageFullReeferExportInervals
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Export")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullReeferDirectIntervals, "10", "2021");
                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                        * fullReeferDirectIntervals.get(0).getPrice();
                storageFullReeferExportInervalsTotal[0] += price;
                if (price != 0)
                    storageFullReeferExportInervalsQuantities[0]++;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * fullReeferDirectIntervals.get(1).getPrice();
                storageFullReeferExportInervalsTotal[1] += price;
                if (price != 0)
                    storageFullReeferExportInervalsQuantities[1]++;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * fullReeferDirectIntervals.get(2).getPrice();
                storageFullReeferExportInervalsTotal[2] += price;
                if (price != 0)
                    storageFullReeferExportInervalsQuantities[2]++;

            }
            // storageFullReeferImportInervals
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Import")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullReeferDirectIntervals, "10", "2021");
                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                        * fullReeferDirectIntervals.get(0).getPrice();
                storageFullReeferImportInervalsTotal[0] += price;
                if (price != 0)
                    storageFullReeferImportInervalsQuantities[0]++;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * fullReeferDirectIntervals.get(1).getPrice();
                storageFullReeferImportInervalsTotal[1] += price;
                if (price != 0)
                    storageFullReeferImportInervalsQuantities[1]++;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * fullReeferDirectIntervals.get(2).getPrice();
                storageFullReeferImportInervalsTotal[2] += price;
                if (price != 0)
                    storageFullReeferImportInervalsQuantities[2]++;

            }

        }
        content += "Description;Customer;Unit Measure;Price per Unit;Quantity;Tot Amount\n";
        // if(hazardousImportTotal!=0)
        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 22,14" + ";" + hazardousImportQuantity + ";" + DoubleRounder.round(hazardousImportTotal, 2) + "\n";

        // if(hazardousExportTotal!=0)
        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 22,14" + ";" + hazardousExportQuantity + ";" + DoubleRounder.round(hazardousExportTotal, 2) + "\n";

        // if(hazardousTranssipmentTotal!=0)
        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 22,14" + ";" + hazardousTransshipmentQuantity + ";"
                + DoubleRounder.round(hazardousTranssipmentTotal, 2) + "\n";

        // if(reeferConnectionTransshipmentTotal!=0)
        content += "Reefer connection Transhipment (storage not included) " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 30,49" + ";" + reeferConnectionTransshipmentQuantity + ";"
                + DoubleRounder.round(reeferConnectionTransshipmentTotal, 2) + "\n";

        // if(reeferConnectionImportTotal!=0)
        content += "Reefer connection Import (storage not included) " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 34,83" + ";" + reeferConnectionImportQuantity + ";"
                + DoubleRounder.round(reeferConnectionImportTotal, 2) + "\n";

        // if(reeferConnectionExportTotal!=0)
        content += "Reefer connection Export (storage not included) " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 34,83" + ";" + reeferConnectionExportQuantity + ";"
                + DoubleRounder.round(reeferConnectionExportTotal, 2) + "\n";

        // if(storageOfDamagedContainersTotal!=0)
        content += "Storage damaged Containers surcharge  " + ";" + "HLC" + ";" + "Quote %200" + ";" + "" + ";"
                + storageOfDamagedContainersQuantity + ";" + storageOfDamagedContainersTotal + "\n";

        // if(storageFullExportInervalsTotal[0]!=0)
        content += "Storage Full Export First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullExportIntervals.get(0).getPrice() + ";" + storageFullExportInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullExportInervalsTotal[0], 2) + "\n";
        // if(storageFullExportInervalsTotal[1]!=0)
        content += "Storage Full Export Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullExportIntervals.get(1).getPrice() + ";" + storageFullExportInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullExportInervalsTotal[1], 2) + "\n";
        // if(storageFullExportInervalsTotal[2]!=0)
        content += "Storage Full Export Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullExportIntervals.get(2).getPrice() + ";" + storageFullExportInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullExportInervalsTotal[2], 2) + "\n";

        // if(storageFullImportInervalsTotal[0]!=0)
        content += "Storage Full Import First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullImportIntervals.get(0).getPrice() + ";" + storageFullImportInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullImportInervalsTotal[0], 2) + "\n";
        // if(storageFullImportInervalsTotal[1]!=0)
        content += "Storage Full Import Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullImportIntervals.get(1).getPrice() + ";" + storageFullImportInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullImportInervalsTotal[1], 2) + "\n";
        // if(storageFullImportInervalsTotal[2]!=0)
        content += "Storage Full Import Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullImportIntervals.get(2).getPrice() + ";" + storageFullImportInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullImportInervalsTotal[2], 2) + "\n";

        // if(storageFullTransshipmentInervalsTotal[0]!=0)
        content += "Storage Full Transshipment First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(0).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[0]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[0], 2) + "\n";
        // if(storageFullTransshipmentInervalsTotal[1]!=0)
        content += "Storage Full Transshipment Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(1).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[1]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[1], 2) + "\n";
        // if(storageFullTransshipmentInervalsTotal[2]!=0)
        content += "Storage Full Transshipment Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(2).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[2]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[2], 2) + "\n";
        // if(storageFullTransshipmentInervalsTotal[3]!=0)
        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(3).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[3]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[3], 2) + "\n";

        // if(storageFullReeferTransshipmentInervalsTotal[0]!=0)
        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(0).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[0], 2) + "\n";
        // if(storageFullReeferTransshipmentInervalsTotal[1]!=0)
        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(1).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[1], 2) + "\n";
        // if(storageFullReeferTransshipmentInervalsTotal[2]!=0)
        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(2).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[2], 2) + "\n";
        // if(storageFullReeferTransshipmentInervalsTotal[3]!=0)
        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(3).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[3] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[3], 2) + "\n";

        // if(storageFullReeferExportInervalsTotal[0]!=0)
        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(0).getPrice() + ";" + storageFullReeferExportInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullReeferExportInervalsTotal[0], 2) + "\n";
        // if(storageFullReeferExportInervalsTotal[1]!=0)
        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(1).getPrice() + ";" + storageFullReeferExportInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullReeferExportInervalsTotal[1], 2) + "\n";
        // if(storageFullReeferExportInervalsTotal[2]!=0)
        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(2).getPrice() + ";" + storageFullReeferExportInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullReeferExportInervalsTotal[2], 2) + "\n";
        // if(storageFullReeferExportInervalsTotal[3]!=0)
        content += "Storage Full Reefer Export Fourth Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(3).getPrice() + ";" + storageFullReeferExportInervalsQuantities[3] + ";"
                + DoubleRounder.round(storageFullReeferExportInervalsTotal[3], 2) + "\n";

        // if(storageFullReeferImportInervalsTotal[0]!=0)
        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(0).getPrice() + ";" + storageFullReeferImportInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullReeferImportInervalsTotal[0], 2) + "\n";
        // if(storageFullReeferImportInervalsTotal[1]!=0)
        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(1).getPrice() + ";" + storageFullReeferImportInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullReeferImportInervalsTotal[1], 2) + "\n";
        // if(storageFullReeferImportInervalsTotal[2]!=0)
        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(2).getPrice() + ";" + storageFullReeferImportInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullReeferImportInervalsTotal[2], 2) + "\n";
        // if(storageFullReeferImportInervalsTotal[3]!=0)
        content += "Storage Full Reefer Import Fourth Period Revenue " + ";" + "HLC" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(3).getPrice() + ";" + storageFullReeferImportInervalsQuantities[3] + ";"
                + DoubleRounder.round(storageFullReeferImportInervalsTotal[3], 2) + "\n";

        // if(storageOfOOGContainersTotal!=0)
        content += "Storage surcharge Non-Standard Container " + ";" + "HLC" + ";" + "Quote %100" + ";" + "" + ";"
                + storageOfOOGContainersQuantity + ";" + DoubleRounder.round(storageOfOOGContainersTotal, 2) + "\n";

        // if(storageOfTankContainersTotal!=0)
        content += "Storage tank Containers surcharge " + ";" + "HLC" + ";" + "Quote %100" + ";" + "" + ";"
                + storageOfTankContainersQuantity + ";" + DoubleRounder.round(storageOfTankContainersTotal, 2) + "\n";
        double total = hazardousImportTotal
                + hazardousExportTotal
                + hazardousTranssipmentTotal
                + reeferConnectionTransshipmentTotal
                + reeferConnectionImportTotal
                + reeferConnectionExportTotal
                + storageOfDamagedContainersTotal
                + storageFullExportInervalsTotal[0]
                + storageFullExportInervalsTotal[1]
                + storageFullExportInervalsTotal[2]
                + storageFullImportInervalsTotal[0]
                + storageFullImportInervalsTotal[1]
                + storageFullImportInervalsTotal[2]
                + storageFullTransshipmentInervalsTotal[0]
                + storageFullTransshipmentInervalsTotal[1]
                + storageFullTransshipmentInervalsTotal[2]
                + storageFullTransshipmentInervalsTotal[3]
                + storageFullReeferTransshipmentInervalsTotal[0]
                + storageFullReeferTransshipmentInervalsTotal[1]
                + storageFullReeferTransshipmentInervalsTotal[2]
                + storageFullReeferTransshipmentInervalsTotal[3]
                + storageFullReeferExportInervalsTotal[0]
                + storageFullReeferExportInervalsTotal[1]
                + storageFullReeferExportInervalsTotal[2]
                + storageFullReeferExportInervalsTotal[3]
                + storageFullReeferImportInervalsTotal[0]
                + storageFullReeferImportInervalsTotal[1]
                + storageFullReeferImportInervalsTotal[2]
                + storageFullReeferImportInervalsTotal[3]
                + storageOfOOGContainersTotal
                + storageOfTankContainersTotal
                + emptyDepotContainersTotal
                + emptyExportContainersTotal
                + emptyImportContainersTotal
                + emptyTransshipmentContainersTotal[0]
                + emptyTransshipmentContainersTotal[1]
                + emptyTransshipmentContainersTotal[2]
                + emptyTransshipmentContainersTotal[3];
        total = DoubleRounder.round(total, 2);
        content += "Total " + ";" + " " + ";" + " " + ";" + " " + ";" + ";" + total + "\n";
        // setThisShipingLineAllDetailsToFile(content,"HLC");

    }

    public static void ARKSumMonthlyStorageDetails() throws ParseException, IOException {
        String content = "";
        hazardousImportTotal = 0;
        hazardousImportQuantity = 0;
        hazardousExportTotal = 0;
        hazardousExportQuantity = 0;
        hazardousTranssipmentTotal = 0;
        hazardousTransshipmentQuantity = 0;
        reeferConnectionTransshipmentTotal = 0;
        reeferConnectionTransshipmentQuantity = 0;
        reeferConnectionImportTotal = 0;
        reeferConnectionImportQuantity = 0;
        reeferConnectionExportTotal = 0;
        reeferConnectionExportQuantity = 0;
        storageOfDamagedContainersTotal = 0;
        storageOfDamagedContainersQuantity = 0;
        storageFullExportInervalsTotal = new double[] { 0, 0, 0 };
        storageFullExportInervalsQuantities = new int[] { 0, 0, 0 };
        storageFullImportInervalsTotal = new double[] { 0, 0, 0 };
        storageFullImportInervalsQuantities = new int[] { 0, 0, 0 };
        storageFullTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
        storageFullTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
        storageFullReeferTransshipmentInervalsTotal = new double[] { 0, 0, 0, 0, 0, 0 };
        storageFullReeferTransshipmentInervalsQuantities = new int[] { 0, 0, 0, 0, 0, 0 };
        storageFullReeferExportInervalsTotal = new double[] { 0, 0, 0, 0 };
        storageFullReeferExportInervalsQuantities = new int[] { 0, 0, 0, 0 };
        storageFullReeferImportInervalsTotal = new double[] { 0, 0, 0, 0 };
        storageFullReeferImportInervalsQuantities = new int[] { 0, 0, 0, 0 };
        storageOfOOGContainersTotal = 0;
        storageOfOOGContainersQuantity = 0;
        storageOfTankContainersTotal = 0;
        storageOfTankContainersQuantity = 0;
        emptyDepotContainersTotal = 0;
        emptyDepotContainersQuantity = 0;
        emptyExportContainersTotal = 0;
        emptyExportContainersQuantity = 0;
        emptyImportContainersTotal = 0;
        emptyImportContainersQuantity = 0;
        emptyTransshipmentContainersTotal = new double[] { 0, 0, 0, 0 };
        List<Interval> fullExportIntervals = new ArrayList<>();
        List<Interval> fullImportIntervals = new ArrayList<>();
        List<Interval> fullTransshipmentIntervals = new ArrayList<>();
        List<Interval> fullReeferTransshipmentIntervals = new ArrayList<>();
        List<Interval> fullReeferDirectIntervals = new ArrayList<>();
        List<Interval> emptyImportOrExportIntervals = new ArrayList<>();

        fullReeferTransshipmentIntervals.add(new Interval(1, 8, 0));
        fullReeferTransshipmentIntervals.add(new Interval(9, 15, 3));
        fullReeferTransshipmentIntervals.add(new Interval(16, 21, 6));
        fullReeferTransshipmentIntervals.add(new Interval(22, 28, 9));
        fullReeferTransshipmentIntervals.add(new Interval(29, 45, 12.9));
        fullReeferTransshipmentIntervals.add(new Interval(46, 10000, 36));

        fullExportIntervals.add(new Interval(1, 2, 0));
        fullExportIntervals.add(new Interval(3, 7, 1.4));
        fullExportIntervals.add(new Interval(8, 1000000, 3.78));

        fullImportIntervals.add(new Interval(1, 5, 0));
        fullImportIntervals.add(new Interval(6, 7, 1.4));
        fullImportIntervals.add(new Interval(8, 1000000, 3.78));

        fullReeferDirectIntervals.add(new Interval(1, 2, 0));
        fullReeferDirectIntervals.add(new Interval(3, 7, 2.8));
        fullReeferDirectIntervals.add(new Interval(8, 1000000, 7.56));

        fullTransshipmentIntervals.add(new Interval(1, 8, 0));
        fullTransshipmentIntervals.add(new Interval(9, 15, 3));
        fullTransshipmentIntervals.add(new Interval(16, 21, 6));
        fullTransshipmentIntervals.add(new Interval(22, 28, 9));
        fullTransshipmentIntervals.add(new Interval(29, 45, 12.9));
        fullTransshipmentIntervals.add(new Interval(46, 10000, 36));

        emptyImportOrExportIntervals.add(new Interval(1, 5, 0));
        emptyImportOrExportIntervals.add(new Interval(3, 7, 1.05));
        emptyImportOrExportIntervals.add(new Interval(8, 1000000, 2.8));

        for (ContainerStorageDetailsDTO containerStorageDetailsDTO : containerStorageDetailsDTOList) {
            Container container = convertToContainer(containerStorageDetailsDTO);
            /*
             * System.err.println(container);
             * System.out.println(containerStorageDetailsDTO);
             */
            // Hazardous import Surcharge
            if (containerStorageDetailsDTO.imdg && containerStorageDetailsDTO.invoiceCategory.equals("Import")) {
                hazardousImportTotal += containerStorageDetailsDTO.imdgSurcharge;
                hazardousImportQuantity += getContainerTeus(container);
            }
            // Hazardous Export Surcharge
            if (containerStorageDetailsDTO.imdg && containerStorageDetailsDTO.invoiceCategory.equals("Export")) {
                hazardousExportTotal += containerStorageDetailsDTO.imdgSurcharge;
                hazardousExportQuantity += getContainerTeus(container);
            }
            // Hazardous Transshipment Surcharge
            if (containerStorageDetailsDTO.imdg && containerStorageDetailsDTO.invoiceCategory.equals("Transshipment")) {
                hazardousTranssipmentTotal += containerStorageDetailsDTO.imdgSurcharge;
                hazardousTransshipmentQuantity += getContainerTeus(container);
            }
            // reeferConnectionTransshipment & Direct Total surchagre
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.fullOrEmpty.equals("Full")
                    && containerStorageDetailsDTO.invoiceCategory.equals("Transshipment")) {
                int numOfDays = numberOfDaysForEachIntervalInMonth(container, fullReeferTransshipmentIntervals, "10",
                        "2021").stream().mapToInt(Integer::intValue).sum();
                reeferConnectionTransshipmentTotal += DoubleRounder.round((numOfDays * 40), 2);
                reeferConnectionTransshipmentQuantity++;
            }
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.fullOrEmpty.equals("Full")
                    && containerStorageDetailsDTO.invoiceCategory.equals("Import")) {
                int numOfDays = numberOfDaysForEachIntervalInMonth(container, fullReeferTransshipmentIntervals, "10",
                        "2021").stream().mapToInt(Integer::intValue).sum();
                reeferConnectionImportTotal += DoubleRounder.round(numOfDays * 34.83, 2);
                reeferConnectionImportQuantity++;
            }
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.fullOrEmpty.equals("Full")
                    && containerStorageDetailsDTO.invoiceCategory.equals("Export")) {
                int numOfDays = numberOfDaysForEachIntervalInMonth(container, fullReeferTransshipmentIntervals, "10",
                        "2021").stream().mapToInt(Integer::intValue).sum();
                reeferConnectionExportTotal += DoubleRounder.round(numOfDays * 34.83, 2);
                reeferConnectionExportQuantity++;
            }

            // storageOfDamagedContainers surcharge
            if (containerStorageDetailsDTO.dmg) {
                storageOfDamagedContainersTotal += containerStorageDetailsDTO.dmgSurcharge;
                storageOfDamagedContainersQuantity += getContainerTeus(container);
            }
            // storageOfOOGContainers surchagre
            if (containerStorageDetailsDTO.oog) {
                storageOfOOGContainersTotal += containerStorageDetailsDTO.oogSurcharge;
                storageOfOOGContainersQuantity += getContainerTeus(container);
            }
            // storageOfTankContainers surchagre
            if (containerStorageDetailsDTO.type.equals("TK") || containerStorageDetailsDTO.type.equals("TO")) {
                storageOfTankContainersTotal += containerStorageDetailsDTO.tankSurcharge;
                storageOfTankContainersQuantity += getContainerTeus(container);
            }

            // storageFullExportInervalsTotal
            if (!containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Export")
                    && containerStorageDetailsDTO.fullOrEmpty.equals("Full")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullExportIntervals, "10", "2021");
                int numberOfTeus = getContainerTeus(container);
                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                        * fullExportIntervals.get(0).getPrice();
                storageFullExportInervalsTotal[0] += price;
                storageFullExportInervalsQuantities[0] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                        * fullExportIntervals.get(1).getPrice();
                storageFullExportInervalsTotal[1] += price;
                storageFullExportInervalsQuantities[1] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                        * fullExportIntervals.get(2).getPrice();
                storageFullExportInervalsTotal[2] += price;
                storageFullExportInervalsQuantities[2] += (price != 0) ? numberOfTeus : 0;
            }
            // storageFullImportInervalsTotal
            if (!containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Import")
                    && containerStorageDetailsDTO.fullOrEmpty.equals("Full")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullImportIntervals, "10", "2021");
                int numberOfTeus = getContainerTeus(container);
                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                        * fullImportIntervals.get(0).getPrice();
                storageFullImportInervalsTotal[0] += price;
                storageFullImportInervalsQuantities[0] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                        * fullImportIntervals.get(1).getPrice();
                storageFullImportInervalsTotal[1] += price;
                storageFullImportInervalsQuantities[1] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                        * fullImportIntervals.get(2).getPrice();
                storageFullImportInervalsTotal[2] += price;
                storageFullImportInervalsQuantities[2] += (price != 0) ? numberOfTeus : 0;
            }
            // storageFullTransshipmentInervalsTotal
            if (!containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Transshipment")
                    && containerStorageDetailsDTO.fullOrEmpty.equals("Full")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullTransshipmentIntervals, "10", "2021");
                int numberOfTeus = getContainerTeus(container);
                double price = numberOfDaysForEachIntervalInMonthList.get(0) * numberOfTeus
                        * fullTransshipmentIntervals.get(0).getPrice();
                storageFullTransshipmentInervalsTotal[0] += price;
                storageFullTransshipmentInervalsQuantities[0] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * numberOfTeus
                        * fullTransshipmentIntervals.get(1).getPrice();
                storageFullTransshipmentInervalsTotal[1] += price;
                storageFullTransshipmentInervalsQuantities[1] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * numberOfTeus
                        * fullTransshipmentIntervals.get(2).getPrice();
                storageFullTransshipmentInervalsTotal[2] += price;
                storageFullTransshipmentInervalsQuantities[2] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(3) * numberOfTeus
                        * fullTransshipmentIntervals.get(3).getPrice();
                storageFullTransshipmentInervalsTotal[3] += price;
                storageFullTransshipmentInervalsQuantities[3] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(4) * numberOfTeus
                        * fullTransshipmentIntervals.get(4).getPrice();
                storageFullTransshipmentInervalsTotal[4] += price;
                storageFullTransshipmentInervalsQuantities[4] += (price != 0) ? numberOfTeus : 0;

                price = numberOfDaysForEachIntervalInMonthList.get(5) * numberOfTeus
                        * fullTransshipmentIntervals.get(5).getPrice();
                storageFullTransshipmentInervalsTotal[5] += price;
                storageFullTransshipmentInervalsQuantities[5] += (price != 0) ? numberOfTeus : 0;
            }
            // storageFullReeferTransshipmentInervals
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Transshipment")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullReeferTransshipmentIntervals, "10", "2021");

                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                        * fullReeferTransshipmentIntervals.get(0).getPrice();
                storageFullReeferTransshipmentInervalsTotal[0] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[0]++;

                price = numberOfDaysForEachIntervalInMonthList.get(1)
                        * fullReeferTransshipmentIntervals.get(1).getPrice();
                storageFullReeferTransshipmentInervalsTotal[1] += numberOfDaysForEachIntervalInMonthList.get(1)
                        * fullReeferTransshipmentIntervals.get(1).getPrice();
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[1]++;

                price = numberOfDaysForEachIntervalInMonthList.get(2)
                        * fullReeferTransshipmentIntervals.get(2).getPrice();
                storageFullReeferTransshipmentInervalsTotal[2] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[2]++;

                price = numberOfDaysForEachIntervalInMonthList.get(3)
                        * fullReeferTransshipmentIntervals.get(3).getPrice();
                storageFullReeferTransshipmentInervalsTotal[3] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[3]++;

                price = numberOfDaysForEachIntervalInMonthList.get(4)
                        * fullReeferTransshipmentIntervals.get(4).getPrice();
                storageFullReeferTransshipmentInervalsTotal[4] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[4]++;

                price = numberOfDaysForEachIntervalInMonthList.get(5)
                        * fullReeferTransshipmentIntervals.get(5).getPrice();
                storageFullReeferTransshipmentInervalsTotal[5] += price;
                if (price != 0)
                    storageFullReeferTransshipmentInervalsQuantities[5]++;
            }
            // storageFullReeferExportInervals
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Export")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullReeferDirectIntervals, "10", "2021");

                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                        * fullReeferDirectIntervals.get(0).getPrice();
                storageFullReeferExportInervalsTotal[0] += price;
                if (price != 0)
                    storageFullReeferExportInervalsQuantities[0]++;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * fullReeferDirectIntervals.get(1).getPrice();
                storageFullReeferExportInervalsTotal[1] += price;
                if (price != 0)
                    storageFullReeferExportInervalsQuantities[1]++;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * fullReeferDirectIntervals.get(2).getPrice();
                storageFullReeferExportInervalsTotal[2] += price;
                if (price != 0)
                    storageFullReeferExportInervalsQuantities[2]++;

            }
            // storageFullReeferImportInervals
            if (containerStorageDetailsDTO.reef && containerStorageDetailsDTO.invoiceCategory.equals("Import")) {
                List<Integer> numberOfDaysForEachIntervalInMonthList = numberOfDaysForEachIntervalInMonth(container,
                        fullReeferDirectIntervals, "10", "2021");
                double price = numberOfDaysForEachIntervalInMonthList.get(0)
                        * fullReeferDirectIntervals.get(0).getPrice();
                storageFullReeferImportInervalsTotal[0] += price;
                if (price != 0)
                    storageFullReeferImportInervalsQuantities[0]++;

                price = numberOfDaysForEachIntervalInMonthList.get(1) * fullReeferDirectIntervals.get(1).getPrice();
                storageFullReeferImportInervalsTotal[1] += price;
                if (price != 0)
                    storageFullReeferImportInervalsQuantities[1]++;

                price = numberOfDaysForEachIntervalInMonthList.get(2) * fullReeferDirectIntervals.get(2).getPrice();
                storageFullReeferImportInervalsTotal[2] += price;
                if (price != 0)
                    storageFullReeferImportInervalsQuantities[2]++;

            }

            /*
             * //emptyDepotContainers
             * if(containerStorageDetailsDTO.fullOrEmpty.equals("Empty") &&
             * container.isStillInYard() &&
             * (containerStorageDetailsDTO.invoiceCategory.equals("Export") ||
             * containerStorageDetailsDTO.invoiceCategory.equals("Import"))){
             * List<Integer> numberOfDaysForEachIntervalInMonthList=
             * numberOfDaysForEachIntervalInMonth(container,emptyImportOrExportIntervals,
             * "10","2021");
             * int numberOfTeus=getContainerTeus(container);
             * 
             * emptyDepotContainersTotal=numberOfDaysForEachIntervalInMonthList.get(0)*
             * emptyImportOrExportIntervals.get(0).getPrice();
             * emptyDepotContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(0)!
             * =0)?numberOfTeus:0;
             * 
             * emptyDepotContainersTotal=numberOfDaysForEachIntervalInMonthList.get(1)*
             * emptyImportOrExportIntervals.get(1).getPrice();
             * emptyDepotContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(1)!
             * =0)?numberOfTeus:0;
             * 
             * emptyDepotContainersTotal=numberOfDaysForEachIntervalInMonthList.get(2)*
             * emptyImportOrExportIntervals.get(2).getPrice();
             * emptyDepotContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(2)!
             * =0)?numberOfTeus:0;
             * }
             * //emptyExportContainers
             * if(containerStorageDetailsDTO.fullOrEmpty.equals("Empty") &&
             * !container.isStillInYard() &&
             * containerStorageDetailsDTO.invoiceCategory.equals("Export") ){
             * List<Integer> numberOfDaysForEachIntervalInMonthList=
             * numberOfDaysForEachIntervalInMonth(container,emptyImportOrExportIntervals,
             * "10","2021");
             * int numberOfTeus=getContainerTeus(container);
             * 
             * emptyExportContainersTotal=numberOfDaysForEachIntervalInMonthList.get(0)*
             * emptyImportOrExportIntervals.get(0).getPrice();
             * emptyExportContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(0)
             * !=0)?numberOfTeus:0;
             * 
             * emptyExportContainersTotal=numberOfDaysForEachIntervalInMonthList.get(1)*
             * emptyImportOrExportIntervals.get(1).getPrice();
             * emptyExportContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(1)
             * !=0)?numberOfTeus:0;
             * 
             * emptyExportContainersTotal=numberOfDaysForEachIntervalInMonthList.get(2)*
             * emptyImportOrExportIntervals.get(2).getPrice();
             * emptyExportContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(2)
             * !=0)?numberOfTeus:0;
             * }
             * //emptyImportContainers
             * if(containerStorageDetailsDTO.fullOrEmpty.equals("Empty") &&
             * !container.isStillInYard() &&
             * containerStorageDetailsDTO.invoiceCategory.equals("Import") ){
             * List<Integer> numberOfDaysForEachIntervalInMonthList=
             * numberOfDaysForEachIntervalInMonth(container,emptyImportOrExportIntervals,
             * "10","2021");
             * int numberOfTeus=getContainerTeus(container);
             * 
             * emptyImportContainersTotal=numberOfDaysForEachIntervalInMonthList.get(0)*
             * emptyImportOrExportIntervals.get(0).getPrice();
             * emptyImportContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(0)
             * !=0)?numberOfTeus:0;
             * 
             * emptyImportContainersTotal=numberOfDaysForEachIntervalInMonthList.get(1)*
             * emptyImportOrExportIntervals.get(1).getPrice();
             * emptyImportContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(1)
             * !=0)?numberOfTeus:0;
             * 
             * emptyImportContainersTotal=numberOfDaysForEachIntervalInMonthList.get(2)*
             * emptyImportOrExportIntervals.get(2).getPrice();
             * emptyImportContainersQuantity+=(numberOfDaysForEachIntervalInMonthList.get(2)
             * !=0)?numberOfTeus:0;
             * }
             * 
             * System.out.println("storageFullExportInervalsTotal 0"
             * +storageFullExportInervalsTotal[0]);
             * System.out.println("storageFullExportInervalsTotal 0"
             * +storageFullExportInervalsTotal[0]);
             * System.out.println("storageFullExportInervalsTotal 1"
             * +storageFullExportInervalsTotal[1]);
             * System.out.println("storageFullExportInervalsTotal 1"
             * +storageFullExportInervalsTotal[1]);
             * System.out.println("storageFullExportInervalsTotal 2"
             * +storageFullExportInervalsTotal[2]);
             * System.out.println("storageFullExportInervalsTotal 2"
             * +storageFullExportInervalsTotal[2]);
             * System.out.println("storageFullImportInervalsTotal 2"
             * +storageFullImportInervalsTotal[2]);
             * 
             * System.out.println("storageFullTransshipmentInervalsTotal 0"
             * +storageFullTransshipmentInervalsTotal[0]);
             * System.out.println("storageFullTransshipmentInervalsTotal 1"
             * +storageFullTransshipmentInervalsTotal[1]);
             * System.out.println("storageFullTransshipmentInervalsTotal 2"
             * +storageFullTransshipmentInervalsTotal[2]);
             * System.out.println("storageFullTransshipmentInervalsTotal 2"
             * +storageFullTransshipmentInervalsTotal[3]);
             * 
             * System.out.println("storageFullReeferTransshipmentInervalsTotal 0"
             * +storageFullReeferTransshipmentInervalsTotal[0]);
             * System.out.println("storageFullReeferTransshipmentInervalsTotal 1"
             * +storageFullReeferTransshipmentInervalsTotal[1]);
             * System.out.println("storageFullReeferTransshipmentInervalsTotal 2"
             * +storageFullReeferTransshipmentInervalsTotal[2]);
             * System.out.println("storageFullReeferTransshipmentInervalsTotal 2"
             * +storageFullReeferTransshipmentInervalsTotal[3]);
             */

        }
        content += "Description;Customer;Unit Measure;Price per Unit;Quantity;Tot Amount\n";
        // if(hazardousImportTotal!=0)
        content += "Hazardous class except class 1 , 6.2, 7 Import " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 23,61" + ";" + hazardousImportQuantity + ";" + DoubleRounder.round(hazardousImportTotal, 2) + "\n";

        // if(hazardousExportTotal!=0)
        content += "Hazardous class except class 1 , 6.2, 7 Export " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 23,61" + ";" + hazardousExportQuantity + ";" + DoubleRounder.round(hazardousExportTotal, 2) + "\n";

        // if(hazardousTranssipmentTotal!=0)
        content += "Hazardous class except class 1 , 6.2, 7 Transhipment " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 23,61" + ";" + hazardousTransshipmentQuantity + ";"
                + DoubleRounder.round(hazardousTranssipmentTotal, 2) + "\n";

        // if(reeferConnectionTransshipmentTotal!=0)
        content += "Reefer connection Transhipment (storage not included) " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 40,00" + ";" + reeferConnectionTransshipmentQuantity + ";"
                + DoubleRounder.round(reeferConnectionTransshipmentTotal, 2) + "\n";

        // if(reeferConnectionImportTotal!=0)
        content += "Reefer connection Import (storage not included) " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 34,83" + ";" + reeferConnectionImportQuantity + ";"
                + DoubleRounder.round(reeferConnectionImportTotal, 2) + "\n";

        // if(reeferConnectionExportTotal!=0)
        content += "Reefer connection Export (storage not included) " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";"
                + "€ 34,83" + ";" + reeferConnectionExportQuantity + ";"
                + DoubleRounder.round(reeferConnectionExportTotal, 2) + "\n";

        // if(storageOfDamagedContainersTotal!=0)
        content += "Storage damaged Containers surcharge  " + ";" + "ARK" + ";" + "Quote %200" + ";" + "" + ";"
                + storageOfDamagedContainersQuantity + ";" + storageOfDamagedContainersTotal + "\n";

        // if(storageFullExportInervalsTotal[0]!=0)
        content += "Storage Full Export First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullExportIntervals.get(0).getPrice() + ";" + storageFullExportInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullExportInervalsTotal[0], 2) + "\n";
        // if(storageFullExportInervalsTotal[1]!=0)
        content += "Storage Full Export Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullExportIntervals.get(1).getPrice() + ";" + storageFullExportInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullExportInervalsTotal[1], 2) + "\n";
        // if(storageFullExportInervalsTotal[2]!=0)
        content += "Storage Full Export Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullExportIntervals.get(2).getPrice() + ";" + storageFullExportInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullExportInervalsTotal[2], 2) + "\n";

        // if(storageFullImportInervalsTotal[0]!=0)
        content += "Storage Full Import First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullImportIntervals.get(0).getPrice() + ";" + storageFullImportInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullImportInervalsTotal[0], 2) + "\n";
        // if(storageFullImportInervalsTotal[1]!=0)
        content += "Storage Full Import Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullImportIntervals.get(1).getPrice() + ";" + storageFullImportInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullImportInervalsTotal[1], 2) + "\n";
        // if(storageFullImportInervalsTotal[2]!=0)
        content += "Storage Full Import Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullImportIntervals.get(2).getPrice() + ";" + storageFullImportInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullImportInervalsTotal[2], 2) + "\n";

        // if(storageFullTransshipmentInervalsTotal[0]!=0)
        content += "Storage Full Transshipment First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(0).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[0]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[0], 2) + "\n";
        // if(storageFullTransshipmentInervalsTotal[1]!=0)
        content += "Storage Full Transshipment Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(1).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[1]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[1], 2) + "\n";
        // if(storageFullTransshipmentInervalsTotal[2]!=0)
        content += "Storage Full Transshipment Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(2).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[2]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[2], 2) + "\n";
        // if(storageFullTransshipmentInervalsTotal[3]!=0)
        content += "Storage Full Transshipment Fourth Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(3).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[3]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[3], 2) + "\n";
        // if(storageFullTransshipmentInervalsTotal[4]!=0)
        content += "Storage Full Transshipment Fifth Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(4).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[4]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[4], 2) + "\n";
        // if(storageFullTransshipmentInervalsTotal[5]!=0)
        content += "Storage Full Transshipment Sixth Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullTransshipmentIntervals.get(5).getPrice() + ";" + storageFullTransshipmentInervalsQuantities[5]
                + ";" + DoubleRounder.round(storageFullTransshipmentInervalsTotal[5], 2) + "\n";

        // if(storageFullReeferTransshipmentInervalsTotal[0]!=0)
        content += "Storage Full Reefer Transshipment First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(0).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[0], 2) + "\n";
        // if(storageFullReeferTransshipmentInervalsTotal[1]!=0)
        content += "Storage Full Reefer Transshipment Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(1).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[1], 2) + "\n";
        // if(storageFullReeferTransshipmentInervalsTotal[2]!=0)
        content += "Storage Full Reefer Transshipment Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(2).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[2], 2) + "\n";
        // if(storageFullReeferTransshipmentInervalsTotal[3]!=0)
        content += "Storage Full Reefer Transshipment Fourth Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(3).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[3] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[3], 2) + "\n";
        // if(storageFullReeferTransshipmentInervalsTotal[4]!=0)
        content += "Storage Full Reefer Transshipment Fifth Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(4).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[4] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[4], 2) + "\n";
        // if(storageFullReeferTransshipmentInervalsTotal[5]!=0)
        content += "Storage Full Reefer Transshipment Sixth Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day"
                + ";€ " + fullReeferTransshipmentIntervals.get(5).getPrice() + ";"
                + storageFullReeferTransshipmentInervalsQuantities[5] + ";"
                + DoubleRounder.round(storageFullReeferTransshipmentInervalsTotal[5], 2) + "\n";

        // if(storageFullReeferExportInervalsTotal[0]!=0)
        content += "Storage Full Reefer Export First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(0).getPrice() + ";" + storageFullReeferExportInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullReeferExportInervalsTotal[0], 2) + "\n";
        // if(storageFullReeferExportInervalsTotal[1]!=0)
        content += "Storage Full Reefer Export Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(1).getPrice() + ";" + storageFullReeferExportInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullReeferExportInervalsTotal[1], 2) + "\n";
        // if(storageFullReeferExportInervalsTotal[2]!=0)
        content += "Storage Full Reefer Export Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(2).getPrice() + ";" + storageFullReeferExportInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullReeferExportInervalsTotal[2], 2) + "\n";

        // if(storageFullReeferImportInervalsTotal[0]!=0)
        content += "Storage Full Reefer Import First Period Revenue  " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(0).getPrice() + ";" + storageFullReeferImportInervalsQuantities[0] + ";"
                + DoubleRounder.round(storageFullReeferImportInervalsTotal[0], 2) + "\n";
        // if(storageFullReeferImportInervalsTotal[1]!=0)
        content += "Storage Full Reefer Import Second Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(1).getPrice() + ";" + storageFullReeferImportInervalsQuantities[1] + ";"
                + DoubleRounder.round(storageFullReeferImportInervalsTotal[1], 2) + "\n";
        // if(storageFullReeferImportInervalsTotal[2]!=0)
        content += "Storage Full Reefer Import Third Period Revenue " + ";" + "ARK" + ";" + "Tarif/Unit/Day" + ";€ "
                + fullReeferDirectIntervals.get(2).getPrice() + ";" + storageFullReeferImportInervalsQuantities[2] + ";"
                + DoubleRounder.round(storageFullReeferImportInervalsTotal[2], 2) + "\n";

        // if(storageOfOOGContainersTotal!=0)
        content += "Storage surcharge Non-Standard Container " + ";" + "ARK" + ";" + "Quote %100" + ";" + "" + ";"
                + storageOfOOGContainersQuantity + ";" + DoubleRounder.round(storageOfOOGContainersTotal, 2) + "\n";

        // if(storageOfTankContainersTotal!=0)
        content += "Storage tank Containers surcharge " + ";" + "ARK" + ";" + "Quote %100" + ";" + "" + ";"
                + storageOfTankContainersQuantity + ";" + DoubleRounder.round(storageOfTankContainersTotal, 2) + "\n";

        double total = hazardousImportTotal
                + hazardousExportTotal
                + hazardousTranssipmentTotal
                + reeferConnectionTransshipmentTotal
                + reeferConnectionImportTotal
                + reeferConnectionExportTotal
                + storageOfDamagedContainersTotal
                + storageFullExportInervalsTotal[0]
                + storageFullExportInervalsTotal[1]
                + storageFullExportInervalsTotal[2]
                + storageFullImportInervalsTotal[0]
                + storageFullImportInervalsTotal[1]
                + storageFullImportInervalsTotal[2]
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
                + storageFullReeferExportInervalsTotal[3]
                + storageFullReeferImportInervalsTotal[0]
                + storageFullReeferImportInervalsTotal[1]
                + storageFullReeferImportInervalsTotal[2]
                + storageFullReeferImportInervalsTotal[3]
                + storageOfOOGContainersTotal
                + storageOfTankContainersTotal
                + emptyDepotContainersTotal
                + emptyExportContainersTotal
                + emptyImportContainersTotal
                + emptyTransshipmentContainersTotal[0]
                + emptyTransshipmentContainersTotal[1]
                + emptyTransshipmentContainersTotal[2]
                + emptyTransshipmentContainersTotal[3];
        total = DoubleRounder.round(total, 2);
        content += "Total " + ";" + " " + ";" + " " + ";" + " " + ";" + ";" + total + "\n";
        setThisShipingLineAllDetailsToFile(content, "ARK");

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
        String strStartDate = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month) - 1).get("minValue") + "/"
                + month + "/" + year;
        String strEndDate = getMinAndMaxDaysOfMonth(year).get(Integer.parseInt(month) - 1).get("maxValue") + "/" + month
                + "/" + year;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = simpleDateFormat.parse(strStartDate);
        Date endDate = simpleDateFormat.parse(strEndDate);
        for (Map<String, Date> map : getMinAndMAxValueDateOfEachInterval(container, intervals)) {
            for (LocalDate date = convertToLocalDate(startDate); !date.isAfter(convertToLocalDate(endDate)); date = date
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
        map.put("maxDate", convertToDate(convertToLocalDate(container.getIncDate()).plusDays(numberOfDays.get(0) - 1)));
        list.add(map);
        for (int i = 1; i < numberOfDays.size(); i++) {
            Map<String, Date> stringDateMap = new HashMap<>();
            stringDateMap.put("minDate", convertToDate(convertToLocalDate(list.get(i - 1).get("maxDate")).plusDays(1)));
            stringDateMap.put("maxDate",
                    convertToDate(convertToLocalDate(stringDateMap.get("minDate")).plusDays(numberOfDays.get(i) - 1)));
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
}
