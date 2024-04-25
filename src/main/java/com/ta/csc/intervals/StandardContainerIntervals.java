package com.ta.csc.intervals;

import com.ta.csc.domain.Interval;

import java.util.ArrayList;
import java.util.List;

public class StandardContainerIntervals {

    public static List<Interval> transshipmentIntervals() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 7, 0);
        Interval secondInterval = new Interval(8, 15, 3);
        Interval thirdInterval = new Interval(16, 21, 6);
        Interval fourthInterval = new Interval(22, 28, 12);
        Interval fifthInterval = new Interval(29, 45, 18);
        Interval sixthInterval = new Interval(45, 1000000, 36);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);
        intervals.add(fourthInterval);
        intervals.add(fifthInterval);
        intervals.add(sixthInterval);

        return intervals;
    }

    public static List<Interval> transshipmentIntervals2021() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 7, 0);
        Interval secondInterval = new Interval(8, 15, 3);
        Interval thirdInterval = new Interval(16, 21, 6);
        Interval fourthInterval = new Interval(22, 28, 12);
        Interval fifthInterval = new Interval(29, 45, 18);
        Interval sixthInterval = new Interval(45, 1000000, 36);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);
        intervals.add(fourthInterval);
        intervals.add(fifthInterval);
        intervals.add(sixthInterval);

        return intervals;
    }

    public static List<Interval> fullExportIntervals() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.43);
        Interval thirdInterval = new Interval(8, 1000000, 3.86);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullExportIntervals2021() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.4);
        Interval thirdInterval = new Interval(8, 1000000, 3.78);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullExportIntervals2023() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.46);
        Interval thirdInterval = new Interval(8, 1000000, 3.94);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullExportIntervals2024() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.49);
        Interval thirdInterval = new Interval(8, 1000000, 4.02);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullImportIntervals() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 5, 0);
        Interval secondInterval = new Interval(6, 7, 1.43);
        Interval thirdInterval = new Interval(8, 1000000, 3.86);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullImportIntervals2021() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 5, 0);
        Interval secondInterval = new Interval(6, 7, 1.4);
        Interval thirdInterval = new Interval(8, 1000000, 3.78);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullImportIntervals2023() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 5, 0);
        Interval secondInterval = new Interval(6, 7, 1.46);
        Interval thirdInterval = new Interval(8, 1000000, 3.94);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullImportIntervals2024() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 5, 0);
        Interval secondInterval = new Interval(6, 7, 1.49);
        Interval thirdInterval = new Interval(8, 1000000, 4.02);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyImportIntervals() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.07);
        Interval thirdInterval = new Interval(8, 1000000, 2.86);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyImportIntervals2021() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.05);
        Interval thirdInterval = new Interval(8, 1000000, 2.8);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyImportIntervals2023() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.09);
        Interval thirdInterval = new Interval(8, 1000000, 2.92);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyImportIntervals2024() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.11);
        Interval thirdInterval = new Interval(8, 1000000, 2.98);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyExportIntervals() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.07);
        Interval thirdInterval = new Interval(8, 1000000, 2.86);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyExportIntervals2021() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.05);
        Interval thirdInterval = new Interval(8, 1000000, 2.8);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyExportIntervals2023() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.09);
        Interval thirdInterval = new Interval(8, 1000000, 2.92);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyExportIntervals2024() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.11);
        Interval thirdInterval = new Interval(8, 1000000, 2.98);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyDepotIntervals() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.07);
        Interval thirdInterval = new Interval(8, 1000000, 2.86);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyDepotIntervals2021() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.05);
        Interval thirdInterval = new Interval(8, 1000000, 2.80);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyDepotIntervals2023() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.09);
        Interval thirdInterval = new Interval(8, 1000000, 2.92);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> emptyDepotIntervals2024() {

        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 1.11);
        Interval thirdInterval = new Interval(8, 1000000, 2.98);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullReeferDirectIntervals() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 2.86);
        Interval thirdInterval = new Interval(8, 1000000, 7.71);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullReeferDirectIntervals2021() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 2.8);
        Interval thirdInterval = new Interval(8, 1000000, 7.56);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullReeferDirectIntervals2023() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 2.92);
        Interval thirdInterval = new Interval(8, 1000000, 7.86);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

    public static List<Interval> fullReeferDirectIntervals2024() {
        List<Interval> intervals = new ArrayList<>();
        Interval firstInterval = new Interval(1, 2, 0);
        Interval secondInterval = new Interval(3, 7, 2.98);
        Interval thirdInterval = new Interval(8, 1000000, 8.02);

        intervals.add(firstInterval);
        intervals.add(secondInterval);
        intervals.add(thirdInterval);

        return intervals;
    }

}
