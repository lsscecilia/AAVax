package com.example.aavax.ui.travel;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aavax.R;
import com.example.aavax.ui.IMainActivity;

/**
 * Shows the list of continents available for travel
 * Called by: {@link com.example.aavax.ui.MainActivity}
 * Calls: {@link com.example.aavax.ui.travel.TravelCountriesFragment}
 */
public class TravelPageFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "TravelFragment";

    //widgets
    private ImageView AsiaImg, EuropeImg, NorthAmericaImg, SouthAmericaImg, OceaniaImg, AfricaImg;
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

        AsiaImg = view.findViewById(R.id.AsiaImg);
        EuropeImg = view.findViewById(R.id.EuropeImg);
        NorthAmericaImg = view.findViewById(R.id.NorthAmericaImg);
        SouthAmericaImg = view.findViewById(R.id.SouthAmericaImg);
        OceaniaImg = view.findViewById(R.id.OceaniaImg);
        AfricaImg = view.findViewById(R.id.AfricaImg);

        AsiaImg.setOnClickListener(this);
        EuropeImg.setOnClickListener(this);
        NorthAmericaImg.setOnClickListener(this);
        SouthAmericaImg.setOnClickListener(this);
        OceaniaImg.setOnClickListener(this);
        AfricaImg.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {

        mIMainActivity.inflateFragment(String.valueOf(v.getContentDescription()), String.valueOf(v.getContentDescription()));

    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        mIMainActivity.setToolbarTitle(getTag());
    }
}