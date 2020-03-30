package com.example.aavax.ui;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import model.ProfileRV;

public class ProfileRVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView profileRVName;

    public ProfileRVHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        profileRVName = itemView.findViewById(R.id.profile_recycler);
    }
    public void setDetails(ProfileRV profile) { profileRVName.setText(profile.getName()); }

    @Override
    public void onClick(View itemView) {
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        Fragment myFragment = new OtherProfilesFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
    }
}
