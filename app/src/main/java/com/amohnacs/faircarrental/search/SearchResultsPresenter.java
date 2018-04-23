package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.support.annotation.Nullable;

import com.amohnacs.common.mvp.BasePresenter;
import com.amohnacs.faircarrental.search.contracts.SearchResultsContract;
import com.amohnacs.model.amadeus.AmadeusResult;
import com.amohnacs.model.amadeus.Car;
import com.amohnacs.model.amadeus.Provider;
import com.amohnacs.model.googlemaps.LatLngLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public class SearchResultsPresenter extends BasePresenter<SearchResultsContract.View> implements SearchResultsContract.Presenter, SearchResultsContract.Provider.Callback {
    private static final String TAG = SearchResultsPresenter.class.getSimpleName();

    private static volatile SearchResultsPresenter instance;

    private CarSearchProvider provider;
    private ArrayList<Car> resultsForSorting;
    private LatLngLocation userLocation;

    private SearchResultsPresenter(Context context) {
        provider = CarSearchProvider.getInstance(context);

        resultsForSorting = new ArrayList<>();
    }

    public static SearchResultsPresenter getInstance(Context context) {
        if (instance == null) {
            synchronized (SearchResultsPresenter.class) {
                if (instance == null) {
                    instance = new SearchResultsPresenter(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void getCars(String addressQueryString, String pickupSelection, String dropoffSelection) {
        String apiFriendlyAddress = addressQueryString.replace(" ", " +");
        provider.carSearch(this, apiFriendlyAddress, pickupSelection, dropoffSelection);
    }

    @Override
    public ArrayList<Car> getNextCachedCarBundle() {
        return null;
    }

    @Override
    public void onCarSearchResults(LatLngLocation userLocation, List<AmadeusResult> providerResults) {
        this.userLocation = userLocation;

        ArrayList<Car> carList = new ArrayList<>();

        for (AmadeusResult result : providerResults) {
            for (Car car : result.getCars()) {
                car.setCompanyName(result.getProvider().getCompanyName());
                car.setAmadeusLocation(result.getAmadeusLocation());
                car.setAddress(result.getAddress());
                car.setAirport(result.getAirport());

                carList.add(car);
            }
        }

        if (resultsForSorting.size() > 0) {
            resultsForSorting.clear();
        }
        resultsForSorting.addAll(carList);

        if (isViewAttached()) {
            getMvpView().updateCarSearchResults(resultsForSorting);
        }

    }

    @Override
    public void onCarSearchResultError(String errorMessage) {

    }

    @Nullable
    public LatLngLocation getUserLatLngLocation() {
        return userLocation;
    }
}
