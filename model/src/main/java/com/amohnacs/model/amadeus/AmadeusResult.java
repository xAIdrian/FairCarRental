package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class AmadeusResult implements Parcelable {

    @SerializedName("provider")
    private Provider provider;
    @SerializedName("location")
    private AmadeusLocation amadeusLocation;
    @SerializedName("address")
    private Address address;
    @SerializedName("airport")
    private String airport;
    @SerializedName("cars")
    private List<Car> cars;

    private float cumulativePrice;

    public AmadeusResult() {
    }

    public AmadeusResult(Provider provider, AmadeusLocation amadeusLocation, Address address, String airport, List<Car> cars) {
        this.provider = provider;
        this.amadeusLocation = amadeusLocation;
        this.address = address;
        this.airport = airport;
        this.cars = cars;
    }

    protected AmadeusResult(Parcel in) {
        amadeusLocation = in.readParcelable(AmadeusLocation.class.getClassLoader());
        address = in.readParcelable(Address.class.getClassLoader());
        airport = in.readString();
        cars = in.createTypedArrayList(Car.CREATOR);
        cumulativePrice = in.readFloat();
    }

    public static final Creator<AmadeusResult> CREATOR = new Creator<AmadeusResult>() {
        @Override
        public AmadeusResult createFromParcel(Parcel in) {
            return new AmadeusResult(in);
        }

        @Override
        public AmadeusResult[] newArray(int size) {
            return new AmadeusResult[size];
        }
    };

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public AmadeusLocation getAmadeusLocation() {
        return amadeusLocation;
    }

    public void setAmadeusLocation(AmadeusLocation amadeusLocation) {
        this.amadeusLocation = amadeusLocation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(amadeusLocation, flags);
        dest.writeParcelable(address, flags);
        dest.writeString(airport);
        dest.writeTypedList(cars);
        dest.writeFloat(cumulativePrice);
    }

    public void setCumulativePrice(float cumulativePrice) {
        this.cumulativePrice = cumulativePrice;
    }
}
