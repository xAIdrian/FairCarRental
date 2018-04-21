package com.amohnacs.faircarrental.search.contracts;

import com.amohnacs.model.Result;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public interface SearchResultsContract {

    interface Provider {

        void carSearch(Callback callback, float latitude, float longitude, String pickupSelection, String dropoffSelection);

        interface Callback {

            void onCarSearchResults(List<Result> carResults);
            void onCarSearchResultError(String errorMessage);
        }
    }

    interface Presenter {
        void getCars(String addressQueryString, String pickupSelection, String dropoffSelection);
    }

    interface View {

    }
}
