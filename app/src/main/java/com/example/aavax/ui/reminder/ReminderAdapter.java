package com.example.aavax.ui.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;

import entity.VaccineLogEntry;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderHolder>{
    private Context context;
    private String uId;

    private ArrayList<VaccineLogEntry> vaccineLog;

    public ReminderAdapter(Context context, ArrayList<VaccineLogEntry> vaccineLog, String uId) {
        this.context = context;
        this.vaccineLog = vaccineLog;
        this.uId = uId;
    }

    @Override
    public int getItemCount() {
        return vaccineLog.size();
    }
    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reminder_row, parent, false);
        return new ReminderHolder(view);
    }

    @Override
    public void onBindViewHolder(ReminderHolder holder, int position) {
        VaccineLogEntry vaccine = vaccineLog.get(position);
        holder.setDetails(vaccine);
        holder.setUId(uId);
    }
}
