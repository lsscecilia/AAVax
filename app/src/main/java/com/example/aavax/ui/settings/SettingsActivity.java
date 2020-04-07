package com.example.aavax.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.aavax.R;
import com.example.aavax.ui.MainActivity;


public class SettingsActivity extends AppCompatActivity {



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

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
