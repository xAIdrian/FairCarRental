package com.amohnacs.model;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

class Price {

    private float amount;
    private String currency;

    public Price() {
    }

    public Price(float amount, String currency) {
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
