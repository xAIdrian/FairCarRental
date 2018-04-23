package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class AmadeusResults implements Parcelable {

    @SerializedName("results")
    private List<AmadeusResult> amadeusResults;

    public AmadeusResults() {
    }

    public AmadeusResults(List<AmadeusResult> amadeusResults) {
        this.amadeusResults = amadeusResults;
    }

    protected AmadeusResults(Parcel in) {
        amadeusResults = in.createTypedArrayList(AmadeusResult.CREATOR);
    }

    public static final Creator<AmadeusResults> CREATOR = new Creator<AmadeusResults>() {
        @Override
        public AmadeusResults createFromParcel(Parcel in) {
            return new AmadeusResults(in);
        }

        @Override
        public AmadeusResults[] newArray(int size) {
            return new AmadeusResults[size];
        }
    };

    public List<AmadeusResult> getAmadeusResults() {
        return amadeusResults;
    }

    public void setAmadeusResults(List<AmadeusResult> amadeusResults) {
        this.amadeusResults = amadeusResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(amadeusResults);
    }
}
