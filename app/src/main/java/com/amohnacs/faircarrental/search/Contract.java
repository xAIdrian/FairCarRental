package com.amohnacs.faircarrental.search;

import android.support.annotation.StringRes;
import android.widget.EditText;

import com.amohnacs.model.Result;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public interface Contract {

    interface Provider {

        void carSearch(Callback callback, String addressQueryString, String pickupSelection, String dropoffSelection);

        interface Callback {

            void onCarSearchResults(List<Result> carResults);
            void onCarSearchResultError(String errorMessage);
        }
    }

    interface Presenter {

        void validateAddress(String addressString);

        void onDateSetChecker(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);

        void validateInputsForSearch();
    }

    interface View {
        void addressValidity(boolean isValid);

        void displayDateSelection(String dialogIdentifier, String dateString);

        void searchParamError(@StringRes int messageRes);
    }
}
