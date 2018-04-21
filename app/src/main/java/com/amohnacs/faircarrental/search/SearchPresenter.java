package com.amohnacs.faircarrental.search;

import android.content.Context;

import com.amohnacs.common.mvp.BasePresenter;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class SearchPresenter extends BasePresenter<Contract.View> implements Contract.Presenter {
    private static final String TAG = SearchPresenter.class.getSimpleName();

    private static volatile SearchPresenter instance;

    private Context context;

    private SearchPresenter(Context context) {
        this.context = context;
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
        if (isViewAttached()) {
            getMvpView().addressValidity(!addressString.isEmpty());
        }
    }

    @Override
    public void validatePickup(String pickupString) {

    }

    @Override
    public void validateDropOff(String dropoffString) {

    }
}
