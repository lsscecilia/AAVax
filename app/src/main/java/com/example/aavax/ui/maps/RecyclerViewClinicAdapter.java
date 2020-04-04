package com.example.aavax.ui.maps;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.IMainActivity;

import java.util.ArrayList;

public class RecyclerViewClinicAdapter extends RecyclerView.Adapter<RecyclerViewClinicAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mClinicNames = new ArrayList<>();
    private ArrayList<String> mDistances = new ArrayList<>();
    private IMainActivity mIMainActivity;

    public RecyclerViewClinicAdapter(ArrayList<String> mClinicNames, ArrayList<String> mDistances) {
        this.mClinicNames = mClinicNames;
        this.mDistances = mDistances;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clinic_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.clinicName.setText(mClinicNames.get(position));

        holder.distanceValue.setText(mDistances.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mClinicNames.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mClinicNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView distanceValue;
        TextView clinicName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            distanceValue = itemView.findViewById(R.id.distance_value);
            clinicName = itemView.findViewById(R.id.clinic_name_text);
            parentLayout = itemView.findViewById(R.id.clinic_parent_layout);
        }
    }
}
