package com.amohnacs.model;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Result {

    private Provider provider;
    private Location location;
    private Address address;
    private String airport;

    public Result() {
    }

    public Result(Provider provider, Location location, Address address, String airport) {
        this.provider = provider;
        this.location = location;
        this.address = address;
        this.airport = airport;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }
}
