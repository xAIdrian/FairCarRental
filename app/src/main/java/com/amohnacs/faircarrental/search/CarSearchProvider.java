package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.util.Log;

import com.amohnacs.faircarrental.BuildConfig;
import com.amohnacs.faircarrental.R;
import com.amohnacs.faircarrental.domain.AmadeusClient;
import com.amohnacs.faircarrental.domain.GoogleMapsClient;
import com.amohnacs.faircarrental.domain.RetrofitClientGenerator;
import com.amohnacs.faircarrental.search.contracts.SearchResultsContract;
import com.amohnacs.model.amadeus.AmadeusResults;
import com.amohnacs.model.googlemaps.GeoCodingResults;
import com.amohnacs.model.googlemaps.LatLngLocation;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class CarSearchProvider implements SearchResultsContract.Provider {
    private static final String TAG = CarSearchProvider.class.getSimpleName();

    private static volatile CarSearchProvider instance;

    private Context context;
    private GoogleMapsClient mapsClient;
    private AmadeusClient amadeusClient;
    private WeakReference<Callback> weakCallback;

    private CarSearchProvider(Context context) {
        this.context = context;
        mapsClient = RetrofitClientGenerator.generateGoogleMapsClient(GoogleMapsClient.class);
        amadeusClient = RetrofitClientGenerator.generateAmadeusClient(AmadeusClient.class);
    }

    public static CarSearchProvider getInstance(Context context) {
        if (instance == null) {
            synchronized (CarSearchProvider.class) {
                if (instance == null) {
                    instance = new CarSearchProvider(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void carSearch(Callback callback, String address, String pickupSelection, String dropoffSelection) {
        Call<GeoCodingResults> calls = mapsClient.getMapGeocodingResult(address,
                context.getResources().getString(R.string.google_maps_key));

        calls.enqueue(new retrofit2.Callback<GeoCodingResults>() {
            @Override
            public void onResponse(Call<GeoCodingResults> call, Response<GeoCodingResults> response) {
                LatLngLocation location = response.body().getGeoCodingResults().get(0)
                        .getGeometry().getLocation();

                getCarSearchResults(callback, location, pickupSelection, dropoffSelection);
            }

            @Override
            public void onFailure(Call<GeoCodingResults> call, Throwable t) {
                Log.e(TAG, "fail");
            }
        });
    }

    private void getCarSearchResults(Callback callback, LatLngLocation location, String pickupSelection, String dropoffSelection) {
        weakCallback = new WeakReference<>(callback);

        String key = BuildConfig.ApiKey;
        Call<AmadeusResults> call = amadeusClient.getSearchResult(BuildConfig.ApiKey,
                location.getLat(), location.getLongg(), 42, pickupSelection, dropoffSelection);

        call.enqueue(new retrofit2.Callback<AmadeusResults>() {
            @Override
            public void onResponse(Call<AmadeusResults> call, Response<AmadeusResults> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (weakCallback != null) {
                        weakCallback.get().onCarSearchResults(location, response.body().getAmadeusResults());
                    }
                } else {
                    if (weakCallback != null) {
                        weakCallback.get().onCarSearchResultError("Response not successful");
                    }
                }
            }

            @Override
            public void onFailure(Call<AmadeusResults> call, Throwable t) {
                if (weakCallback != null) {
                    weakCallback.get().onCarSearchResultError(t.getMessage());
                }
            }
        });
    }
}
