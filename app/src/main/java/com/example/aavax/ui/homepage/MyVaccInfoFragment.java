package com.example.aavax.ui.homepage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.FirebaseManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import entity.FirebaseInterface;
import entity.VaccineLogEntry;

public class MyVaccInfoFragment extends Fragment {

    private FirebaseInterface firebaseManager;
    private String vaccineName;
    private ArrayList<VaccineLogEntry> vaccineArrayList;
    private String uId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        firebaseManager = new FirebaseManager();
        View view = inflater.inflate(R.layout.fragment_my_vacc_info, container, false);

        //get vaccine name from bundle
        Bundle bundle = this.getArguments();
        vaccineName = bundle.getString("vaccineName");

        TextView vaccine = view.findViewById(R.id.vaccineName);
        final TextView dateTaken = view.findViewById(R.id.dateTakenFillText);
        final TextView nextDue = view.findViewById(R.id.nextDueFillText);
        final TextView remindMe = view.findViewById(R.id.remindMeFillText);
        final Button editBtn = view.findViewById(R.id.editBtn);
        final Button viewVaccineDetail = view.findViewById(R.id.moreInfoText);

        viewVaccineDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //switch fragment
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new VaccineDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.intent_message),vaccineName);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });


        vaccine.setText(vaccineName);

        //retrieve vaccine
        firebaseManager.retrieveVaccineLog(new FirebaseManager.MyCallbackVaccineLog() {
            @Override
            public void onCallback(ArrayList<VaccineLogEntry> value) {
                for (VaccineLogEntry v: value)
                {
                    if (v.getVaccine().getName().compareTo(vaccineName)==0)
                    {
                        dateTaken.setText(v.getDateTaken().getMonth()+"/"+v.getDateTaken().getDate()+"/"+v.getDateTaken().getYear());
                        if (v.getReminder())
                        {
                            remindMe.setText("true");
                            nextDue.setText(v.getNextDue().getMonth()+"/"+v.getNextDue().getDate()+"/"+v.getNextDue().getYear());
                        }

                        else
                        {
                            remindMe.setText("false");
                            nextDue.setText("NA");
                        }
                    }
                }
            }
        },uId );

        editBtn.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment myFragment = new EditMyVaccInfoFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString("uId", uId);
            bundle1.putString("vaccineName",vaccineName);
            myFragment.setArguments(bundle1);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
        });

        return view;
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
}
