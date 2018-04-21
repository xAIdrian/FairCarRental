package com.amohnacs.model;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

class Car {

    private VehicleInfo vehicleInfo;
    private Rates rates;
    private EstimatedTotal estimatedTotal;

    public Car() {
    }

    public Car(VehicleInfo vehicleInfo, Rates rates, EstimatedTotal estimatedTotal) {
        this.vehicleInfo = vehicleInfo;
        this.rates = rates;
        this.estimatedTotal = estimatedTotal;
    }

    public VehicleInfo getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleInfo vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public EstimatedTotal getEstimatedTotal() {
        return estimatedTotal;
    }

    public void setEstimatedTotal(EstimatedTotal estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }
}
