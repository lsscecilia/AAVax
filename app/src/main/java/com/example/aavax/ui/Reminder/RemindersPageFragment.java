package com.example.aavax.ui.Reminder;

import android.content.Context;
import android.net.Uri;
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
import com.example.aavax.ui.IMainActivity;

import java.util.ArrayList;

import model.Vaccine;


public class RemindersPageFragment extends Fragment {

    private static final String TAG = "RemindersFragment";

    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    private ReminderVaccineAdapter adapter;
    private ArrayList<Vaccine> RemvaccineArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders_page, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        RemvaccineArrayList = new ArrayList<>();
        // initialise vaccines
        createListData();
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.vaccine_recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // add line after each vaccine row
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new ReminderVaccineAdapter(getActivity(), RemvaccineArrayList);
        recyclerView.setAdapter(adapter);
    }
    private void createListData() {
        Vaccine vac1 = new Vaccine("Hepatitis A", "detail 1");
        RemvaccineArrayList.add(vac1);
        Vaccine vac2 = new Vaccine("Measles", "detail 1");
        RemvaccineArrayList.add(vac2);
        Vaccine vac3 = new Vaccine("Rubella", "detail 1");
        RemvaccineArrayList.add(vac3);
        Vaccine vac4 = new Vaccine("Td Booster", "detail 1");
        RemvaccineArrayList.add(vac4);
        Vaccine vac5 = new Vaccine("Varicella", "detail 1");
        RemvaccineArrayList.add(vac5);
        Vaccine vac6 = new Vaccine("Malaria", "detail 1");
        RemvaccineArrayList.add(vac6);
        Vaccine vac7 = new Vaccine("Vaccine", "detail 1");
        RemvaccineArrayList.add(vac7);
        RemvaccineArrayList.add(vac7);
        RemvaccineArrayList.add(vac7);
    }
}