package com.amohnacs.model;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

class Rates {

    private List<Rate> rates;

    public Rates() {
    }

    public Rates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
