package com.amohnacs.faircarrental;

import com.amohnacs.model.amadeus.AmadeusLocation;
import com.amohnacs.model.amadeus.Car;
import com.amohnacs.model.googlemaps.LatLngLocation;

import java.text.DecimalFormat;

/**
 * Created by adrianmohnacs on 4/22/18.
 */

public class MyUtils {
    private static final String DISTANCE_PREPEND = "Distance : ";


    /**
     * Haversine Formula
     * I can't really take credit for this :/
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static float distance(float lat_a, float lng_a, float lat_b, float lng_b)
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        float result = new Float(distance * meterConversion).floatValue();
        return result / 1000;
    }

    public static float getDistanceForSorting(Car car, LatLngLocation userLocation) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);

        return distance(userLocation.getLatitude(), userLocation.getLongitude(),
                car.getAmadeusLocation().getLatitude(), car.getAmadeusLocation().getLongitude());
    }

    public static String getDistanceString(AmadeusLocation amadeusLocation, LatLngLocation userLocation) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);

        float distance = MyUtils.distance(userLocation.getLatitude(), userLocation.getLongitude(),
                amadeusLocation.getLatitude(), amadeusLocation.getLongitude());
        String distanceString = decimalFormat.format(distance);
        return DISTANCE_PREPEND + distanceString + " km";
    }
}
