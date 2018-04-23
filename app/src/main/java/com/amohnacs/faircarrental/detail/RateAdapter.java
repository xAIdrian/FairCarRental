package com.amohnacs.faircarrental.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amohnacs.faircarrental.R;
import com.amohnacs.model.amadeus.Rate;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/23/18.
 */

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.ViewHolder> {

    private List<Rate> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    RateAdapter(Context context, List<Rate> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.detail_activity_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rate rate = mData.get(position);
        String price = "$" + rate.getPrice().getAmount();
        holder.myTextView.setText(price);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.rate_textView);
        }
    }
}