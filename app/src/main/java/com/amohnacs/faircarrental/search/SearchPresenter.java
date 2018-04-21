package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.util.Log;

import com.amohnacs.common.mvp.BasePresenter;
import com.amohnacs.faircarrental.R;
import com.amohnacs.model.Result;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.amohnacs.faircarrental.search.SearchActivity.DROPOFF_DIALOG;
import static com.amohnacs.faircarrental.search.SearchActivity.PICKUP_DIALOG;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class SearchPresenter extends BasePresenter<Contract.View> implements Contract.Presenter, Contract.Provider.Callback {
    private static final String TAG = SearchPresenter.class.getSimpleName();

    private static volatile SearchPresenter instance;

    private Context context;

    private static final String DATE_HELPER_ZERO = "0";

    SimpleDateFormat stringQueryFormat;
    Date todaysDate;

    private boolean addressIsValid = false;
    private Date pickupDate;
    private Date dropoffDate;
    private String addressQueryString;
    private SearchProvider provider;

    private SearchPresenter(Context context) {
        this.context = context;

        provider = SearchProvider.getInstance(context);

        stringQueryFormat = new SimpleDateFormat("yyyy-MM-dd");
        todaysDate = new Date();
    }

    public static SearchPresenter getInstance(Context context) {
        if (instance == null) {
            synchronized (SearchPresenter.class) {
                if (instance == null) {
                    instance = new SearchPresenter(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void validateAddress(String addressString) {
        addressQueryString = addressString;

        if (addressString.isEmpty()) {
            addressIsValid = false;
            if (isViewAttached()) {
                getMvpView().addressValidity(false);
            }
        } else {
            addressIsValid = true;
            if (isViewAttached()) {
                getMvpView().addressValidity(true);
            }
        }

    }

    @Override
    public void onDateSetChecker(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateQueryString =
                year + "-" + (monthOfYear < 10 ? DATE_HELPER_ZERO + monthOfYear : monthOfYear) + "-"
                        + (dayOfMonth < 10 ? DATE_HELPER_ZERO + dayOfMonth : dayOfMonth);
        //chaning our string to a date
        Date selectedDate = new Date();
        try {
            selectedDate = stringQueryFormat.parse(dateQueryString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (view.getTag().equals(PICKUP_DIALOG)) {
            pickupDate = selectedDate;
            if (isViewAttached()) {
                getMvpView().displayDateSelection(view.getTag(), stringQueryFormat.format(selectedDate));
            }
        } else if (view.getTag().equals(DROPOFF_DIALOG)) {
            dropoffDate = selectedDate;
            if (isViewAttached()) {
                getMvpView().displayDateSelection(view.getTag(), stringQueryFormat.format(selectedDate));
            }
        } else {
            Log.e(TAG, "inappropriate tag for date picker dialog");
        }
    }

    @Override
    public void validateInputsForSearch() {
        //gets our query formatted string
        if (pickupDate != null && dropoffDate != null) {
            String pickupSelection = stringQueryFormat.format(pickupDate);
            String dropoffSelection = stringQueryFormat.format(dropoffDate);

            if (!pickupDate.after(todaysDate)) {
                //check to see if date is before today
                if (isViewAttached()) {
                    getMvpView().searchParamError(R.string.pickup_before_today);
                }
            } else if (dropoffDate.after(pickupDate)) {
                //check to see if date is before pickupdate
                if (isViewAttached()) {
                    getMvpView().searchParamError(R.string.pickup_after_dropoff);
                }
            } else if (!addressIsValid && addressQueryString != null) {
                //ensuring address is valid
                if(isViewAttached()) {
                    getMvpView().searchParamError(R.string.invalid_address);
                }
            } else {
                //success case
                provider.carSearch(this, addressQueryString, pickupSelection, dropoffSelection);
            }
        } else {
            if (isViewAttached()) {
                getMvpView().searchParamError(R.string.date_missing_error);
            }
        }
    }

    @Override
    public void onCarSearchResults(List<Result> carResults) {

    }

    @Override
    public void onCarSearchResultError(String errorMessage) {

    }
}
