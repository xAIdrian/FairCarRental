package com.amohnacs.faircarrental.search;

import android.content.Context;

import com.amohnacs.faircarrental.search.contracts.SearchContract;
import com.amohnacs.faircarrental.search.contracts.SearchResultsContract;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class CarSearchProvider implements SearchResultsContract.Provider {
    private static final String TAG = CarSearchProvider.class.getSimpleName();

    private static volatile CarSearchProvider instance;

    private Context context;

    private CarSearchProvider(Context context) {
        this.context = context;
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
    public void carSearch(Callback callback, float latitude, float longitude, String pickupSelection, String dropoffSelection) {

    }
}
