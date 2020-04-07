package com.example.aavax.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.IMainActivity;
import com.example.aavax.ui.homepage.VaccinationHistoryFragment;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import control.ProfileMgr;
import entity.ProfileMgrInterface;


public class EditProfileFragment extends Fragment {

    private static final String TAG = "Edit Profile";
    private ProfileMgrInterface profileMgr;
    private String uId;
    private String pId;
    private Menu menu;

    private IMainActivity mIMainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(TAG);
        profileMgr = new ProfileMgr();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        super.onCreateView(inflater, container, savedInstanceState);


        final Button editProfile = view.findViewById(R.id.editConfirmChangeButton);
        final Button deleteProfile = view.findViewById(R.id.deleteProfileButton);
        final EditText editFirstNameText = view.findViewById(R.id.editFirstNameProfile);
        final EditText editLastNameText = view.findViewById(R.id.editLastNameProfile);
        final EditText editDateOfBirthText = view.findViewById(R.id.editDOBProfile);

        //get pid
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            pId = bundle.getString("pId");
        }
        else
            System.out.println("error in getting pid in edit profile fragment");


        //show profile details
        profileMgr.retrieveProfile((name, dob) -> {

            if (name.indexOf(" ")!=-1)
            {
                int index = name.indexOf(" ");
                int lastIndex = name.length();
                if (name!=null)
                {
                    String firstName = name.substring(0,index);
                    String lastName = name.substring(index+1, lastIndex);
                    editFirstNameText.setText(firstName);
                    editLastNameText.setText(lastName);
                    editDateOfBirthText.setText(dob);
                }
            }
            else
            {
                editFirstNameText.setText(name);
                editLastNameText.setText("");
                editDateOfBirthText.setText(dob);
            }

        }, uId, pId);

        //edit profile
        editProfile.setOnClickListener(v -> {
            NavigationView navView = getActivity().findViewById(R.id.nav_view);
            menu = navView.getMenu();

            //get user input
            final String firstNameInput = getInput(editFirstNameText);
            final String lastNameInput = getInput(editLastNameText);
            final String dobInput = getInput(editDateOfBirthText);

            //save to database
            profileMgr.editProfile(uId, pId, firstNameInput+" "+lastNameInput, dobInput);

            //switch profile to this current one
            profileMgr.changeProfile(uId,pId);

            //remove from menu bar?
            menu.removeItem(Integer.parseInt(pId));

            //go back to the home page fragment
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment myFragment = new VaccinationHistoryFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
        });


        deleteProfile.setOnClickListener(v -> {
            NavigationView navView = getActivity().findViewById(R.id.nav_view);
            menu = navView.getMenu();


            //delete from database
            profileMgr.deleteProfile(uId,pId);

            //remove from menu
            menu.removeItem(Integer.parseInt(pId));

            //go back to profile page fragment --> try
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment myFragment = new ProfilePageFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
        });


        return view;
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
