package com.example.aavax.boundary.homepage;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import control.Vaccine;

public class VaccineHolder extends RecyclerView.ViewHolder {

    private TextView vaccineName;

    public VaccineHolder(View itemView) {
        super(itemView);
        vaccineName = itemView.findViewById(R.id.vaccine_name);
    }

    public void setDetails(Vaccine vaccine) {
        vaccineName.setText(vaccine.getName());
    }
}

