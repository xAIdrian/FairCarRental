package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class AmadeusLocation implements Parcelable{

    @SerializedName("latitude")
    private float latitude;
    @SerializedName("longitude")
    private float longitude;

    public AmadeusLocation() {
    }

    public AmadeusLocation(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected AmadeusLocation(Parcel in) {
        latitude = in.readFloat();
        longitude = in.readFloat();
    }

    public static final Creator<AmadeusLocation> CREATOR = new Creator<AmadeusLocation>() {
        @Override
        public AmadeusLocation createFromParcel(Parcel in) {
            return new AmadeusLocation(in);
        }

        @Override
        public AmadeusLocation[] newArray(int size) {
            return new AmadeusLocation[size];
        }
    };

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }
}
