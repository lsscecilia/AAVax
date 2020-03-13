package com.example.aavax.boundary.homepage;

import android.content.Context;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;

import control.Vaccine;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineHolder> {

    private Context context;

    private ArrayList<Vaccine> vaccines;

    public VaccineAdapter(Context context, ArrayList<Vaccine> vaccines) {
        this.context = context;
        this.vaccines = vaccines;
    }

    @Override
    public int getItemCount() {
        return vaccines.size();
    }

    @NonNull
    @Override
    public VaccineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vaccine_row, parent, false);
        return new VaccineHolder(view);
    }

    @Override
    public void onBindViewHolder(VaccineHolder holder, int position) {
        Vaccine vaccine = vaccines.get(position);
        holder.setDetails(vaccine);
    }
}
