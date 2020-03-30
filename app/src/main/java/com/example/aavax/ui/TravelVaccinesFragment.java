package com.example.aavax.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;

import java.util.ArrayList;
import java.util.Arrays;


public class TravelVaccinesFragment extends Fragment {

    private static final String TAG = "RemindersFragment";

    private IMainActivity mIMainActivity;
    //vars
    private ArrayList<String> mMandatoryVaccines = new ArrayList<>();
    private ArrayList<String> mRecommendedVaccines = new ArrayList<>();
    private ArrayList<Integer> mMandatoryTakenImgs = new ArrayList<>();
    private ArrayList<Integer> mRecommendedTakenImgs = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chosen_country, container, false);
        getVaccineEntries();
        Log.d(TAG, "initRecyclerView: init recyclerview.");

        RecyclerView recyclerViewManVaccines = view.findViewById(R.id.recyclerViewMandatoryVaccines);
        RecyclerViewAdapter adapterMan = new RecyclerViewAdapter(mMandatoryVaccines, mMandatoryTakenImgs);
        recyclerViewManVaccines.setAdapter(adapterMan);
        recyclerViewManVaccines.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerViewRecVaccines = view.findViewById(R.id.recyclerViewRecommendedVaccines);
        RecyclerViewAdapter adapterRec = new RecyclerViewAdapter(mRecommendedVaccines, mRecommendedTakenImgs);
        recyclerViewRecVaccines.setAdapter(adapterRec);
        recyclerViewRecVaccines.setLayoutManager(new LinearLayoutManager(getContext()));


        final Button viewClinicBtn = view.findViewById(R.id.ViewClinicsBtn);
        viewClinicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();

    }

    private void getVaccineEntries(){
        Resources res = getResources();
        mMandatoryVaccines = new ArrayList(Arrays.asList(res.getStringArray(R.array.vaccines)));

        for (String vaccineName : mMandatoryVaccines){
            mMandatoryTakenImgs.add(R.drawable.ic_warning_yellow_16dp);
        }

        mRecommendedVaccines = new ArrayList(Arrays.asList(res.getStringArray(R.array.vaccines)));

        for (String vaccineName : mRecommendedVaccines){
            mRecommendedTakenImgs.add(R.drawable.ic_warning_yellow_16dp);
        }

    }

}