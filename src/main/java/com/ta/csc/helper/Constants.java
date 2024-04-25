package com.ta.csc.helper;


import java.util.*;

public class Constants {

    public static List<Map<String, String>> getMinAndMaxDaysOfMonth(String year) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("minValue","1");
        map1.put("maxValue","31");
        list.add(map1);
        Map<String, String> map2 = new HashMap<>();
        String februaryMaxValue = Integer.parseInt(year) % 4 == 0 ? "29" : "28";
        map2.put("minValue","1");
        map2.put("maxValue",februaryMaxValue);
        list.add(map2);
        Map<String, String> map3 = new HashMap<>();
        map3.put("minValue","1");
        map3.put("maxValue","31");
        list.add(map3);
        Map<String, String> map4 = new HashMap<>();
        map4.put("minValue","1");
        map4.put("maxValue","30");
        list.add(map4);
        Map<String, String> map5 = new HashMap<>();
        map5.put("minValue","1");
        map5.put("maxValue","31");
        list.add(map5);
        Map<String, String> map6 = new HashMap<>();
        map6.put("minValue","1");
        map6.put("maxValue","30");
        list.add(map6);
        Map<String, String> map7 = new HashMap<>();
        map7.put("minValue","1");
        map7.put("maxValue","31");
        list.add(map7);
        Map<String, String> map8 = new HashMap<>();
        map8.put("minValue","1");
        map8.put("maxValue","31");
        list.add(map8);
        Map<String, String> map9 = new HashMap<>();
        map9.put("minValue","1");
        map9.put("maxValue","30");
        list.add(map9);
        Map<String, String> map10 = new HashMap<>();
        map10.put("minValue","1");
        map10.put("maxValue","31");
        list.add(map10);
        Map<String, String> map11= new HashMap<>();
        map11.put("minValue","1");
        map11.put("maxValue","30");
        list.add(map11);
        Map<String, String> map12 = new HashMap<>();
        map12.put("minValue","1");
        map12.put("maxValue","31");
        list.add(map12);
        return list;
    }

    public static List<String> titles() {
        List<String> titles = new ArrayList<>();
        titles.add("Shipping Line");
        titles.add("Container");
        titles.add("Length");
        titles.add("Type");
        titles.add("F/E");
        titles.add("Invoice Category");
        titles.add("Reef");
        titles.add("IMDG");
        titles.add("OOG");
        titles.add("DMG");
        titles.add("IncDate");
        titles.add("OutDate");
        titles.add("Invoice Storage Duration");
//        titles.add("Invoicing Date");
        titles.add("IMDG Surcharge");
        titles.add("OOG Surcharge");
        titles.add("Reefer Surcharge");
        titles.add("DMG Surcharge");
        titles.add("Tank Surcharge");
        titles.add("Total Surcharge");
        return titles;
    }
    public static List<String> hlcFullTransshipment(){
        List<String> titles = new ArrayList<>(titles());
        titles.add("First Period Revenue");
        titles.add("Second Period Revenue");
        titles.add("Third Period Revenue");
        titles.add("Fourth Period Revenue");
        titles.add("Total Revenue");
        return titles;
    }
    public static List<String> hlcFullDirect(){
        List<String> titles = new ArrayList<>(titles());
        titles.add("First Period Revenue");
        titles.add("Second Period Revenue");
        titles.add("Third Period Revenue");
        titles.add("Total Revenue");
        return titles;
    }

    public static List<String> nonHLCFullTransshipment(){
        List<String> titles = new ArrayList<>(titles());
        titles.add("First Period Revenue");
        titles.add("Second Period Revenue");
        titles.add("Third Period Revenue");
        titles.add("Fourth Period Revenue");
        titles.add("Fifth Period Revenue");
        titles.add("Sixth Period Revenue");
        titles.add("Total Revenue");
        return titles;
    }

    public static  List<String> specialStandardShippingLine() {
        String [] specialShippingLines = {"MSK","HSD","SGL","SAF","HMM","MAG","CMA"};
        return Arrays.asList(specialShippingLines);
    }

    public static  List<String> shippinginesHavingFreePool() {
        String [] specialShippingLines = {"ARK","ONE","HLC","YML"};
        return Arrays.asList(specialShippingLines);
    }

    public static int numberOfFreeTeus(String shippingLine) {
        switch (shippingLine) {
            case "HLC": return  3545;
            case "ARK": return 1450;
            case "YML" : return 300;
            case "ONE": return  500;
            default:  return 0;
        }
    }
    public static double priceOfEmptyTeus(String shippingLine) {
        switch (shippingLine) {
            case "HLC": return  2.03;
            case "ARK": return 1.75;
            case "YML" :
            case "ONE":
                return 2;
            default:  return 0;
        }
    }


}
