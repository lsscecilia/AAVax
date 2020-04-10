package com.example.aavax.ui.travel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.IMainActivity;

import java.util.ArrayList;

/**
 * Adapter to show the list of vaccines required for a country
 */
public class RecyclerViewTravelVacAdapter extends RecyclerView.Adapter<RecyclerViewTravelVacAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mVaccineNames = new ArrayList<>();
    private ArrayList<Integer> mTakenImgs = new ArrayList<>();
    //private Context mContext;
    private IMainActivity mIMainActivity;

    public RecyclerViewTravelVacAdapter(ArrayList<String> mVaccineNames, ArrayList<Integer> mTakenImgs) {
        this.mVaccineNames = mVaccineNames;
        this.mTakenImgs = mTakenImgs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_vaccine_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.vaccineName.setText(mVaccineNames.get(position));

        holder.takenImg.setImageResource(mTakenImgs.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mVaccineNames.get(position));
                Context context = v.getContext();
                mIMainActivity = (IMainActivity) context;
                mIMainActivity.inflateFragment(mVaccineNames.get(position), mVaccineNames.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVaccineNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView takenImg;
        TextView vaccineName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            takenImg = itemView.findViewById(R.id.image_taken);
            vaccineName = itemView.findViewById(R.id.vaccine_name_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
