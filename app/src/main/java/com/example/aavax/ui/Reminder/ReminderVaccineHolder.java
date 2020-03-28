package com.example.aavax.ui.Reminder;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.aavax.R;
import com.example.aavax.ui.AbsVaccineHolder;
import com.example.aavax.ui.homepage.MyVaccInfoFragment;

import model.Vaccine;

public class ReminderVaccineHolder extends AbsVaccineHolder {

    private TextView vaccineName;
    private TextView vaccineDate;

    public ReminderVaccineHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        vaccineName = itemView.findViewById(R.id.vaccine_name);
        vaccineDate = itemView.findViewById(R.id.vaccine_date);
    }

    @Override
    public void setDetails(Vaccine vaccine) {
        vaccineName.setText(vaccine.getName());
        vaccineDate.setText(vaccine.getDate());
    }

    @Override
    public void onClick(View itemView) {
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        Fragment myFragment = new MyVaccInfoFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }
}