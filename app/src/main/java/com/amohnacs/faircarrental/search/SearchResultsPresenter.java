package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amohnacs.common.mvp.BasePresenter;
import com.amohnacs.faircarrental.MyUtils;
import com.amohnacs.faircarrental.search.contracts.SearchResultsContract;
import com.amohnacs.model.amadeus.AmadeusResult;
import com.amohnacs.model.amadeus.Car;
import com.amohnacs.model.googlemaps.LatLngLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public void sortCarsByCompanyDescending() {
        Log.e(TAG, "company descending");
        if (resultsForSorting != null && !resultsForSorting.isEmpty()) {
            Collections.sort(resultsForSorting, new Comparator<Car>() {
                @Override
                public int compare(Car o1, Car o2) {
                    return o2.getCompanyName().compareTo(o1.getCompanyName());
                }
            });
            if (isViewAttached()) {
                getMvpView().updateCarSearchResults(resultsForSorting);
            }
        }
    }

    @Override
    public void sortCarsByDistanceDescending() {
        Log.e(TAG, "distance descending");
        if (resultsForSorting != null && !resultsForSorting.isEmpty()) {
            Collections.sort(resultsForSorting,new Comparator<Car>() {
                @Override
                public int compare(Car car1, Car car2) {
                    int inter1 = (int) car1.getUserDistanceToThisCar();
                    int inter2 = (int) car2.getUserDistanceToThisCar();

                    return inter2 - inter1;
                }
            });
            if (isViewAttached()) {
                getMvpView().updateCarSearchResults(resultsForSorting);
            }
        }
    }

    @Override
    public void sortCarsByPriceDescending() {
        Log.e(TAG, "price descending");
        if (resultsForSorting != null && !resultsForSorting.isEmpty()) {
            Collections.sort(resultsForSorting,new Comparator<Car>() {
                @Override
                public int compare(Car car1, Car car2) {

                    return (int) (car2.getEstimatedTotal().getAmount() - car1.getEstimatedTotal().getAmount());
                }
            });
            if (isViewAttached()) {
                getMvpView().updateCarSearchResults(resultsForSorting);
            }
        }
    }

    @Override
    public void sortCarsByCompanyAscending() {
        Log.e(TAG, "company descending");
        if (resultsForSorting != null && !resultsForSorting.isEmpty()) {
            Collections.sort(resultsForSorting, new Comparator<Car>() {
                @Override
                public int compare(Car o1, Car o2) {
                    return o1.getCompanyName().compareTo(o2.getCompanyName());
                }
            });
            if (isViewAttached()) {
                getMvpView().updateCarSearchResults(resultsForSorting);
            }
        }
    }

    @Override
    public void sortCarsByDistanceAscending() {
        Log.e(TAG, "distance descending");
        if (resultsForSorting != null && !resultsForSorting.isEmpty()) {
            Collections.sort(resultsForSorting,new Comparator<Car>() {
                @Override
                public int compare(Car car1, Car car2) {
                    int inter1 = (int) car1.getUserDistanceToThisCar();
                    int inter2 = (int) car2.getUserDistanceToThisCar();

                    return inter1 - inter2;
                }
            });
            if (isViewAttached()) {
                getMvpView().updateCarSearchResults(resultsForSorting);
            }
        }
    }

    @Override
    public void sortCarsByPriceAscending() {
        Log.e(TAG, "price descending");
        if (resultsForSorting != null && !resultsForSorting.isEmpty()) {
            Collections.sort(resultsForSorting,new Comparator<Car>() {
                @Override
                public int compare(Car car1, Car car2) {

                    return (int) (car1.getEstimatedTotal().getAmount() - car2.getEstimatedTotal().getAmount());
                }
            });
            if (isViewAttached()) {
                getMvpView().updateCarSearchResults(resultsForSorting);
            }
        }
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
                car.setUserDistanceToThisCar(MyUtils.getDistanceForSorting(car, userLocation));

                carList.add(car);
            }
        }

        if (!resultsForSorting.isEmpty()) {
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
