package com.example.aavax.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;


public class SettingsAdapter extends RecyclerView.Adapter<SettingsHolder> {
    private Context context;
    private String uId;
    private ArrayList<String> settings;


    public SettingsAdapter(Context context, String uId, ArrayList<String> settings) {
        this.context = context;
        this.uId = uId;
        this.settings = settings;
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    @NonNull
    @Override
    public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vaccine_row, parent, false);
        return new SettingsHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingsHolder holder, int position) {
        holder.setDetails(settings.get(position));
        holder.setUId(uId);
    }
}
