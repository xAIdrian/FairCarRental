package com.amohnacs.model.googlemaps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public class GeoCodingResult {
    @SerializedName("formatted_address")
    private String formattedAddress;
    @SerializedName("geometry")
    private Geometry geometry;
    @SerializedName("place_id")
    private String placeId;

    public GeoCodingResult() {
    }

    public GeoCodingResult(String formattedAddress, Geometry geometry, String placeId) {
        this.formattedAddress = formattedAddress;
        this.geometry = geometry;
        this.placeId = placeId;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
