package com.example.aavax.boundary.homepage;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import control.Vaccine;

public class VaccineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView vaccineName;

    public VaccineHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        vaccineName = itemView.findViewById(R.id.vaccine_name);
    }

    public void setDetails(Vaccine vaccine) {
        vaccineName.setText(vaccine.getName());
    }

    @Override
    public void onClick(View itemView) {
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        Fragment myFragment = new MyVaccInfoFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }
}

