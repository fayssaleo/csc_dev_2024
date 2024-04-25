package com.ta.csc.intervals;

import com.ta.csc.domain.Interval;

import java.util.List;

public class ContainerIntervals {
    public static List<Interval> fullTransshipmentIntervals(String shippingLine) {
        switch (shippingLine) {
            case "HLC" : return HLCContainerIntervals.fullTransshipmentIntervals();
            case "ARK" : return ARKContainerIntervals.fullTransshipmentIntervals();
            case "YML" : return YMLContainerIntervals.fullTransshipmentIntervals();
            case "ONE" : return ONEContainerIntervals.fullTransshipmentIntervals();
            default : return StandardContainerIntervals.transshipmentIntervals();
        }
    }

    public static List<Interval> fullReeferTransshipmentIntervals(String shippingLine) {
        switch (shippingLine) {
            case "HLC" : return HLCContainerIntervals.fullReeferTransshipmentIntervals();
            case "ARK" : return ARKContainerIntervals.fullTransshipmentIntervals();
            case "YML" : return YMLContainerIntervals.fullTransshipmentIntervals();
            case "ONE" : return ONEContainerIntervals.fullTransshipmentIntervals();
            default : return StandardContainerIntervals.transshipmentIntervals();
        }
    }

    public static List<Interval> fullImportIntervals(String shippingLine) {
        switch (shippingLine) {
            case "HLC" : return HLCContainerIntervals.fullImportIntervals();
            case "ARK" : return ARKContainerIntervals.fullImportIntervals();
            case "YML" : return YMLContainerIntervals.fullImportIntervals();
            case "ONE" : return ONEContainerIntervals.fullImportIntervals();
            default : return StandardContainerIntervals.fullImportIntervals();
        }
    }
    public static List<Interval> fullExportIntervals(String shippingLine) {
        switch (shippingLine) {
            case "HLC" : return HLCContainerIntervals.fullExportIntervals();
            case "ARK" : return ARKContainerIntervals.fullExportIntervals();
            case "YML" : return YMLContainerIntervals.fullExportIntervals();
            case "ONE" : return ONEContainerIntervals.fullIExportIntervals();
            default : return StandardContainerIntervals.fullExportIntervals();
        }
    }



    public static List<Interval> fullReeferDirectIntervals(String shippingLine) {
        switch (shippingLine) {
            case "HLC" : return HLCContainerIntervals.fullReeferDirectIntervals();
            case "ARK" : return ARKContainerIntervals.fullReeferDirectIntervals();
            case "YML" : return YMLContainerIntervals.fullReeferDirectIntervals();
            case "ONE" : return ONEContainerIntervals.fullReeferDirectIntervals();
            default : return StandardContainerIntervals.fullReeferDirectIntervals();
        }
    }
}
