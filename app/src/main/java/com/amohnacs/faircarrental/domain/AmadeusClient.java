package com.amohnacs.faircarrental.domain;


import com.amohnacs.model.amadeus.AmadeusResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by adrianmohnacs on 4/17/18.
 */

public interface AmadeusClient {
//https://api.sandbox.amadeus.com/v1.2/cars/search-circle
    @GET("v1.2/cars/search-circle")
    Call<AmadeusResults> getSearchResult(
            @Query("apikey") String apiKey,
            @Query("latitude") float latitude,
            @Query("longitude") float longitude,
            @Query("radius") int radius,
            @Query("pick_up") String pick_up, //format : 2018-06-07
            @Query("drop_off") String drop_off //format : 2018-06-08
    );
}
