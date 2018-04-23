package com.amohnacs.faircarrental.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amohnacs.faircarrental.R;
import com.amohnacs.model.amadeus.Address;
import com.amohnacs.model.amadeus.AmadeusLocation;
import com.amohnacs.model.amadeus.Car;
import com.amohnacs.model.amadeus.Image;
import com.amohnacs.model.amadeus.VehicleInfo;
import com.amohnacs.model.googlemaps.LatLngLocation;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amohnacs.faircarrental.MyUtils.getDistanceString;
import static com.amohnacs.faircarrental.search.CarAdapter.PRICE_PREPEND;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final String FOCUSED_CAR_EXTRA = "focused_car_extra";
    private static final String USER_LOCATION_EXTRA = "user_location_extra";

    private static final String SAVED_INSTANCE_STATE_CAR = "saved_instance_state_car";
    private static final String SAVED_INSTANCE_STATE_USER_LOCATION = "saved_instance_state_user_location";

    private static final String AC_NEGATIVE = "Does not have air conditioning";
    private static final String AC_POSITIVE = "Has air conditioning";

    @BindView(R.id.map)
    MapView mapView;

    @BindView(R.id.fab)
    FloatingActionButton navFab;

    @BindView(R.id.company_code_name_textView)
    TextView companyTitleTextView;

    @BindView(R.id.company_textView)
    TextView companyTextView;

    @BindView(R.id.address_textView)
    TextView addressTextView;

    @BindView(R.id.distance_textView)
    TextView distanceTextView;

    @BindView(R.id.price_textView)
    TextView priceTextView;

    @BindView(R.id.transmission_textView)
    TextView transmissionTextView;

    @BindView(R.id.fuel_textView)
    TextView fuelTextView;

    @BindView(R.id.air_conditioning_textView)
    TextView airConditioningTextView;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.car_imageView)
    ImageView carImageView;

    private GoogleMap map;

    private Car focusedCar;
    private VehicleInfo vi;
    private LatLngLocation userLocation;
    private RateAdapter adapter;

    public static Intent getStartIntent(Activity activity, Car car, LatLngLocation location) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(FOCUSED_CAR_EXTRA, car);
        intent.putExtra(USER_LOCATION_EXTRA, location);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) {
                focusedCar = getIntent().getExtras().getParcelable(FOCUSED_CAR_EXTRA);
                userLocation = getIntent().getExtras().getParcelable(USER_LOCATION_EXTRA);
            }
        } else {
            focusedCar = savedInstanceState.getParcelable(SAVED_INSTANCE_STATE_CAR);
            userLocation = savedInstanceState.getParcelable(SAVED_INSTANCE_STATE_USER_LOCATION);
        }

        if (focusedCar == null) {
            throw new IllegalArgumentException("No car was passed to Detail Activity.  Something went very wrong");
        }
        vi = focusedCar.getVehicleInfo();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //google maps setup
        mapView.onCreate(null);
        mapView.getMapAsync(googleMap -> {
            map = googleMap;

            MapsInitializer.initialize(this);
            map.getUiSettings().setMapToolbarEnabled(false);

            updateMapContents(focusedCar.getAmadeusLocation());
        });

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.transparent));

        String title = vi.getAcrissCode() + " : " + vi.getCategory() + " " + vi.getType();
        companyTitleTextView.setText(title);
        companyTextView.setText(focusedCar.getCompanyName());

        Address address = focusedCar.getAddress();
        String addressString = address.getLine1() + ", " + address.getCity() + ", " + address.getCountry()
                + ", " + address.getPostalCode();
        addressTextView.setText(addressString);

        distanceTextView.setText(
                getDistanceString(focusedCar.getAmadeusLocation(), userLocation)
        );

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String output = PRICE_PREPEND + formatter.format(focusedCar.getEstimatedTotal().getAmount());
        priceTextView.setText(output);

        transmissionTextView.setText(vi.getTransmission());
        fuelTextView.setText(vi.getFuel());
        airConditioningTextView.setText(vi.isAirConditioning() ? AC_POSITIVE : AC_NEGATIVE);

        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RateAdapter(this, focusedCar.getRates());
        recyclerView.setAdapter(adapter);

        Image image = focusedCar.getImage();
        if (image != null) {
            Glide
                    .with(this)
                    .load(image.getUrl())
                    .into(carImageView);
        }

        navFab.setOnClickListener(v -> {
            //todo navigation activity OR intent to Google Maps ?

        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_INSTANCE_STATE_CAR, focusedCar);
        outState.putParcelable(SAVED_INSTANCE_STATE_USER_LOCATION, userLocation);
        super.onSaveInstanceState(outState);
    }

    private void updateMapContents(AmadeusLocation mapAmadeusLocation) {
        LatLng latlng = new LatLng(mapAmadeusLocation.getLatitude(), mapAmadeusLocation.getLongitude());
        // Since the mapView is re-used, need to remove pre-existing mapView features.
        map.clear();

        // Update the mapView feature data and camera position.
        map.addMarker(new MarkerOptions().position(latlng));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 10f);
        map.moveCamera(cameraUpdate);
    }
}
