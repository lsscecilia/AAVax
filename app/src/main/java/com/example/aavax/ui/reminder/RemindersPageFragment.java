package com.example.aavax.ui.reminder;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aavax.R;
import com.example.aavax.ui.FirebaseManager;
import com.example.aavax.ui.IMainActivity;

import java.util.ArrayList;

import model.VaccineLogEntry;


public class RemindersPageFragment extends Fragment {

    private static final String TAG = "RemindersFragment";

    private IMainActivity mIMainActivity;

    private RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private ArrayList<VaccineLogEntry> vaccineLogEntries;
    private String uId;
    private FirebaseManager firebaseManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders_page, container, false);
        firebaseManager = new FirebaseManager();
        super.onCreateView(inflater, container, savedInstanceState);
        vaccineLogEntries = new ArrayList<>();

        Bundle bundle = this.getArguments();
        uId = bundle.getString("Intent");
        // initialise vaccines
       // createListData();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.vaccine_recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // add line after each vaccine row
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
}