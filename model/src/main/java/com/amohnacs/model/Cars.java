package com.amohnacs.model;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Cars {

    private List<Car> cars;

    public Cars() {
    }

    public Cars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
