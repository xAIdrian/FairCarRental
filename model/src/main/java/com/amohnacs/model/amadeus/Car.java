package com.amohnacs.model.amadeus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Car implements Parcelable {

    @SerializedName("vehicle_info")
    private VehicleInfo vehicleInfo;
    @SerializedName("rates")
    private List<Rate> rates;
    @SerializedName("estimated_total")
    private EstimatedTotal estimatedTotal;
    @SerializedName("image")
    private Image image;

    public Car() {
    }

    public Car(VehicleInfo vehicleInfo, List<Rate> rates, EstimatedTotal estimatedTotal, Image image) {
        this.vehicleInfo = vehicleInfo;
        this.rates = rates;
        this.estimatedTotal = estimatedTotal;
        this.image = image;
    }

    protected Car(Parcel in) {
        estimatedTotal = in.readParcelable(EstimatedTotal.class.getClassLoader());
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public VehicleInfo getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleInfo vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public EstimatedTotal getEstimatedTotal() {
        return estimatedTotal;
    }

    public void setEstimatedTotal(EstimatedTotal estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(estimatedTotal, flags);
    }
}
