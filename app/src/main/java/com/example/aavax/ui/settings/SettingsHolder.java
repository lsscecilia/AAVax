package com.example.aavax.ui.settings;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.profile.OtherProfilesFragment;

import entity.Vaccine;

public class SettingsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView settingsName;
    private String uId;
    private String settingChoice;

    public SettingsHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        settingsName = itemView.findViewById(R.id.vaccine_name);
    }

    public void setDetails(String name) {
        settingsName.setText(name);
        this.settingChoice = name ;
    }

    public void setUId(String uId){
        this.uId = uId;
    }

    @Override
    public void onClick(View v) {
        if (settingChoice.compareTo("Change password")==0)
        {
            //go to change password fragment
            Fragment myFragment = new ChangePasswordFragment();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_settings, myFragment).addToBackStack("change password").commit();
        }

        if (settingChoice.compareTo("Delete account")==0)
        {
            Fragment myFragment = new DeleteAccountFragment();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_settings, myFragment).addToBackStack("delete account").commit();
        }

    }
}
