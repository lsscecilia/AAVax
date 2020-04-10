package com.example.aavax.ui.homepage;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.DatePickerFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import control.VaccineLogMgr;
import entity.VaccineLogEntry;
import entity.VaccineLogMgrInterface;


/**
 * edit vaccine log entry
 * Called by: {@link MyVaccInfoFragment}
 * Calls: {@link VaccinationHistoryFragment}
 */
public class EditMyVaccInfoFragment extends Fragment {
    private String vaccineName;
    private String uId;
    private VaccineLogMgrInterface vaccineLogMgr;
    private String selectedDateTaken;
    private String selectedNextDue;
    private  Date dateTakenOriginal;
    private Date nextDueOriginal;
    private String reminderOriginal;
    private Date dateTaken=null;
    private Date nextDue=null;
    private String reminder=null;

    private static final int REQUEST_CODE = 22;
    private static final int REQUEST_CODE2 = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_my_vacc_info, container, false);
        final TextView vaccine = view.findViewById(R.id.vaccineName2);
        final Button dateTakenBtn = view.findViewById(R.id.dateTakenBtn);
        final Button nextDueBtn = view.findViewById(R.id.nextDueBtn);
        final EditText reminderBtn = view.findViewById(R.id.remindMeBtn);
        final Button confirmChanges = view.findViewById(R.id.confirmChangesBtn);
        final Button deleteEntry = view.findViewById(R.id.deleteEntryBtn);

        vaccineLogMgr = new VaccineLogMgr();

        //subscribe to event bus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //get vaccine name from bundle
        Bundle bundle = this.getArguments();
        vaccineName = bundle.getString("vaccineName");

        //set vaccine text
        vaccine.setText(vaccineName);

        //retrieve vaccine
        vaccineLogMgr.retrieveVaccineLog(value -> {
            for (VaccineLogEntry v: value)
            {
                System.out.println(v.getVaccine() + "taken on: " + v.getDateTaken());
                if (v.getVaccine().getName().compareTo(vaccineName)==0)
                {
                    System.out.println(v.getVaccine().getName() + "taken on: " + v.getDateTaken());
                    dateTakenBtn.setText(v.getDateTaken().getMonth()+"/"+v.getDateTaken().getDate()+"/"+v.getDateTaken().getYear());
                    dateTakenOriginal = v.getDateTaken();
                    nextDueOriginal = v.getNextDue();
                    if (v.getReminder()) {
                        nextDueBtn.setText(v.getNextDue().getMonth()+"/"+v.getDateTaken().getDate()+"/"+v.getDateTaken().getYear());
                        reminderBtn.setText("true");
                        reminderOriginal = "true";
                    }
                    else
                    {
                        nextDueBtn.setText("NA");
                        reminderBtn.setText("false");
                        reminderOriginal = "false";
                    }

                }
            }
        },uId );

        dateTakenBtn.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.setTargetFragment(EditMyVaccInfoFragment.this, REQUEST_CODE);
            datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
        });

        nextDueBtn.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.setTargetFragment(EditMyVaccInfoFragment.this, REQUEST_CODE2);
            datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
        });


        //dialog for reminder
        ListView listView = new ListView(this.getActivity());
        List<String> data = new ArrayList<>();
        data.add("true");
        data.add("false");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setView(listView);
        final AlertDialog dialog = builder.create();
        reminderBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //get input from user from dialog
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reminderBtn.setText(adapter.getItem(position));
                reminder = adapter.getItem(position);
                dialog.dismiss();
            }
        });

        confirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update database
                if (dateTaken==null)
                    dateTaken = dateTakenOriginal;

                if (nextDue==null)
                    nextDue = nextDueOriginal;

                if (reminder==null)
                    reminder = reminderOriginal;

                vaccineLogMgr.updateVaccineLogEntry(uId, vaccineName, dateTaken, nextDue, reminder);
                //go back to home page fragment
                Fragment fragment = new VaccinationHistoryFragment();
                doFragmentTransaction(fragment, getString(R.string.my_vaccines), false, uId);
            }
        });

        deleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete data
                vaccineLogMgr.deleteVaccineLogEntry(uId, vaccineName);

                //go back to home page fragment
                Fragment fragment = new VaccinationHistoryFragment();
                doFragmentTransaction(fragment, getString(R.string.my_vaccines), false, uId);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        final Button dateTakenBtn = getActivity().findViewById(R.id.dateTakenBtn);
        final Button nextDueBtn = getActivity().findViewById(R.id.nextDueBtn);
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedDateTaken = data.getStringExtra("selectedDate");
            dateTakenBtn.setText(selectedDateTaken);
            String[] dateSplit = selectedDateTaken.split("/");
            dateTaken = new Date(Integer.parseInt(dateSplit[2]),Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));
        }
        if (requestCode == REQUEST_CODE2 && resultCode == Activity.RESULT_OK)
        {
                selectedNextDue = data.getStringExtra("selectedDate");
                System.out.println(selectedNextDue + "next due selected date");
                nextDueBtn.setText(selectedNextDue);
                String[] dateSplit = selectedNextDue.split("/");
                nextDue = new Date(Integer.parseInt(dateSplit[2]),Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));

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
