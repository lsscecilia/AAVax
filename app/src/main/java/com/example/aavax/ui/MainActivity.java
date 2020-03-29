package com.example.aavax.ui;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.*;
import com.example.aavax.R;
import com.example.aavax.ui.homepage.HomePageFragment;
import com.example.aavax.ui.reminder.RemindersPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String TAG = "MainActivity";

    private TextView mToolbarTitle;
    private String uId;
    private FirebaseManager firebaseManager;

    private String[] continents;
    private String[] countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        mToolbarTitle = findViewById(R.id.toolbar_title);

        /*
        firebaseManager =  new FirebaseManager();
        firebaseManager.updateVaccine();*/


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        //Bundle extras = intent.getExtras();
        //uId = savedInstanceState.getString("userId");
        String uId = getIntent().getExtras().getString("userId");
        System.out.println("userid here is:  "+ uId);
        if (savedInstanceState == null) {
            Fragment fragment = new HomePageFragment();
            doFragmentTransaction(fragment, getString(R.string.my_vaccines), false, uId);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String title = "";
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomePageFragment();
                            title = getString(R.string.my_vaccines);
                            break;
                        case R.id.navigation_travel:
                            selectedFragment = new TravelPageFragment();
                            title = getString(R.string.continents);
                            break;
                        case R.id.navigation_reminders:
                            selectedFragment = new RemindersPageFragment();
                            title = getString(R.string.title_reminders);
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfilePageFragment();
                            title = getString(R.string.title_profile);
                            break;
                    }
                    doFragmentTransaction(selectedFragment, title, true, uId);

                    return true;
                }
            };

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!message.equals("")){
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.intent_message), message);
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragment_container, fragment, tag);
        if(addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void setToolbarTitle(String fragmentTag) {
        mToolbarTitle.setText(fragmentTag);
    }

    @Override
    public void inflateFragment(String fragmentTag, String message) {
        continents = getResources().getStringArray(R.array.continents);
        countries = getResources().getStringArray(R.array.all_countries);
        if (Arrays.asList(continents).contains(fragmentTag)){
            TravelCountriesFragment fragment = new TravelCountriesFragment();
            doFragmentTransaction(fragment, fragmentTag, true, message);
        }
        else if (Arrays.asList(countries).contains(fragmentTag)){
            TravelVaccinesFragment fragment = new TravelVaccinesFragment();
            doFragmentTransaction(fragment, fragmentTag, true, message);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println(getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
            getSupportFragmentManager().beginTransaction().commit();
        }
    }


    /**
     * On fragment start, it will register for EventBus, a subscription Mechanism
     */
    @Override
    public void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
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
        //DisplayName.setText(usernameImported);

    }

}