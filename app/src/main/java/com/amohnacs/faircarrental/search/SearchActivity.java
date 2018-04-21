package com.amohnacs.faircarrental.search;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amohnacs.common.mvp.MvpActivity;
import com.amohnacs.faircarrental.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends MvpActivity<SearchPresenter, Contract.View> implements Contract.View {
    private static final String TAG = SearchActivity.class.getSimpleName();

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

    private SearchPresenter presenter;

    private boolean addressIsValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        ButterKnife.bind(this);
        presenter = SearchPresenter.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white)); //Color of your title

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    public Contract.View getMvpView() {
        return this;
    }

    @Override
    public void addressValidity(boolean isValid) {
        if(isValid) {
            addressWrapper.setErrorEnabled(false);
        } else {
            addressWrapper.setError(getString(R.string.address_error));
            addressEditText.requestFocus();
            addressIsValid = false;
        }
    }

    public class SearchTextValidator implements TextWatcher {

        private View view;

        public SearchTextValidator(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

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
