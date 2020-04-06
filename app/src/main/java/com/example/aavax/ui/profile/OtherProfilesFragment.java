package com.example.aavax.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.IMainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;


import control.ProfileMgr;
import entity.Profile;
import entity.ProfileMgrInterface;

public class OtherProfilesFragment extends Fragment {
    private static final String TAG = "Other_Profiles";

    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    private OtherProfilesAdapter adapter;
    private ArrayList<Profile> profileArrayList;
    private String uId;
    private ProfileMgrInterface profileMgr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profiles, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        profileArrayList = new ArrayList<>();
        profileMgr = new ProfileMgr();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        recyclerView = getView().findViewById(R.id.profile_name_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        //get hash map
        Bundle bundle = this.getArguments();
        HashMap<String, String> hashMap;
        if (bundle != null) {
            hashMap = (HashMap<String, String>) bundle.getSerializable("hashMap");
        }
        else
            hashMap= new HashMap<>();

        final ImageButton addProfileButton = view.findViewById(R.id.addProfileBtn);
        addProfileButton.setOnClickListener(v -> {


            Intent intent = new Intent(addProfileButton.getContext(), NewProfileEntry.class);
            intent.putExtra("num of side Profile", Integer.toString(hashMap.size()+1));
            startActivity(intent);
        });

        //get names that are sub profiles
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new OtherProfilesAdapter(getActivity(), uId, hashMap);
        recyclerView.setAdapter(adapter);


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
