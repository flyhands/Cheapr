package kict.edu.my.cheapr.models;

/**
 * Created by anas on 5/13/18.
 */

public class Price {
    private double price_value;
    private double currency_value;
    private int day_start;
    private int day_end;
    private int month_start;
    private int month_end;
    private int year_start;
    private int year_end;
    private String supermarket;

    public Price(double price_value, double currency_value, int day_start, int day_end, int month_start, int month_end, int year_start, int year_end, String supermarket) {
        this.price_value = price_value;
        this.currency_value = currency_value;
        this.day_start = day_start;
        this.day_end = day_end;
        this.month_start = month_start;
        this.month_end = month_end;
        this.year_start = year_start;
        this.year_end = year_end;
        this.supermarket = supermarket;
    }

    public double getPrice_value() {
        return price_value;
    }

    public void setPrice_value(double price_value) {
        this.price_value = price_value;
    }

    public double getCurrency_value() {
        return currency_value;
    }

    public void setCurrency_value(double currency_value) {
        this.currency_value = currency_value;
    }

    public int getDay_start() {
        return day_start;
    }

    public void setDay_start(int day_start) {
        this.day_start = day_start;
    }

    public int getDay_end() {
        return day_end;
    }

    public void setDay_end(int day_end) {
        this.day_end = day_end;
    }

    public int getMonth_start() {
        return month_start;
    }

    public void setMonth_start(int month_start) {
        this.month_start = month_start;
    }

    public int getMonth_end() {
        return month_end;
    }

    public void setMonth_end(int month_end) {
        this.month_end = month_end;
    }

    public int getYear_start() {
        return year_start;
    }

    public void setYear_start(int year_start) {
        this.year_start = year_start;
    }

    public int getYear_end() {
        return year_end;
    }

    public void setYear_end(int year_end) {
        this.year_end = year_end;
    }

    public String getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(String supermarket) {
        this.supermarket = supermarket;
    }

    @Override
    public String toString() {
        return String.format("%f | supermarket %s", price_value, supermarket);
    }
}
