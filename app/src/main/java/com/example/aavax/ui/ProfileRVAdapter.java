package com.example.aavax.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;

import model.ProfileRV;

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVHolder> {
    private Context context;

    private ArrayList<ProfileRV> profilesrv;

    public ProfileRVAdapter(Context context, ArrayList<ProfileRV> profiles){
        this.context = context;
        this.profilesrv = profiles;

    }
    @Override
    public int getItemCount() { return profilesrv.size(); }

    @NonNull
    @Override
    public ProfileRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_page_fragment_listview, parent, false);
        return new ProfileRVHolder(view);
    }
    @Override
    public void onBindViewHolder(ProfileRVHolder holder, int position) {
        ProfileRV profilerv = profilesrv.get(position);
        holder.setDetails(profilerv);
    }

}
