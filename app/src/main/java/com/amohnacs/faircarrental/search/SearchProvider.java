package com.amohnacs.faircarrental.search;

import android.content.Context;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class SearchProvider implements Contract.Provider {
    private static final String TAG = SearchProvider.class.getSimpleName();

    private static volatile SearchProvider instance;

    private Context context;

    private SearchProvider(Context context) {
        this.context = context;
    }

    public static SearchProvider getInstance(Context context) {
        if (instance == null) {
            synchronized (SearchProvider.class) {
                if (instance == null) {
                    instance = new SearchProvider(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void carSearch(Callback callback, String addressQueryString, String pickupSelection, String dropoffSelection) {

    }
}
