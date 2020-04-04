package com.example.aavax.ui.reminder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.FirebaseManager;
import com.example.aavax.ui.IMainActivity;
import com.example.aavax.ui.maps.MapsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import entity.FirebaseInterface;
import entity.VaccineLogEntry;


public class RemindersPageFragment extends Fragment {

    private static final String TAG = "RemindersFragment";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private IMainActivity mIMainActivity;

    private RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private ArrayList<VaccineLogEntry> vaccineLogEntries;
    private String uId;
    private FirebaseInterface firebaseManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        View view = inflater.inflate(R.layout.fragment_reminders_page, container, false);
        firebaseManager = new FirebaseManager();
        super.onCreateView(inflater, container, savedInstanceState);
        vaccineLogEntries = new ArrayList<>();

        Bundle bundle = this.getArguments();

        if(isServicesOK()){
            Button viewClinicsBtn = (Button) view.findViewById(R.id.ViewClinicsBtnRemind);
            viewClinicsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.vaccine_recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        firebaseManager.retrieveVaccineLogWithReminder(new FirebaseManager.MyCallbackVaccineLog() {
            @Override
            public void onCallback(ArrayList<VaccineLogEntry> value) {
                vaccineLogEntries = value;
                adapter = new ReminderAdapter(getActivity(), vaccineLogEntries, uId);
                recyclerView.setAdapter(adapter);
            }
        }, uId);

        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    /**
     * On stop, it will stop getting updates from EventBus
     */
    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(CustomMessageEvent event) {
        Log.d("HOMEFRAG EB RECEIVER", "Username :\"" + event.getCustomMessage() + "\" Successfully Received!");
        uId = event.getCustomMessage();


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