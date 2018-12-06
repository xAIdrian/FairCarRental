package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.util.Log;

import com.amohnacs.common.mvp.BasePresenter;
import com.amohnacs.faircarrental.BuildConfig;
import com.amohnacs.faircarrental.R;
import com.amohnacs.faircarrental.search.contracts.SearchContract;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.amohnacs.faircarrental.search.ui.SearchActivity.DROPOFF_DIALOG;
import static com.amohnacs.faircarrental.search.ui.SearchActivity.PICKUP_DIALOG;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {
    private static final String TAG = SearchPresenter.class.getSimpleName();

    private static volatile SearchPresenter instance;

    private Context context;

    private static final String DATE_HELPER_ZERO = "0";

    private SimpleDateFormat stringQueryFormat;
    private Date yesterday;

    private boolean addressIsValid = false;
    private Date pickupDate;
    private Date dropoffDate;
    private String addressQueryString;
    private CarSearchProvider provider;

    private SearchPresenter(Context context) {
        this.context = context;

        stringQueryFormat = new SimpleDateFormat("yyyy-MM-dd");
        yesterday = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
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
        int offsetZeroIndexMonth = monthOfYear + 1;
        String dateQueryString =
                year + "-" + (offsetZeroIndexMonth < 10 ? DATE_HELPER_ZERO + offsetZeroIndexMonth : offsetZeroIndexMonth) + "-"
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
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "inappropriate tag for date picker dialog");
            }
        }
    }

    @Override
    public void validateInputsForSearch() {
        //gets our query formatted string
        if (pickupDate != null && dropoffDate != null) {
            String pickupSelection = stringQueryFormat.format(pickupDate);
            String dropoffSelection = stringQueryFormat.format(dropoffDate);

            if (!pickupDate.after(yesterday)) {
                //check to see if date is before today
                if (isViewAttached()) {
                    getMvpView().searchParamError(R.string.pickup_before_today);
                }
            } else if (!dropoffDate.after(pickupDate)) {
                //check to see if date is before pickupdate
                if (isViewAttached()) {
                    getMvpView().searchParamError(R.string.pickup_after_dropoff);
                }
            } else if (!addressIsValid || addressQueryString == null || addressQueryString.isEmpty()) {
                //ensuring address is valid
                if(isViewAttached()) {
                    getMvpView().searchParamError(R.string.invalid_address);
                }
            } else {
                //success case
                if (isViewAttached()) {
                    getMvpView().validInputsLaunchFragment(addressQueryString, pickupSelection, dropoffSelection);
                }
            }
        } else {
            if (isViewAttached()) {
                getMvpView().searchParamError(R.string.date_missing_error);
            }
        }
    }
}
