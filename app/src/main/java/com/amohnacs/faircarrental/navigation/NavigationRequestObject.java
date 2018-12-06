package com.amohnacs.faircarrental.navigation;

import com.google.maps.model.TravelMode;

public class NavigationRequestObject {

    private TravelMode mode;
    private String originLatLng;
    private String destinationLatLng;

    NavigationRequestObject(TravelMode mode, String originLatLng, String destinationLatLng) {
        this.mode = mode;
        this.originLatLng = originLatLng;
        this.destinationLatLng = destinationLatLng;
    }

    public TravelMode getMode() {
        return mode;
    }

    public void setMode(TravelMode mode) {
        this.mode = mode;
    }

    public String getOriginLatLng() {
        return originLatLng;
    }

    public void setOriginLatLng(String originLatLng) {
        this.originLatLng = originLatLng;
    }

    public String getDestinationLatLng() {
        return destinationLatLng;
    }

    public void setDestinationLatLng(String destinationLatLng) {
        this.destinationLatLng = destinationLatLng;
    }
}
