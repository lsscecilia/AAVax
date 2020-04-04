package com.example.aavax.ui.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicHolder> {
    private Context context;
    private ArrayList<String> clinics;

    public ClinicAdapter(Context context, ArrayList<String> clinics) {
        this.context = context;
        this.clinics = clinics;
    }

    @Override
    public int getItemCount() {
        return clinics.size();
    }

    @NonNull
    @Override
    public ClinicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vaccine_row, parent, false);
        return new ClinicHolder(view);
    }

    @Override
    public void onBindViewHolder(ClinicHolder holder, int position) {
        String clinic = clinics.get(position);
        holder.setClinic(clinic);
    }
}
