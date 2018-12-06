package com.amohnacs.faircarrental.navigation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amohnacs.faircarrental.R;
import com.google.maps.model.DirectionsStep;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    public static final String PRICE_PREPEND = "Price : ";

    private final DirectionsStep[] values;

    public StepsAdapter(DirectionsStep[] items) {
        values = items;
    }

    @Override
    public StepsAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.navigation_activity_item, parent, false);
        return new StepsAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepsAdapter.StepViewHolder holder, int position) {
        DirectionsStep step = values[position];

        holder.setDistance(step.distance.toString());
        holder.setDuration(step.duration.toString());
        holder.setStep(step.htmlInstructions);
    }

    @Override
    public int getItemCount() {
        return values.length;
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.distance_textView)
        TextView distanceTextView;
        @BindView(R.id.duration_textView)
        TextView durationTextView;
        @BindView(R.id.step_textView)
        TextView htmlStepTextView;

        StepViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }

        void setDistance(String distance) {
            distanceTextView.setText(distance);
        }

        void setDuration(String duration) {
            durationTextView.setText(duration);
        }

        void setStep(String htmlStep) {
            htmlStepTextView.setText(Html.fromHtml(htmlStep));
        }
    }
}