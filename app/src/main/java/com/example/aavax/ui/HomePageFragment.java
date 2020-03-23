package com.example.aavax.ui;

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
import model.Vaccine;
import com.example.aavax.ui.homepage.VaccineAdapter;
import java.util.ArrayList;

public class HomePageFragment extends Fragment {

    private static final String TAG = "MyVaccinations";

    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    private VaccineAdapter adapter;
    private ArrayList<Vaccine> vaccineArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        vaccineArrayList = new ArrayList<>();
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
        recyclerView = getView().findViewById(R.id.vaccine_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // add line after each vaccine row
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new VaccineAdapter(getActivity(), vaccineArrayList);
        recyclerView.setAdapter(adapter);
    }

    //TODO: call profile method to get list of vaccine log entries
    private void createListData() {
        Vaccine vac1 = new Vaccine("Hepatitis A", "detail 1");
        vaccineArrayList.add(vac1);
        Vaccine vac2 = new Vaccine("Measles", "detail 1");
        vaccineArrayList.add(vac2);
        Vaccine vac3 = new Vaccine("Rubella", "detail 1");
        vaccineArrayList.add(vac3);
        Vaccine vac4 = new Vaccine("Td Booster", "detail 1");
        vaccineArrayList.add(vac4);
        Vaccine vac5 = new Vaccine("Varicella", "detail 1");
        vaccineArrayList.add(vac5);
        Vaccine vac6 = new Vaccine("Malaria", "detail 1");
        vaccineArrayList.add(vac6);
        Vaccine vac7 = new Vaccine("Vaccine", "detail 1");
        vaccineArrayList.add(vac7);
        vaccineArrayList.add(vac7);
        vaccineArrayList.add(vac7);
    }
}