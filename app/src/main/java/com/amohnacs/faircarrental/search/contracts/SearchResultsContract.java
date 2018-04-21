package com.amohnacs.faircarrental.search.contracts;

import com.amohnacs.model.amadeus.AmadeusResult;

import java.util.List;


/**
 * Created by adrianmohnacs on 4/21/18.
 */

public interface SearchResultsContract {

    interface Provider {

        void carSearch(Callback callback, String address, String pickupSelection, String dropoffSelection);

        interface Callback {

            void onCarSearchResults(List<AmadeusResult> carResults);
            void onCarSearchResultError(String errorMessage);
        }
    }

    interface Presenter {
        void getCars(String addressQueryString, String pickupSelection, String dropoffSelection);
    }

    interface View {

    }
}
