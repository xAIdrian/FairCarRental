package com.amohnacs.model.amadeus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

class Car {

    @SerializedName("vehicle_info")
    private VehicleInfo vehicleInfo;
    @SerializedName("rates")
    private List<Rate> rates;
    @SerializedName("estimated_total")
    private EstimatedTotal estimatedTotal;
    @SerializedName("image")
    private Image image;

    public Car() {
    }

    public Car(VehicleInfo vehicleInfo, List<Rate> rates, EstimatedTotal estimatedTotal, Image image) {
        this.vehicleInfo = vehicleInfo;
        this.rates = rates;
        this.estimatedTotal = estimatedTotal;
        this.image = image;
    }

    public VehicleInfo getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleInfo vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public EstimatedTotal getEstimatedTotal() {
        return estimatedTotal;
    }

    public void setEstimatedTotal(EstimatedTotal estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
