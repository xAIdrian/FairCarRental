package com.amohnacs.faircarrental.domain;


import com.amohnacs.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by adrianmohnacs on 4/17/18.
 */

public interface AmadeusClient {

    @GET("v1.2/cars/search-circle")
    Call<Result> getSearchResult(
            @Query("apiKey") String apiKey,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("radius") int radius,
            @Query("pick_up") String pick_up, //format : 2018-06-07
            @Query("drop_off") String drop_off //format : 2018-06-08
    );
}
