package kict.edu.my.cheapr.models;

/**
 * Created by anas on 5/13/18.
 */

public class Price {
    private double value;
    private String supermarket;

    public Price(double value, String supermarket) {
        this.value = value;
        this.supermarket = supermarket;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(String supermarket) {
        this.supermarket = supermarket;
    }

    @Override
    public String toString() {
        return String.format("%f | supermarket %s", value, supermarket);
    }
}
