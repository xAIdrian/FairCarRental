package com.amohnacs.model.googlemaps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public class Geometry {
    @SerializedName("location")
    private LatLngLocation location;

    public Geometry() {
    }

    public Geometry(LatLngLocation location) {
        this.location = location;
    }

    public LatLngLocation getLocation() {
        return location;
    }

    public void setLocation(LatLngLocation location) {
        this.location = location;
    }
}
