package com.amohnacs.model.amadeus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class AmadeusResults {

    @SerializedName("results")
    private List<AmadeusResult> amadeusResults;

    public AmadeusResults() {
    }

    public AmadeusResults(List<AmadeusResult> amadeusResults) {
        this.amadeusResults = amadeusResults;
    }

    public List<AmadeusResult> getAmadeusResults() {
        return amadeusResults;
    }

    public void setAmadeusResults(List<AmadeusResult> amadeusResults) {
        this.amadeusResults = amadeusResults;
    }
}
