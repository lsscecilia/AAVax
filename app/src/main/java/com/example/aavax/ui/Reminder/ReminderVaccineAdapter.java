package com.example.aavax.ui.Reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;

import model.Vaccine;

public class ReminderVaccineAdapter extends RecyclerView.Adapter<ReminderVaccineHolder>{

    private Context context;

    private ArrayList<Vaccine> vaccines;

    public ReminderVaccineAdapter(Context context, ArrayList<Vaccine> vaccines) {
        this.context = context;
        this.vaccines = vaccines;
    }
    @Override
    public int getItemCount() {
        return vaccines.size();
    }
    @NonNull
    @Override
    public ReminderVaccineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reminder_vaccine_row, parent, false);
        return new ReminderVaccineHolder(view);
    }

    @Override
    public void onBindViewHolder(ReminderVaccineHolder holdr, int position) {
        Vaccine vaccine = vaccines.get(position);
        holdr.setDetails(vaccine);
    }

}
