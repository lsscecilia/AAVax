package com.example.aavax.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.FirebaseManager;
import com.example.aavax.ui.IMainActivity;
import com.example.aavax.ui.login.LoginActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DeleteAccountFragment extends Fragment {
    private static final String TAG = "Change password";
    private IMainActivity mIMainActivity;
    private FirebaseManager firebaseManager;
    private String uId;
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseManager  = new FirebaseManager();

        //subscribe to event bus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_account, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        //final Toolbar toolbarTitile = view.findViewById(R.id.settings_toolbar);
        //toolbarTitile.setTitle("Change password");

        final Button cancelBtn = view.findViewById(R.id.cancelDeleteAccBtn);
        final Button deleteAccBtn = view.findViewById(R.id.confirmDeleteAccBtn);
        final EditText passwordEditText = view.findViewById(R.id.passwordDeleteAcc);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        deleteAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read input
                String password = getInput(passwordEditText);
                deleteAcc(password);

            }
        });



        return view;
    }


    private void deleteAcc(String pw)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        View view = getView();
        final EditText passwordEditText =  view.findViewById(R.id.passwordDeleteAcc);

        firebaseManager.retrieveEmailAdress(new FirebaseManager.MyCallbackString() {
            @Override
            public void onCallback(String value) {
                email = value;
                AuthCredential credential = EmailAuthProvider.getCredential(email, pw);
                user.reauthenticate(credential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                user.delete();

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "old password is incorrect");
                                passwordEditText.setError("incorrect password entered");

                            }
                        });
            }

        }, uId);
    }


    public String getInput(EditText editText){
        return editText.getText().toString().trim();
    }


    @Override
    public void onStart(){
        super.onStart();
        //EventBus.getDefault().register(this);
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
