package com.amohnacs.model.googlemaps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public class LatLngLocation {

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
}
