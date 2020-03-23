package com.example.aavax.ui.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.aavax.R;

//TODO: display correct vacc info
//might have to pass the vaccine log entry in vaccineholder when instantiating new fragment
public class MyVaccInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_vacc_info, container, false);
    }
}
