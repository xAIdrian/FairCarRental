package com.amohnacs.faircarrental.search.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amohnacs.common.mvp.MvpActivity;
import com.amohnacs.faircarrental.R;
import com.amohnacs.faircarrental.search.contracts.SearchContract;
import com.amohnacs.faircarrental.search.SearchPresenter;
import com.amohnacs.model.amadeus.AmadeusResult;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends MvpActivity<SearchPresenter, SearchContract.View> implements SearchContract.View,
        DatePickerDialog.OnDateSetListener, SearchResultsFragment.OnListFragmentInteractionListener {
    private static final String TAG = SearchActivity.class.getSimpleName();

    public static final String PICKUP_DIALOG = "pickup_dialog";
    public static final String DROPOFF_DIALOG = "dropoff_dialog";

    @BindView(R.id.address_wrapper)
    TextInputLayout addressWrapper;

    @BindView(R.id.address_editText)
    EditText addressEditText;

    @BindView(R.id.pickup_button)
    Button pickupButton;

    @BindView(R.id.dropoff_button)
    Button dropoffButton;

    @BindView(R.id.pickup_result_textView)
    TextView pickUpResultTextView;

    @BindView(R.id.dropoff_result_textView)
    TextView dropOffResultTextView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private SearchPresenter presenter;
    private SearchResultsFragment searchResultFragment;

    private Calendar calendar;
    private boolean datePickerActive = false; //datepickerdialog is a little laggy

    private boolean isCompanyDescending = true;
    private boolean isDistaceDescending = true;
    private boolean isPriceDescending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        ButterKnife.bind(this);
        presenter = SearchPresenter.getInstance(this);

        SearchResultsFragment fragment = (SearchResultsFragment) getSupportFragmentManager().findFragmentByTag(SearchResultsFragment.TAG);
        if (fragment == null) {
            searchResultFragment = SearchResultsFragment.newInstance(1);
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            tr.replace(R.id.fragment_container, searchResultFragment, SearchResultsFragment.TAG);
            tr.addToBackStack(null);
            tr.commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white)); //Color of your title

        calendar = Calendar.getInstance();

        pickupButton.setOnClickListener((view) -> {
            showDatePickerDialog(PICKUP_DIALOG);
        });

        dropoffButton.setOnClickListener((view) -> {
            if (!datePickerActive) {
                showDatePickerDialog(DROPOFF_DIALOG);
                datePickerActive = true;
            }
        });

        fab.setOnClickListener((view) -> {
            if (!datePickerActive) {
                presenter.validateInputsForSearch();
                datePickerActive = true;
            }
        });

        addressEditText.addTextChangedListener(new SearchTextValidator(addressEditText));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public SearchPresenter getPresenter() {
        return presenter;
    }

    @Override
    public SearchContract.View getMvpView() {
        return this;
    }

    @Override
    public void addressValidity(boolean isValid) {
        if (isValid) {
            addressWrapper.setErrorEnabled(false);
        } else {
            try {
                addressWrapper.setError(getString(R.string.address_error));
                addressEditText.requestFocus();
            } catch (UnsupportedOperationException e) { //offset error thrown with backspaces
                e.printStackTrace();
            }
        }
    }

    @Override
    public void displayDateSelection(String dialogIdentifier, String dateString) {
        if (dialogIdentifier.equals(PICKUP_DIALOG)) {
            pickUpResultTextView.setText(dateString);

            dropoffButton.setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.ic_calendar_primary, 0, 0);
            pickupButton.setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.ic_calendar_today_accent, 0, 0);
        } else {
            dropOffResultTextView.setText(dateString);

            dropoffButton.setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.ic_calendar_accent, 0, 0);
        }
    }

    @Override
    public void searchParamError(@StringRes int messageRes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messageRes)
                .setTitle(R.string.param_error_title)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    // User clicked OK button
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    @Override
    public void validInputsLaunchFragment(String addressQueryString, String pickupSelection, String dropoffSelection) {
        SearchResultsFragment fragment = (SearchResultsFragment) getSupportFragmentManager().findFragmentByTag(SearchResultsFragment.TAG);
        getSupportFragmentManager().executePendingTransactions();
        fragment.makeCarResultsRequest(addressQueryString, pickupSelection, dropoffSelection);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        datePickerActive = false;
        presenter.onDateSetChecker(view, year, monthOfYear, dayOfMonth);
    }

    private void showDatePickerDialog(String tag) {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setOnCancelListener(dialog -> datePickerActive = false);
        datePickerDialog.setOnDismissListener(dialog -> datePickerActive = false);
        datePickerDialog.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.vibrate(true);
        datePickerDialog.dismissOnPause(true);

        datePickerDialog.show(getFragmentManager(), tag);
    }

    @Override
    public void resultsLoading(boolean setLoadingIndicator) {
        progressBar.setVisibility(setLoadingIndicator ? View.VISIBLE : View.GONE);
    }

    /**
     * When the user has finished entering data in edit text we send the string to the presenter
     * which ensures the string is not empty
     */
    public class SearchTextValidator implements TextWatcher {

        private View view;

        public SearchTextValidator(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //no op
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //no op
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.address_editText:
                    String addressString = addressEditText.getText().toString().trim();
                    presenter.validateAddress(addressString);
                    break;
            }
        }
    }
}
