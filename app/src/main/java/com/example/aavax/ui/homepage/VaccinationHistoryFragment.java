package com.example.aavax.ui.homepage;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.aavax.R;

import control.VaccineLogMgr;
import entity.Vaccine;
import entity.VaccineLogMgrInterface;

import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.IMainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Show a list that consist of all of user vaccination history
 * Called by: {@link com.example.aavax.ui.MainActivity}
 * Calls: {@link MyVaccInfoFragment in VaccineHolder when a particular vaccine is chosen}, {@link VaccineEntryFragment when user request to enter new vaccine entry}
 */
public class VaccinationHistoryFragment extends Fragment {

    private static final String TAG = "My Vaccinations";

    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    private VaccineAdapter adapter;
    private ArrayList<Vaccine> vaccineArrayList;
    private String uId;
    private VaccineLogMgrInterface vaccineLogMgr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(TAG);
        vaccineLogMgr = new VaccineLogMgr();

        //subscribe to eventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        vaccineArrayList = new ArrayList<>();
        final ImageButton addVaccineButton = view.findViewById(R.id.add_vaccine_button);


        //when add vaccine button is clicked
        addVaccineButton.setOnClickListener(v -> {
            Fragment fragment = new VaccineEntryFragment();
            doFragmentTransaction(fragment, getString(R.string.my_vaccines), true, "");
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //initialise recycler view
        recyclerView = getView().findViewById(R.id.vaccine_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // add line after each vaccine row
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        //retrieve user vaccine log
        vaccineLogMgr.retrieveUserVaccine(value -> {
            vaccineArrayList = value;
            adapter = new VaccineAdapter(getActivity(), vaccineArrayList, uId);
            recyclerView.setAdapter(adapter);
        }, uId);

    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
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

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        mIMainActivity.setToolbarTitle(TAG);
    }

}