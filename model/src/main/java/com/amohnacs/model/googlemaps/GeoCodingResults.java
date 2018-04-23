package com.amohnacs.model.googlemaps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public class GeoCodingResults {

    @SerializedName("results")
    private List<GeoCodingResult> geoCodingResults;

    public GeoCodingResults() {
    }

    public GeoCodingResults(List<GeoCodingResult> geoCodingResults) {
        this.geoCodingResults = geoCodingResults;
    }

    public List<GeoCodingResult> getGeoCodingResults() {
        return geoCodingResults;
    }

    public void setGeoCodingResults(List<GeoCodingResult> geoCodingResults) {
        this.geoCodingResults = geoCodingResults;
    }
}
