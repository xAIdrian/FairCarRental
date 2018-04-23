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
    private float lat;
    @SerializedName("lng")
    private float longg;

    public LatLngLocation() {
    }

    public LatLngLocation(float lat, float longg) {
        this.lat = lat;
        this.longg = longg;
    }

    protected LatLngLocation(Parcel in) {
        lat = in.readFloat();
        longg = in.readFloat();
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

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLongg() {
        return longg;
    }

    public void setLongg(float longg) {
        this.longg = longg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(lat);
        dest.writeFloat(longg);
    }
}
