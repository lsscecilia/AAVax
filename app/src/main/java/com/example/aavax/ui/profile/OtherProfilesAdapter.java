package com.example.aavax.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.FirebaseManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import entity.Profile;

public class OtherProfilesAdapter extends RecyclerView.Adapter<OtherProfilesHolder> {

    private Context context;
    private String uId;
    private HashMap<String, String> profiles;
    //private FirebaseManager firebaseManager;

    public OtherProfilesAdapter(Context context, String uId, HashMap<String, String> hashMap) {
        this.context = context;
        this.uId = uId;
        //this.profiles = profiles;
        /*
        firebaseManager = new FirebaseManager();
        firebaseManager.retrieveSubprofileNameAndID(new FirebaseManager.MyCallbackHashMap() {
            @Override
            public void onCallback(HashMap<String, String> value) {

                profiles = value;
            }
        }, uId);*/
        this.profiles= hashMap;

    }


    //here how to return item count only aft the shit is retrieve LOL
    @Override
    public int getItemCount() {
        /*
        firebaseManager.retrieveSubprofileNameAndID(new FirebaseManager.MyCallbackHashMap() {
            @Override
            public void onCallback(HashMap<String, String> value) {
                profiles = value;
            }
        }, uId);*/
        return profiles.size();
    }

    @NonNull
    @Override
    public OtherProfilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_row, parent, false);
        return new OtherProfilesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherProfilesHolder holder, int position) {
        Iterator hm = profiles.entrySet().iterator();
        String profile="", profileId="";
        for (int i=0; i<position+1; i++)
        {
            Map.Entry mapElement = (Map.Entry)hm.next();
            if (i==position)
            {
                profile = (String) mapElement.getKey();
                profileId = ((String) mapElement.getValue());
            }


        }

        if (profile!=null)
        {
            System.out.println("IN adapter, profile name yohz" + profile);
            holder.setDetails(profile, position, uId);
            holder.setProfileId(profileId);
        }

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
                        profileId = ((String) mapElement.getValue());
                    }


                }
                holder.setDetails(profile, position, uId);
                //holder.setProfileId(profileId);

            }
        }, uId);*/
    }
}
