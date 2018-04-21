package com.amohnacs.model.amadeus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class AmadeusResult {

    @SerializedName("provider")
    private Provider provider;
    @SerializedName("location")
    private Location location;
    @SerializedName("address")
    private Address address;
    @SerializedName("airport")
    private String airport;
    @SerializedName("cars")
    private List<Car> cars;

    public AmadeusResult() {
    }

    public AmadeusResult(Provider provider, Location location, Address address, String airport, List<Car> cars) {
        this.provider = provider;
        this.location = location;
        this.address = address;
        this.airport = airport;
        this.cars = cars;
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
