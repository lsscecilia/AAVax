package com.example.aavax.ui.homepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.DatePickerFragment;
import com.example.aavax.ui.IMainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import control.VaccineLogMgr;
import entity.Vaccine;
import entity.VaccineLogMgrInterface;

public class VaccineEntryFragment extends Fragment {

    private IMainActivity mIMainActivity;
    private static final String TAG = "Add Vaccine";
    private String selectedDate;
    private VaccineLogMgrInterface vaccineLogMgr;
    private List<Vaccine> vaccines;
    private Date date;
    private String vaccineChoosen;
    private String uId;

    public static final int REQUEST_CODE = 11;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mIMainActivity.setToolbarTitle(getTag());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vaccine_entry, container, false);

        final Button lastTakenDateBtn = view.findViewById(R.id.chooseDateBtn);
        lastTakenDateBtn.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.setTargetFragment(VaccineEntryFragment.this, REQUEST_CODE);
            datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
        });

        //get vaccines from database
        final List<String> data = new ArrayList<>();
        vaccineLogMgr = new VaccineLogMgr();
        vaccineLogMgr.retrieveVaccines(value -> {
            for (Vaccine v: value)
            {
                data.add(v.getName());
            }
        });

        //put vaccine in adapter
        ListView listView = new ListView(this.getActivity());
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setView(listView);
        final AlertDialog dialog = builder.create();
        final EditText editVaccine = view.findViewById(R.id.editVaccine);

        editVaccine.setOnClickListener(v -> dialog.show());

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            editVaccine.setText(adapter.getItem(position));
            vaccineChoosen = adapter.getItem(position);
            dialog.dismiss();
        });


        final Button addEntry = view.findViewById(R.id.addVaccineBtn);
        addEntry.setOnClickListener(v -> {
            //save data
            vaccineLogMgr.addVaccineLogEntry(uId, date, vaccineChoosen);
            //go back to HomePageFragment
            Fragment fragment = new HomePageFragment();
            doFragmentTransaction(fragment, getString(R.string.my_vaccines), false, uId);

        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        final Button lastTakenDateBtn = getActivity().findViewById(R.id.chooseDateBtn);
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedDate = data.getStringExtra("selectedDate");
            lastTakenDateBtn.setText(selectedDate);
            String[] dateSplit = selectedDate.split("/");
            date = new Date(Integer.parseInt(dateSplit[2]),Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));
        }
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(!message.equals("")){
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.intent_message), message);
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragment_container, fragment, tag);
        if(addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    /**
     * On fragment start, it will register for EventBus, a subscription Mechanism
     */
    @Override
    public void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
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
