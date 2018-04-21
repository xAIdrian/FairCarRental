package com.amohnacs.faircarrental.search.contracts;

import android.support.annotation.StringRes;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public interface SearchContract {

    interface Presenter {

        void validateAddress(String addressString);

        void onDateSetChecker(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);

        void validateInputsForSearch();
    }

    interface View {
        void addressValidity(boolean isValid);

        void displayDateSelection(String dialogIdentifier, String dateString);

        void searchParamError(@StringRes int messageRes);

        void validInputsLaunchFragment(String addressQueryString, String pickupSelection, String dropoffSelection);
    }
}
