package com.example.aavax.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.IMainActivity;
import com.example.aavax.ui.MainActivity;
import com.example.aavax.ui.homepage.HomePageFragment;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> settingsArrayList;
    private SettingsAdapter adapter;
    private String uId;

    private TextView mToolbarTitle;


   // private VaccineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);


        //top toolbar
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);


        if (savedInstanceState == null) {
            Fragment myFragment = new SettingFragment();
             this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_settings, myFragment).addToBackStack(null).commit();
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
