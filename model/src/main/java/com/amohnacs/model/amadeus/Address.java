package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Address implements Parcelable {

    @SerializedName("line1")
    private String line1;
    @SerializedName("line2")
    private String line2;
    @SerializedName("city")
    private String city;
    @SerializedName("region")
    private String region;
    @SerializedName("country")
    private String country;
    @SerializedName("postal_code")
    private int postalCode;

    public Address() {
    }

    public Address(String line1, String line2, String city, String region, String country, int postalCode) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;
    }

    protected Address(Parcel in) {
        line1 = in.readString();
        line2 = in.readString();
        city = in.readString();
        region = in.readString();
        country = in.readString();
        postalCode = in.readInt();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(line1);
        dest.writeString(line2);
        dest.writeString(city);
        dest.writeString(region);
        dest.writeString(country);
        dest.writeInt(postalCode);
    }
}
