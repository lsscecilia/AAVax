package com.example.aavax.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import control.ProfileMgr;
import entity.ProfileMgrInterface;

public class NewProfileEntry extends AppCompatActivity {
    private ProfileMgrInterface profileMgr;
    private String uId;
    private String pId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        profileMgr = new ProfileMgr();

        final Button createProfile = findViewById(R.id.create_profile_button);
        final EditText firstNameText = findViewById(R.id.firstNameProfile);
        final EditText lastNameText = findViewById(R.id.lastNameProfile);
        final EditText dateOfBirthText = findViewById(R.id.DOBProfile);

        Toolbar toolbar = findViewById(R.id.create_profile_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        // back button
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed(); // Implemented by activity
        });

        // create account button
        createProfile.setOnClickListener(v -> {

            //get  input
            final String firstName = getInput(firstNameText);
            final String lastName = getInput(lastNameText);
            final String dob = getInput(dateOfBirthText);

            //get pid
            Bundle bundle = getIntent().getExtras();
            System.out.println("value in new profile buncle"+bundle.getString("num of side Profile"));

            if(bundle.getString("num of side Profile")!= null) {
                pId = bundle.getString("num of side Profile");
            }

            System.out.println("pid in new profile entry" + pId );

            //save to database
            profileMgr.addProfile(uId, firstName + " " + lastName, dob);


            //go to home page fragment
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ProfilePageFragment.class));
        finish();
    }

    public String getInput(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * On stop, it will stop getting updates from EventBus
     */
    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(CustomMessageEvent event) {
        Log.d("HOMEFRAG EB RECEIVER", "Username :\"" + event.getCustomMessage() + "\" Successfully Received!");
        uId = event.getCustomMessage();
    }



}
