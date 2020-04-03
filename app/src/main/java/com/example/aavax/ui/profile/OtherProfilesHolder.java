package com.example.aavax.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.FirebaseManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import entity.Profile;

public class OtherProfilesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView profileName;
    private String pId;
    private FirebaseManager firebaseManager;
    private HashMap<String, String> profiles;
    private String uId;

    public OtherProfilesHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        profileName = itemView.findViewById(R.id.profile_name);
        firebaseManager = new FirebaseManager();
    }

    public void setDetails(String profile, int position, String uId) {
        System.out.println("SET PROFILE TEXT IS WHEN");
        System.out.println("profile name in holder" + profile);
        profileName.setText(profile);
        this.uId = uId;

        /*
        firebaseManager.retrieveSubprofileNameAndID(new FirebaseManager.MyCallbackHashMap() {
            @Override
            public void onCallback(HashMap<String, String> value) {
                profiles = value;
                Iterator hm = profiles.entrySet().iterator();
                String profile="", profileId="";
                for (int i=0; i<position+1; i++)
                {
                    Map.Entry mapElement = (Map.Entry)hm.next();
                    if (i==position)
                    {
                        profile = (String) mapElement.getKey();
                        pId = ((String) mapElement.getValue());
                    }


                }
                profileName.setText(profile);
            }
        }, uId);*/

    }

    public void setProfileId(String id){
        pId = id;
    }

    @Override
    public void onClick(View itemView) {
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();

        Bundle bundle = new Bundle();
        bundle.putString("pId", pId);

        Fragment myFragment = new EditProfileFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

        myFragment.setArguments(bundle);


        /*
        //click to switch profile then go back to profilepagefragment
        firebaseManager.changeProfile(uId, pId);
        Fragment myFragment = new ProfilePageFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
         */

        //edit profile fragment


    }
}
