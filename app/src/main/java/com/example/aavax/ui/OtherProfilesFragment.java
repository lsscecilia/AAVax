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
import com.example.aavax.ui.OtherProfilesAdapter;
import com.example.aavax.R;
import model.Profile;
import model.VaccineLogEntry;


import java.util.ArrayList;
import java.util.Date;

public class OtherProfilesFragment extends Fragment{

    private static final String TAG = "Other_Profiles";

    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    private OtherProfilesAdapter adapter;
    private ArrayList<Profile> profileArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_profiles_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        profileArrayList = new ArrayList<>();
        // initialise profiles
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
        recyclerView = getView().findViewById(R.id.profile_name_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // add line after each vaccine row
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new OtherProfilesAdapter(getActivity(), profileArrayList);
        recyclerView.setAdapter(adapter);
    }

    private void createListData() {
        Profile profile1 = new Profile("James cougar","23-4-1996");
        profileArrayList.add(profile1);
        Profile profile2 = new Profile("laura cougar", "03-05-2996");
        profileArrayList.add(profile2);

    }

}
