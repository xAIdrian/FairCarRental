package com.amohnacs.faircarrental.domain;

import com.amohnacs.model.googlemaps.GeoCodingResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public interface GoogleMapsClient {

    @GET("api/geocode/json")
    Call<GeoCodingResults> getMapGeocodingResult(
            @Query("address") String address,
            @Query("key") String keyApi
    );
}
