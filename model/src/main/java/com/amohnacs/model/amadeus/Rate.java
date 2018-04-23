package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Rate implements Parcelable {

    @SerializedName("type")
    private String type;
    @SerializedName("price")
    private Price price;

    public Rate() {
    }

    public Rate(String type, Price price) {
        this.type = type;
        this.price = price;
    }

    protected Rate(Parcel in) {
        type = in.readString();
        price = in.readParcelable(Price.class.getClassLoader());
    }

    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel in) {
            return new Rate(in);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeParcelable(price, flags);
    }
}
