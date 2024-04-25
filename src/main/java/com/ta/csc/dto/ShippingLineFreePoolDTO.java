package com.ta.csc.dto;

public class ShippingLineFreePoolDTO {
    public int day;
    public int numberofTeus;
    public double price;

    public ShippingLineFreePoolDTO(int day,int numberofTeus, double price) {
        this.day = day;
        this.numberofTeus = numberofTeus;
        this.price = price;
    }
}
