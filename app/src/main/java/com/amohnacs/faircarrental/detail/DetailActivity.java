package com.amohnacs.faircarrental.detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amohnacs.faircarrental.R;
import com.amohnacs.model.amadeus.AmadeusLocation;
import com.amohnacs.model.amadeus.Car;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final String FOCUSED_CAR_EXTRA = "focused_car_extra";

    @BindView(R.id.map)
    MapView mapView;

    private GoogleMap map;

    private Car focusedCar;
    private AmadeusLocation amadeusLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mapView.onCreate(null);
        mapView.getMapAsync(googleMap -> {
            map = googleMap;

            MapsInitializer.initialize(this);
            map.getUiSettings().setMapToolbarEnabled(false);

            updateMapContents(amadeusLocation);
        });
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
