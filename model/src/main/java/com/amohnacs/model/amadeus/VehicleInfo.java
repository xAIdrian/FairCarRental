package com.amohnacs.model.amadeus;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

class VehicleInfo {

    @SerializedName("acriss_code")
    private String acrissCode;
    @SerializedName("transmission")
    private String transmission;
    @SerializedName("fuel")
    private String fuel;
    @SerializedName("air_conditioning")
    private boolean airConditioning;
    @SerializedName("category")
    private String category;
    @SerializedName("type")
    private String type;

    public VehicleInfo() {
    }

    public VehicleInfo(String acrissCode, String transmission, String fuel, boolean airConditioning, String category, String type) {
        this.acrissCode = acrissCode;
        this.transmission = transmission;
        this.fuel = fuel;
        this.airConditioning = airConditioning;
        this.category = category;
        this.type = type;
    }

    public String getAcrissCode() {
        return acrissCode;
    }

    public void setAcrissCode(String acrissCode) {
        this.acrissCode = acrissCode;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
