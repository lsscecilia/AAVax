package com.example.aavax.ui.reminder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.homepage.MyVaccInfoFragment;

import entity.VaccineLogEntry;

public class ReminderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView vaccineName;
    private TextView vaccineDate;
    private String uId;

    public ReminderHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        vaccineName = itemView.findViewById(R.id.vaccine_name);
        vaccineDate = itemView.findViewById(R.id.vaccine_date);
    }


    public void setDetails(VaccineLogEntry vaccineLogEntry) {
        vaccineName.setText(vaccineLogEntry.getVaccine().getName());
        vaccineDate.setText(vaccineLogEntry.getNextDue().getMonth()+"/"+vaccineLogEntry.getNextDue().getDate()+"/"+vaccineLogEntry.getNextDue().getYear());

    }

    public void setUId(String uId){
        this.uId = uId;
    }

    @Override
    public void onClick(View v) {
        //link to my vacc info
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        Fragment myFragment = new MyVaccInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uId", uId);
        System.out.println("vaccine name lolol" + vaccineName.getText());
        bundle.putString("vaccineName", (String) vaccineName.getText());
        myFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }
}
