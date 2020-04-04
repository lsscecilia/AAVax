package com.example.aavax.ui.homepage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import entity.Vaccine;

public class VaccineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView vaccineName;
    private String uId;
    private static final String TAG = "My Vaccinations";

    public VaccineHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        vaccineName = itemView.findViewById(R.id.vaccine_name);
    }

    public void setDetails(Vaccine vaccine) {
        vaccineName.setText(vaccine.getName());
    }

    public void setUId(String uId){
        this.uId = uId;
    }

    @Override
    public void onClick(View itemView) {
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        Fragment myFragment = new MyVaccInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uId", uId);
        bundle.putString("vaccineName", (String) vaccineName.getText());
        myFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }


}

