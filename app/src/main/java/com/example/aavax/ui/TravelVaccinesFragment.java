package com.example.aavax.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.Arrays;

import model.Country;
import model.Vaccine;


public class TravelVaccinesFragment extends Fragment {

    private static final String TAG = "RemindersFragment";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private IMainActivity mIMainActivity;
    //vars
    private ArrayList<String> mMandatoryVaccines = new ArrayList<>();
    private ArrayList<String> mRecommendedVaccines = new ArrayList<>();
    private ArrayList<Integer> mMandatoryTakenImgs = new ArrayList<>();
    private ArrayList<Integer> mRecommendedTakenImgs = new ArrayList<>();
    private FirebaseManager firebaseManager;
    private String mIncomingMessage = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
        Bundle bundle = this.getArguments();
        if (bundle != null){
            mIncomingMessage = bundle.getString((getString(R.string.intent_message)));
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chosen_country, container, false);
        getVaccineEntries();
        Log.d(TAG, "initRecyclerView: init recyclerview.");

        RecyclerView recyclerViewManVaccines = view.findViewById(R.id.recyclerViewMandatoryVaccines);
        RecyclerViewTravelVacAdapter adapterMan = new RecyclerViewTravelVacAdapter(mMandatoryVaccines, mMandatoryTakenImgs);
        recyclerViewManVaccines.setAdapter(adapterMan);
        recyclerViewManVaccines.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerViewRecVaccines = view.findViewById(R.id.recyclerViewRecommendedVaccines);
        RecyclerViewTravelVacAdapter adapterRec = new RecyclerViewTravelVacAdapter(mRecommendedVaccines, mRecommendedTakenImgs);
        recyclerViewRecVaccines.setAdapter(adapterRec);
        recyclerViewRecVaccines.setLayoutManager(new LinearLayoutManager(getContext()));

        if(isServicesOK()){
            Button viewClinicsBtn = (Button) view.findViewById(R.id.ViewClinicsBtn);
            viewClinicsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIMainActivity.inflateFragment("Nearby Clinics", "");
                }
            });
        }

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();

    }

    private void getVaccineEntries(){

        firebaseManager = new FirebaseManager();
        firebaseManager.initCountries();
        Resources res = getResources();
        //mMandatoryVaccines = new ArrayList(Arrays.asList(res.getStringArray(R.array.vaccines)));
        mMandatoryVaccines = new ArrayList<>();
        System.out.println("Just before");
        if (mIncomingMessage == "Malaysia") {
            System.out.println("I'm in");
            firebaseManager.retrieveCountry(new FirebaseManager.MyCallBackCountry() {
                @Override
                public void onCallback(Country value) {
                    ArrayList<Vaccine> mandatoryVaccines = value.getVaccineRequired();
                    System.out.println(value.getName());
                    System.out.println(value.getVaccineRequired());
                    for (Vaccine vaccine : mandatoryVaccines) {
                        String name = vaccine.getName();
                        mMandatoryVaccines.add(name);
                    }
                }
            }, mIncomingMessage);
        }
        for (String vaccineName : mMandatoryVaccines){
            mMandatoryTakenImgs.add(R.drawable.ic_warning_yellow_16dp);
        }

        mRecommendedVaccines = new ArrayList(Arrays.asList(res.getStringArray(R.array.vaccines)));

        for (String vaccineName : mRecommendedVaccines){
            mRecommendedTakenImgs.add(R.drawable.ic_warning_yellow_16dp);
        }

    }


    public boolean isServicesOK(){
            Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //error occurred but resolvable
            Log.d(TAG, "isServicesOK: resolvable error");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(getContext(), "No map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


}