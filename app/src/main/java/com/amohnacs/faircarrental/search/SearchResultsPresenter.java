package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.util.Log;

import com.amohnacs.common.mvp.BasePresenter;
import com.amohnacs.faircarrental.search.contracts.SearchResultsContract;
import com.amohnacs.model.Result;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/21/18.
 */

public class SearchResultsPresenter extends BasePresenter<SearchResultsContract.View> implements SearchResultsContract.Presenter, SearchResultsContract.Provider.Callback {
    private static final String TAG = SearchResultsPresenter.class.getSimpleName();

    private static volatile SearchResultsPresenter instance;

    private Context context;

    private SearchResultsPresenter(Context context) {
        this.context = context;
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

    }

    @Override
    public void onCarSearchResults(List<Result> carResults) {

    }

    @Override
    public void onCarSearchResultError(String errorMessage) {

    }
}
