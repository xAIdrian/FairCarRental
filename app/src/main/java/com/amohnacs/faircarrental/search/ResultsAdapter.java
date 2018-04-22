package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amohnacs.faircarrental.R;
import com.amohnacs.faircarrental.search.ui.SearchResultsFragment.OnListFragmentInteractionListener;
import com.amohnacs.model.amadeus.Address;
import com.amohnacs.model.amadeus.AmadeusResult;
import com.amohnacs.model.amadeus.Car;
import com.amohnacs.model.amadeus.AmadeusLocation;
import com.amohnacs.model.googlemaps.LatLngLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.CarResultViewHolder> {

    private static final String DISTANCE_PREPEND = "Distance : ";

    private final List<AmadeusResult> values;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;
    private final SearchResultsPresenter presenter;

    public ResultsAdapter(List<AmadeusResult> items, OnListFragmentInteractionListener listener,
                          Context context, SearchResultsPresenter presenter) {
        values = items;
        mListener = listener;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public CarResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_results_fragment_item, parent, false);
        return new CarResultViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final CarResultViewHolder holder, int position) {
        AmadeusResult workingItem = values.get(position);

        holder.setTitle(workingItem.getProvider().getCompanyName());
        holder.setAirport(workingItem.getAirport());
        holder.setMapLocation(workingItem.getAmadeusLocation());

        Address address = workingItem.getAddress();
        String addressString = address.getLine1() + ", " + address.getCity() + ", " + address.getCountry()
                + ", " + address.getPostalCode();
        holder.setAddress(addressString);

        holder.setCalculatedDistance(
                getDistanceString(workingItem.getAmadeusLocation(), presenter.getUserLatLngLocation())
        );
        holder.setCumulativePrice(getPriceString(workingItem));
    }

    // Price : $$$ / Price : $$$$
    private String getPriceString(AmadeusResult result) {
        float cumulativeValue = 0;
        String dollarDollarBillsYall = "$";

        for (Car car : result.getCars()) {
            cumulativeValue = car.getEstimatedTotal().getAmount();
        }

        result.setCumulativePrice(cumulativeValue);
        int remainder = (int) (cumulativeValue / 100);

        if (remainder > 30) {
            dollarDollarBillsYall = "$$$$$$";
        } else if (remainder > 25) {
            dollarDollarBillsYall = "$$$$$";
        } else if (remainder > 20) {
            dollarDollarBillsYall = "$$$$";
        } else if (remainder > 15) {
            dollarDollarBillsYall = "$$$";
        } else if (remainder > 10) {
            dollarDollarBillsYall = "$$";
        } else if (remainder > 5) {
            dollarDollarBillsYall = "$";
        } else {
            dollarDollarBillsYall = "¢";
        }
        return dollarDollarBillsYall;
    }

    private String getDistanceString(AmadeusLocation amadeusLocation, LatLngLocation userLocation) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);

        float distance = distance(userLocation.getLat(), userLocation.getLongg(),
                amadeusLocation.getLatitude(), amadeusLocation.getLongitude());
        String distanceString = decimalFormat.format(distance);
        return DISTANCE_PREPEND + distanceString + " km";
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    /**
     * Haversine Formula
     * I can't really take credit for this :/
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    private float distance(float lat_a, float lng_a, float lat_b, float lng_b)
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        float result = new Float(distance * meterConversion).floatValue();
        return result / 1000;
    }

    //////////////

    public class CarResultViewHolder extends RecyclerView.ViewHolder {

        private GoogleMap map;

        @BindView(R.id.company_code_name_textView)
        TextView companyTitleTextView;
        @BindView(R.id.airport_textView)
        TextView airportTextView;
        @BindView(R.id.map)
        MapView mapView;
        @BindView(R.id.address_textView)
        TextView addressTextView;
        @BindView(R.id.distance_textView)
        TextView distanceTextView;
        @BindView(R.id.price_textView)
        TextView priceTextView;

        AmadeusLocation amadeusLocation;

        CarResultViewHolder(View view, Context context) {
            super(view);

            ButterKnife.bind(this, view);

            mapView.onCreate(null);
            mapView.getMapAsync(googleMap -> {
                map = googleMap;

                MapsInitializer.initialize(context);
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

        void setTitle(String title) {
            companyTitleTextView.setText(title);
        }

        void setAirport(String airport) {
            airportTextView.setText(airport);
        }

        void setAddress(String address) {
            addressTextView.setText(address);
        }

        void setMapLocation(AmadeusLocation mapAmadeusLocation) {
            // If the map is ready, update its content.
            this.amadeusLocation = mapAmadeusLocation;
        }

        // TODO: 4/21/18 we still need to calculate the distance with userLocation
        void setCalculatedDistance(String calculatedDistance) {
            distanceTextView.setText(calculatedDistance);
        }

        public void setCumulativePrice(String price) {
            priceTextView.setText(price);
        }
    }
}
