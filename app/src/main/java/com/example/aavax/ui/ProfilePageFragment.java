
package com.example.aavax.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aavax.R;
import com.example.aavax.ui.login.LoginActivity;
//import com.example.aavax.ui.ProfileRVAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;

//import model.ProfileRV;


public class ProfilePageFragment extends Fragment  {

    private static final String TAG = "ProfileFragment";
    //widgets

    //vars
    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    //private ProfileRVAdapter adapter;
    //private ArrayList<ProfileRV> profileRVArrayList;
    private ImageView editNameButton;
    private AlertDialog dialog;
    private EditText editText;
    private TextView usersName;
    private TextView otherProfiles;
    private TextView aboutUs;
    private TextView sign_out;
    private FirebaseManager firebaseManager;
    private String uId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mIMainActivity.setToolbarTitle(getTag());
        firebaseManager = new FirebaseManager();
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //profileRVArrayList = new ArrayList<>();

        //createListData();
        usersName = view.findViewById(R.id.users_name);
        //transfer username
        firebaseManager.retrieveCurrentProfileName(new FirebaseManager.MyCallbackString() {
            @Override
            public void onCallback(String value) {
                usersName.setText(value);
            }
        }, uId);


        dialog = new AlertDialog.Builder(getActivity()).create();
        editText = new EditText(getActivity());

        dialog.setTitle("Edit name");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE CHANGES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                usersName.setText(editText.getText());
            }
        });

        //not linked to database
        editNameButton = view.findViewById(R.id.editNameButton);
        editNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText());
                dialog.show();
            }
        });

        
        otherProfiles = view.findViewById(R.id.other_profiles_button);
        //onClickListener for other profiles

        aboutUs = view.findViewById(R.id.about_us_button);
        //onClickListen for about us

        sign_out = view.findViewById(R.id.sign_out_button);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
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