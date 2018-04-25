package com.amohnacs.faircarrental.navigation;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.amohnacs.faircarrental.R;
import com.amohnacs.faircarrental.detail.RateAdapter;
import com.amohnacs.model.amadeus.AmadeusLocation;
import com.amohnacs.model.googlemaps.LatLngLocation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.GeoApiContext;
import com.google.maps.model.TravelMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = NavigationActivity.class.getSimpleName();

    private static final String ORIGIN_EXTRA = "origin_extra";
    private static final String DESTINATION_EXTRA = "destination_extra";

    @BindView(R.id.list)
    RecyclerView recyclerView;

    private GoogleMap mapMap;
    private String formattedDestinationString;
    private String formattedOriginString;

    private NavigationRequestObject requestObject;
    private NavigationAsyncTask navigationAsyncTask;

    public static Intent getStartIntent(Activity activity, LatLngLocation origin, AmadeusLocation destination) {
        Intent intent = new Intent(activity, NavigationActivity.class);
        intent.putExtra(ORIGIN_EXTRA, origin);
        intent.putExtra(DESTINATION_EXTRA, destination);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        if (getIntent().getExtras() != null) {
            formattedOriginString = originRequestFormatter(getIntent().getExtras().getParcelable(ORIGIN_EXTRA));
            formattedDestinationString = destRequestFormatter(getIntent().getExtras().getParcelable(DESTINATION_EXTRA));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        requestObject = new NavigationRequestObject(
                TravelMode.DRIVING, formattedOriginString, formattedDestinationString
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapMap = googleMap;
        if (requestObject != null) {
            navigationAsyncTask = new NavigationAsyncTask(getGeoContext(), mapMap, recyclerView);
            navigationAsyncTask.execute(requestObject);
        }
    }

    @Override
    protected void onStop() {
        if (navigationAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            navigationAsyncTask.cancel(true);
        }
        navigationAsyncTask = null;
        super.onStop();
    }

    private String originRequestFormatter(LatLngLocation location) {
        String returnValue = "";
        if (location != null) {
            returnValue += location.getLatitude() + "," + location.getLongitude();
        }
        return returnValue;
    }

    private String destRequestFormatter(AmadeusLocation location) {
        String returnValue = "";
        if (location != null) {
            returnValue += location.getLatitude() + "," + location.getLongitude();
        }
        return returnValue;
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_maps_key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }
}
