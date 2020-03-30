package com.example.aavax.ui;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import model.Profile;


public class OtherProfilesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView profileName;
    public OtherProfilesHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        profileName = itemView.findViewById(R.id.profile_name);
    }

    public void setDetails(Profile profile) {
        profileName.setText(profile.getName());
    }

    @Override
    public void onClick(View itemView) {
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        Fragment myFragment = new ProfilePageFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }
}
