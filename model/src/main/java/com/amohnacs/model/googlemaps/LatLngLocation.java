package com.amohnacs.model.googlemaps;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public class LatLngLocation implements Parcelable
{

    @SerializedName("lat")
    private float latitude;
    @SerializedName("lng")
    private float longitude;

    public LatLngLocation() {
    }

    public LatLngLocation(float lat, float longitude) {
        this.latitude = lat;
        this.longitude = longitude;
    }

    protected LatLngLocation(Parcel in) {
        latitude = in.readFloat();
        longitude = in.readFloat();
    }

    public static final Creator<LatLngLocation> CREATOR = new Creator<LatLngLocation>() {
        @Override
        public LatLngLocation createFromParcel(Parcel in) {
            return new LatLngLocation(in);
        }

        @Override
        public LatLngLocation[] newArray(int size) {
            return new LatLngLocation[size];
        }
    };

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float lat) {
        this.latitude = lat;
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
