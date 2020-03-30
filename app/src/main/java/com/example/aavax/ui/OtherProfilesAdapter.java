package com.example.aavax.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;

import model.Profile;

public class OtherProfilesAdapter extends RecyclerView.Adapter<OtherProfilesHolder> {

    private Context context;
    private ArrayList <Profile> profiles;

    public OtherProfilesAdapter(FragmentActivity activity, ArrayList<Profile> profileArrayList) {
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    @NonNull
    @Override
    public OtherProfilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.other_profiles_fragment_listview, parent, false);
        return new OtherProfilesHolder(view);
    }

    @Override
    public void onBindViewHolder(OtherProfilesHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.setDetails(profile);
    }
}


