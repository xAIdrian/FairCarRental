package com.amohnacs.model;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Address {

    private String line1;
    private String city;
    private String region;
    private String country;
    private int postalCode;

    public Address() {
    }

    public Address(String line1, String city, String region, String country, int postalCode) {
        this.line1 = line1;
        this.city = city;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
