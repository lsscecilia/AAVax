package com.example.aavax.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.homepage.MyVaccInfoFragment;

import model.Vaccine;

public class ClinicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView clinicName;
    private String clinic;

    public ClinicHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        clinicName = itemView.findViewById(R.id.vaccine_name);
    }



    public void setClinic(String clinic){
        clinicName.setText(clinic);
    }

    @Override
    public void onClick(View itemView) {
        //the pointer pop out

        /*
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        Fragment myFragment = new MyVaccInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uId", uId);
        System.out.println("vaccine name lolol" + vaccineName.getText());
        bundle.putString("vaccineName", (String) vaccineName.getText());
        myFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();*/
    }

}
