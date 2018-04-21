package com.amohnacs.model;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

class EstimatedTotal {

    private float amount;
    private String currency;

    public EstimatedTotal() {
    }

    public EstimatedTotal(float amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
