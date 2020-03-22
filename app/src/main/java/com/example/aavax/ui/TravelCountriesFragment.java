package com.example.aavax.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aavax.R;


import java.util.ArrayList;

import Entity.Country;


public class TravelCountriesFragment extends Fragment {

    private static final String TAG = "TravelCountriesFragment";

    //widgets
    ListView countriesListView;
    //ArrayList<Country> countries;
    String[] countries;

    //vars
    private IMainActivity mIMainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_countries, container, false);

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        countriesListView = (ListView) getView().findViewById(R.id.countriesListView);
        Resources res = getResources();
        countries = res.getStringArray(R.array.countries);

        countriesListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.countries_listview_detail, countries));

    }
}