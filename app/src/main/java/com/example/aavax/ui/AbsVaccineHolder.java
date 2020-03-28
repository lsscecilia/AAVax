package com.example.aavax.ui;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.homepage.MyVaccInfoFragment;

import model.Vaccine;


public abstract class AbsVaccineHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView vaccineName;
    private TextView vaccineDate;

    public AbsVaccineHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        vaccineName = itemView.findViewById(R.id.vaccine_name);
        vaccineDate = itemView.findViewById(R.id.vaccine_date);
    }

    public abstract void setDetails(Vaccine vaccine);

    @Override
    public void onClick(View itemView) {
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        Fragment myFragment = new MyVaccInfoFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }
}
