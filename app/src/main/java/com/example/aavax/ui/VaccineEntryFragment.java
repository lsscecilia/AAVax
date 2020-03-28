package com.example.aavax.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.icu.text.SymbolTable;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.aavax.R;
import com.google.firebase.database.DatabaseReference;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Vaccine;

public class VaccineEntryFragment extends Fragment {

    //private IMainActivity mIMainActivity;
    private static final String TAG = "addVaccine";
    private FragmentActivity myContext;
    public static final int REQUEST_CODE = 11;
    String selectedDate;
    private FirebaseManager firebaseManager;
    private List<String> vaccines;
    private Date date;
    private String vaccineChoosen;
    private String uId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //mIMainActivity.setToolbarTitle(getTag());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vaccine_entry, container, false);

        final Button lastTakenDateBtn = view.findViewById(R.id.chooseDateBtn);
        lastTakenDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(VaccineEntryFragment.this, REQUEST_CODE);
                datePicker.show(myContext.getSupportFragmentManager(), "date picker");
                //lastTakenDateBtn.setText(selectedDate);
            }
        });

        //get vaccines from database
        firebaseManager = new FirebaseManager();
        //vaccines = firebaseManager.retrieveVaccinesName();
        ListView listView = new ListView(this.getActivity());
        List<String> data = new ArrayList<>();

        data.add("Hepatitis A");
        data.add("Influenza (Flu)");
        data.add("Measles");

        /*
        for (String s: vaccines)
        {
            System.out.println(s);
        }*/

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setView(listView);
        final AlertDialog dialog = builder.create();
        final EditText editVaccine = view.findViewById(R.id.editVaccine);
        editVaccine.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editVaccine.setText(adapter.getItem(position));
                vaccineChoosen = adapter.getItem(position);
                dialog.dismiss();
            }
        });


        final Button addEntry = view.findViewById(R.id.addVaccineBtn);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save data
                firebaseManager.addVaccineLogEntry(uId, date, vaccineChoosen);
                //go back to HomePageFragment
                Fragment fragment = new HomePageFragment();
                doFragmentTransaction(fragment, getString(R.string.my_vaccines), false, "");

            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        final Button lastTakenDateBtn = myContext.findViewById(R.id.chooseDateBtn);
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedDate = data.getStringExtra("selectedDate");
            lastTakenDateBtn.setText(selectedDate);
            //dateChosen = selectedDate;
            //System.out.println(dateChosen);
            String[] dateSplit = selectedDate.split("/");
            //String dateString = dateSplit[2]+'-'+dateSplit[0]+'-'+dateSplit[1];
            date = new Date(Integer.parseInt(dateSplit[2]),Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));
            // set the value of the editText
            //dateOfBirthET.setText(selectedDate);
        }
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = myContext.getSupportFragmentManager().beginTransaction();
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
        //DisplayName.setText(usernameImported);

    }

}
