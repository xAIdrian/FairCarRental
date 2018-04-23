package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amohnacs.faircarrental.R;
import com.amohnacs.faircarrental.search.ui.SearchResultsFragment.OnListFragmentInteractionListener;
import com.amohnacs.model.amadeus.Address;
import com.amohnacs.model.amadeus.Car;
import com.amohnacs.model.amadeus.AmadeusLocation;
import com.amohnacs.model.amadeus.VehicleInfo;
import com.amohnacs.model.googlemaps.LatLngLocation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.CarResultViewHolder> {

    private static final String DISTANCE_PREPEND = "Distance : ";
    private static final String PRICE_PREPEND = "Price : ";

    private final List<Car> values;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;
    private final SearchResultsPresenter presenter;

    public ResultsAdapter(ArrayList<Car> items, OnListFragmentInteractionListener listener,
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
        Car car = values.get(position);
        VehicleInfo vi = car.getVehicleInfo();

        holder.setTitle(vi.getAcrissCode() + " : " + vi.getCategory() + " " + vi.getType());
        holder.setCompany(car.getCompanyName());

        Address address = car.getAddress();
        String addressString = address.getLine1() + ", " + address.getCity() + ", " + address.getCountry()
                + ", " + address.getPostalCode();
        holder.setAddress(addressString);

        holder.setCalculatedDistance(
                getDistanceString(car.getAmadeusLocation(), presenter.getUserLatLngLocation())
        );

        if (car.getRates() != null && car.getRates().size() > 0) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String output = formatter.format(car.getRates().get(0).getPrice().getAmount());
            holder.setPrice(PRICE_PREPEND + output);
        }
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

    class CarResultViewHolder extends RecyclerView.ViewHolder {

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

        CarResultViewHolder(View view, Context context) {
            super(view);

            ButterKnife.bind(this, view);

        }

        void setTitle(String title) {
            companyTitleTextView.setText(title);
        }

        void setCompany(String airport) {
            companyTextView.setText(airport);
        }

        void setAddress(String address) {
            addressTextView.setText(address);
        }

        void setCalculatedDistance(String calculatedDistance) {
            distanceTextView.setText(calculatedDistance);
        }

        void setPrice(String price) {
            priceTextView.setText(price);
        }
    }
}
