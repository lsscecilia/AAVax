package com.example.aavax.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<String> settingsArrayList;
    private SettingsAdapter adapter;
    private String uId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


        //initialise recycler view
        recyclerView = view.findViewById(R.id.settings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        settingsArrayList = new ArrayList<>();
        settingsArrayList.add("Change password");
        settingsArrayList.add("Delete account");
        adapter = new SettingsAdapter(getActivity(), uId, settingsArrayList);
        recyclerView.setAdapter(adapter);


        return view;
    }


    /**
     * On fragment start, it will register for EventBus, a subscription Mechanism
     */
    @Override
    public void onStart(){
        super.onStart();
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
