package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class EstimatedTotal implements Parcelable {

    @SerializedName("amount")
    private float amount;
    @SerializedName("currency")
    private String currency;

    public EstimatedTotal() {
    }

    public EstimatedTotal(float amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    protected EstimatedTotal(Parcel in) {
        amount = in.readFloat();
        currency = in.readString();
    }

    public static final Creator<EstimatedTotal> CREATOR = new Creator<EstimatedTotal>() {
        @Override
        public EstimatedTotal createFromParcel(Parcel in) {
            return new EstimatedTotal(in);
        }

        @Override
        public EstimatedTotal[] newArray(int size) {
            return new EstimatedTotal[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(amount);
        dest.writeString(currency);
    }
}
