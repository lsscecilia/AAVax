package com.example.aavax.ui.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;

import entity.Vaccine;

/**
 * adapter to show the list of vaccines
 */
public class VaccineAdapter extends RecyclerView.Adapter<VaccineHolder> {

    private Context context;
    private String uId;
    private ArrayList<Vaccine> vaccines;

    public VaccineAdapter(Context context, ArrayList<Vaccine> vaccines, String uId) {
        this.context = context;
        this.vaccines = vaccines;
        this.uId = uId;
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
        holder.setUId(uId);
    }
}
