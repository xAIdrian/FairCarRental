package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Provider implements Parcelable {

    @SerializedName("company_code")
    private String companyCode;
    @SerializedName("company_name")
    private String companyName;

    public Provider() {
    }

    public Provider(String companyCode, String companyName) {
        this.companyCode = companyCode;
        this.companyName = companyName;
    }

    protected Provider(Parcel in) {
        companyCode = in.readString();
        companyName = in.readString();
    }

    public static final Creator<Provider> CREATOR = new Creator<Provider>() {
        @Override
        public Provider createFromParcel(Parcel in) {
            return new Provider(in);
        }

        @Override
        public Provider[] newArray(int size) {
            return new Provider[size];
        }
    };

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyCode);
        dest.writeString(companyName);
    }
}
