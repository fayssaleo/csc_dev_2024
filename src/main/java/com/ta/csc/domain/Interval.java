package com.ta.csc.domain;

public class Interval {

    int minValue;
    int maxValue;
    double price;

    @Override
    public String toString() {
        return "Interval{" +
                "minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", price=" + price +
                '}';
    }

    public Interval(int minValue, int maxValue, double price) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.price = price;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
