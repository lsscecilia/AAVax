package com.example.aavax.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.aavax.ui.IMainActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import control.AccountMgr;
import entity.AccountMgrInterface;


public class ChangePasswordFragment extends Fragment {

    private static final String TAG = "Change password";
    private IMainActivity mIMainActivity;
    private AccountMgrInterface accountMgr;
    private String uId;
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountMgr = new AccountMgr();

        //subscribe to event bus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        super.onCreateView(inflater, container, savedInstanceState);


        final Button changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        final EditText oldPwEditText = view.findViewById(R.id.oldPassword);
        final EditText newPwEditText = view.findViewById(R.id.newPassword);
        final EditText cfmNewPwEditText = view.findViewById(R.id.confirmNewPassword);


        changePasswordBtn.setOnClickListener(v -> {
            //read input
            final String oldPassword = getInput(oldPwEditText);
            final String newPassword = getInput(newPwEditText);
            final String cfmNewPassword = getInput(cfmNewPwEditText);

            if(TextUtils.isEmpty(oldPassword)){
                oldPwEditText.setError("Old password is Required.");
                return;
            }

            if(TextUtils.isEmpty(newPassword)){
                newPwEditText.setError("New password is required");
                return;
            }

            if(TextUtils.isEmpty(cfmNewPassword)){
                cfmNewPwEditText.setError("Confirm new password");
                return;
            }

            if(newPassword.length() < 6){
                newPwEditText.setError("Password Must be >= 6 Characters");
                return;
            }

            if (newPassword.compareTo(cfmNewPassword)!=0)
            {
                newPwEditText.setError("Password does not match");
                return;
            }

            //change password
            changePassword(oldPassword, newPassword);

        });




        return view;
    }

    private void changePassword(String oldPw, String newPw)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View view = getView();
        final EditText oldPwEditText = view.findViewById(R.id.oldPassword);

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        accountMgr.retrieveEmailAdress(value -> {
            email = value;
            AuthCredential credential = EmailAuthProvider.getCredential(email, oldPw);
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPw);
                            accountMgr.changePassword(newPw,uId);

                            Intent intent = new Intent(getActivity(), SettingsActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "old password is incorrect");
                            oldPwEditText.setError("Old password is incorrect.");


                        }
                    });
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
    }


}
