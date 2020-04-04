package com.example.aavax.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aavax.R;
import com.example.aavax.ui.homepage.HomePageFragment;
import com.example.aavax.ui.login.LoginActivity;
import com.example.aavax.ui.homepage.VaccineDetailFragment;
import com.example.aavax.ui.maps.MapViewFragment;
import com.example.aavax.ui.profile.ProfilePageFragment;
import com.example.aavax.ui.reminder.RemindersPageFragment;
import com.example.aavax.ui.settings.SettingsActivity;
import com.example.aavax.ui.travel.TravelCountriesFragment;
import com.example.aavax.ui.travel.TravelPageFragment;
import com.example.aavax.ui.travel.TravelVaccinesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.widget.Toolbar;
//import 	androidx.appcompat.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import entity.FirebaseInterface;

public class MainActivity extends AppCompatActivity implements IMainActivity , NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";

    private DrawerLayout drawer;
    private TextView mToolbarTitle;
    private String uId;
    private FirebaseInterface firebaseManager;
    private Menu menu;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    private String[] continents;
    private String[] countries;
    private String[] vaccines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        firebaseManager = new FirebaseManager();
        Resources res = getResources();

        //top toolbar
        mToolbarTitle = findViewById(R.id.toolbar_title);

        //side bar
        Toolbar toolbar = findViewById(R.id.toolbar_side);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        //create menu dynamically
        final NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        menu = navView.getMenu();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");



        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        //set the current profile and email when drawer is open
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        TextView email = findViewById(R.id.emailaddress);
                        email.setText(dataSnapshot.child(uId).child("email").getValue(String.class));
                        if (dataSnapshot.child(uId).child("profiles").getChildrenCount()!=1)
                        {
                            for (DataSnapshot data: dataSnapshot.child(uId).child("profiles").getChildren())
                            {
                                if (!data.child("thisProfile").getValue(boolean.class))
                                {

                                    if (menu.findItem(Integer.parseInt(data.getKey()))==null)
                                    {
                                        menu.add(R.id.profile_group,Integer.parseInt(data.getKey()), 0, data.child("name").getValue(String.class));

                                    }
                                    else
                                    {
                                        menu.removeItem(Integer.parseInt(data.getKey()));
                                        menu.add(R.id.profile_group,Integer.parseInt(data.getKey()), 0, data.child("name").getValue(String.class));
                                    }

                                    TextView name = findViewById(R.id.profilename);
                                    name.setText(data.child("name").getValue(String.class));

                                }
                                if (data.child("thisProfile").getValue(boolean.class))
                                {
                                    TextView name = findViewById(R.id.profilename);
                                    name.setText(data.child("name").getValue(String.class));
                                }

                            }
                        }

                        //menu.add("new profile");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //get name that are for this profile
                firebaseManager.retrieveCurrentProfileName(new FirebaseManager.MyCallbackString() {
                    @Override
                    public void onCallback(String value) {
                        TextView name = findViewById(R.id.profilename);
                        name.setText(value);
                    }
                }, uId);

                //get email that is for this acount
                firebaseManager.retrieveEmailAdress(new FirebaseManager.MyCallbackString() {
                    @Override
                    public void onCallback(String value) {
                        TextView email = findViewById(R.id.emailaddress);
                        email.setText(value);
                    }
                }, uId);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device


        if (savedInstanceState == null) {
            Fragment fragment = new HomePageFragment();
            doFragmentTransaction(fragment, getString(R.string.my_vaccines), true, "");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        menu = navView.getMenu();
        Fragment selectedFragment = null;
        String title = "";
        System.out.println("lol whats happening here");
        if (item.getItemId()== R.id.setting)
        {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, SettingsActivity.class));
        }
        else if (item.getItemId()==R.id.signout)
        {
            System.out.println("no activity change lol");

            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        else
        {
            System.out.println("remove what itemId" + item.getItemId());
            menu.removeItem(item.getItemId());

            System.out.println("CHANGE PROFILE HEREEEEEE");
            firebaseManager.changeProfile(uId, Integer.toString(item.getItemId())); //change here

            drawer.closeDrawer(GravityCompat.START);
            /*
            selectedFragment = new HomePageFragment();
            title = "switch profile";
            drawer.closeDrawer(GravityCompat.START);
            doFragmentTransaction(selectedFragment, title, false, "");*/


        }



        return true;
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
                    doFragmentTransaction(selectedFragment, title, true, "");

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
        vaccines = getResources().getStringArray(R.array.vaccines);
        if (Arrays.asList(continents).contains(fragmentTag)){
            TravelCountriesFragment fragment = new TravelCountriesFragment();
            doFragmentTransaction(fragment, fragmentTag, true, message);
        }
        else if (Arrays.asList(countries).contains(fragmentTag)){
            TravelVaccinesFragment fragment = new TravelVaccinesFragment();
            doFragmentTransaction(fragment, fragmentTag, true, message);

        }
        else if (fragmentTag.equals("Nearby Clinics")){
            MapViewFragment fragment = new MapViewFragment();
            doFragmentTransaction(fragment, fragmentTag, true, message);
        }

        else if (Arrays.asList(vaccines).contains(fragmentTag)){
            VaccineDetailFragment fragment = new VaccineDetailFragment();
            doFragmentTransaction(fragment, fragmentTag, true, message);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println(getSupportFragmentManager().getBackStackEntryCount() + "back stack count");
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
            getSupportFragmentManager().beginTransaction().commit();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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