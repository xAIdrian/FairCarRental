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

    //set outside of retrofit mapping
    private String companyName;
    private AmadeusLocation amadeusLocation;
    private Address address;
    private String airport;
    private float userDistanceToThisCar;

    public Car() {
    }

    public Car(VehicleInfo vehicleInfo, List<Rate> rates, EstimatedTotal estimatedTotal, Image image, String companyName, AmadeusLocation amadeusLocation, Address address, String airport, float userDistanceToThisCar) {
        this.vehicleInfo = vehicleInfo;
        this.rates = rates;
        this.estimatedTotal = estimatedTotal;
        this.image = image;
        this.companyName = companyName;
        this.amadeusLocation = amadeusLocation;
        this.address = address;
        this.airport = airport;
        this.userDistanceToThisCar = userDistanceToThisCar;
    }

    protected Car(Parcel in) {
        vehicleInfo = in.readParcelable(VehicleInfo.class.getClassLoader());
        rates = in.createTypedArrayList(Rate.CREATOR);
        estimatedTotal = in.readParcelable(EstimatedTotal.class.getClassLoader());
        image = in.readParcelable(Image.class.getClassLoader());
        companyName = in.readString();
        amadeusLocation = in.readParcelable(AmadeusLocation.class.getClassLoader());
        address = in.readParcelable(Address.class.getClassLoader());
        airport = in.readString();
        userDistanceToThisCar = in.readFloat();
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

    //added outside of Retrofit mapping


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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


    public float getUserDistanceToThisCar() {
        return userDistanceToThisCar;
    }

    public void setUserDistanceToThisCar(float userDistanceToThisCar) {
        this.userDistanceToThisCar = userDistanceToThisCar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(vehicleInfo, flags);
        dest.writeTypedList(rates);
        dest.writeParcelable(estimatedTotal, flags);
        dest.writeParcelable(image, flags);
        dest.writeString(companyName);
        dest.writeParcelable(amadeusLocation, flags);
        dest.writeParcelable(address, flags);
        dest.writeString(airport);
        dest.writeFloat(userDistanceToThisCar);
    }
}
