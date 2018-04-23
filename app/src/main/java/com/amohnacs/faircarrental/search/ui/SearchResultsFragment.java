package com.amohnacs.faircarrental.search.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amohnacs.common.mvp.MvpFragment;
import com.amohnacs.faircarrental.R;
import com.amohnacs.faircarrental.search.ResultsAdapter;
import com.amohnacs.faircarrental.search.contracts.SearchResultsContract;
import com.amohnacs.faircarrental.search.SearchResultsPresenter;
import com.amohnacs.model.amadeus.Car;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SearchResultsFragment extends MvpFragment<SearchResultsPresenter, SearchResultsContract.View> implements SearchResultsContract.View {
    public static final String TAG = SearchResultsFragment.class.getSimpleName();

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String CAR_LIST_RESULTS_BUNDLE = "amadeus_results_bundle";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener callbackToActivity;
    private ArrayList<Car> carList;
    private SearchResultsPresenter presenter;
    private ResultsAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchResultsFragment() {
    }

    public static SearchResultsFragment newInstance(int columnCount) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        carList = new ArrayList<>();

        if (savedInstanceState == null || !savedInstanceState.containsKey(CAR_LIST_RESULTS_BUNDLE)) {
            if (getArguments() != null) {
                mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            }
        } else {
            carList.addAll(savedInstanceState.getParcelableArrayList(CAR_LIST_RESULTS_BUNDLE));
        }

        presenter = SearchResultsPresenter.getInstance(getActivity());
        adapter = new ResultsAdapter(carList, callbackToActivity, getActivity(), presenter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_results_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if (getActivity() != null) {
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            }

            recyclerView.setAdapter(adapter);
            //alleviates the stopping of the fling action when the user ACTION_UP
            ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(CAR_LIST_RESULTS_BUNDLE, carList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            callbackToActivity = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackToActivity = null;
    }

    @Override
    public SearchResultsPresenter getPresenter() {
        return presenter;
    }

    @Override
    public SearchResultsContract.View getMvpView() {
        return this;
    }

    protected void makeCarResultsRequest(String addressQueryString, String pickupSelection, String dropoffSelection) {
        callbackToActivity.resultsLoading(true);
        presenter.getCars(addressQueryString, pickupSelection, dropoffSelection);
    }

    @Override
    public void updateCarSearchResults(ArrayList<Car> carResults) {
        callbackToActivity.resultsLoading(false);

        if (carList.size() > 0) {
            carList.clear();
        }
        carList.addAll(carResults);

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void resultsLoading(boolean setLoadingIndicator);
    }
}
