package com.example.aavax.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aavax.R;


public class TravelPageFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "TravelFragment";

    //widgets
    //private Button AsiaBtn, EuropeBtn, NorthAmericaBtn, SouthAmericaBtn, OceaniaBtn, AfricaBtn;
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
        View view = inflater.inflate(R.layout.fragment_travel_page, container, false);

//        AsiaBtn = view.findViewById();
//        EuropeBtn = view.findViewById();
//        NorthAmericaBtn = view.findViewById();
//        SouthAmericaBtn = view.findViewById();
//        OceaniaBtn = view.findViewById();
//        AfricaBtn = view.findViewById();

//        AsiaBtn.setOnClickListener(this);
//        EuropeBtn.setOnClickListener(this);
//        NorthAmericaBtn.setOnClickListener(this);
//        SouthAmericaBtn.setOnClickListener(this);
//        OceaniaBtn.setOnClickListener(this);
//        AfricaBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {

//        switch (v.getId()){
//            case R.id.btn_asia:{
//                mIMainActivity.inflateFragment(getString(R.string.fragment_a), message);
//                break;
//            }
//
//            case R.id.btn_europe:{
//                break;
//            }
//
//            case R.id.btn_north_america:{
//                break;
//            }
//
//            case R.id.btn_south_america:{
//                break;
//            }
//
//            case R.id.btn_oceania:{
//                break;
//            }
//
//            case R.id.btn_africa:{
//                break;
//            }
//        }
    }
}