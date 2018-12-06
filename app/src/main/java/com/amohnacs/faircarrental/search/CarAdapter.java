package com.amohnacs.faircarrental.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amohnacs.faircarrental.R;
import com.amohnacs.faircarrental.MyUtils;
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

import static com.amohnacs.faircarrental.MyUtils.getDistanceString;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarResultViewHolder> {

    public static final String PRICE_PREPEND = "Price : ";

    private final List<Car> values;
    private final RecyclerClickListener recyclerClickListener;
    private final SearchResultsPresenter presenter;

    public CarAdapter(ArrayList<Car> items, RecyclerClickListener listener,
                      Context context, SearchResultsPresenter presenter) {
        values = items;
        recyclerClickListener = listener;
        this.presenter = presenter;
    }

    @Override
    public CarResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_results_fragment_item, parent, false);
        return new CarResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CarResultViewHolder holder, int position) {
        Car car = values.get(position);
        VehicleInfo vi = car.getVehicleInfo();

        holder.setTitle(vi.getAcrissCode() + " : " + vi.getCategory() + " " + vi.getType());
        holder.setCompany(car.getCompanyName());

        Address address = car.getAddress();
        String addressString = address.getLine1() + ", " + address.getCity() + ", " + address.getCountry()
                + ", " + address.getPostalCode();
        holder.setAddress(addressString);

        if (presenter.getUserLatLngLocation() != null) {
            holder.setCalculatedDistance(
                    getDistanceString(car.getAmadeusLocation(), presenter.getUserLatLngLocation())
            );
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String output = formatter.format(car.getEstimatedTotal().getAmount());
        holder.setPrice(PRICE_PREPEND + output);

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

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

        CarResultViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(v ->
                    recyclerClickListener.onItemClick(values.get(getAdapterPosition())));
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

    public interface RecyclerClickListener {
        void onItemClick(Car car);
    }
}
