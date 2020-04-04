package com.example.aavax.ui.homepage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aavax.R;
import com.example.aavax.ui.FirebaseManager;
import com.example.aavax.ui.IMainActivity;

import entity.FirebaseInterface;
import entity.Vaccine;

public class VaccineDetailFragment extends Fragment {

    private IMainActivity mIMainActivity;
    private String vaccineName;
    private String vaccineDetail;
    private FirebaseInterface firebaseManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_vaccine_detail, container, false);

        firebaseManager = new FirebaseManager();
        final TextView vaccineNameText = view.findViewById(R.id.vaccineName3);
        final TextView vaccineDetailText = view.findViewById(R.id.vacccineDetailText);
        Bundle bundle = this.getArguments();

        vaccineName = bundle.getString(getString(R.string.intent_message));
        vaccineNameText.setText(vaccineName);
        mIMainActivity.setToolbarTitle("Vaccine Information");

        firebaseManager.retrieveVaccines(value -> {
            for (Vaccine v: value)
            {
                if (v.getName().compareTo(vaccineName)==0)
                {
                    vaccineDetailText.setText(v.getDetail());
                    break;
                }
            }
        });
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

}
