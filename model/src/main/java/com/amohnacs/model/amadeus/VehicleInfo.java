package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

class VehicleInfo implements Parcelable {

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

    protected VehicleInfo(Parcel in) {
        acrissCode = in.readString();
        transmission = in.readString();
        fuel = in.readString();
        airConditioning = in.readByte() != 0;
        category = in.readString();
        type = in.readString();
    }

    public static final Creator<VehicleInfo> CREATOR = new Creator<VehicleInfo>() {
        @Override
        public VehicleInfo createFromParcel(Parcel in) {
            return new VehicleInfo(in);
        }

        @Override
        public VehicleInfo[] newArray(int size) {
            return new VehicleInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(acrissCode);
        dest.writeString(transmission);
        dest.writeString(fuel);
        dest.writeByte((byte) (airConditioning ? 1 : 0));
        dest.writeString(category);
        dest.writeString(type);
    }
}
