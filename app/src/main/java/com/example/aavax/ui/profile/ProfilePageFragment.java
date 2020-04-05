
package com.example.aavax.ui.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.FirebaseManager;
import com.example.aavax.ui.IMainActivity;
import com.example.aavax.ui.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import entity.FirebaseInterface;

public class ProfilePageFragment extends Fragment  {

    private static final String TAG = "Profile";
    //widgets

    //vars
    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    private ImageView editNameButton;
    private AlertDialog dialog;
    private EditText editText;
    private TextView usersName;
    private TextView otherProfiles;
    private TextView aboutUs;
    private TextView sign_out;
    private FirebaseInterface firebaseManager;
    private String uId;
    private FragmentActivity myContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
        firebaseManager = new FirebaseManager();
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


        usersName = view.findViewById(R.id.users_name);

        //set username
        firebaseManager.retrieveCurrentProfileName(new FirebaseManager.MyCallbackString() {
            @Override
            public void onCallback(String value) {
                usersName.setText(value);
            }
        }, uId);


        //edit name with dialog
        dialog = new AlertDialog.Builder(getActivity()).create();
        editText = new EditText(getActivity());

        dialog.setTitle("Edit name");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE CHANGES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                usersName.setText(editText.getText());
                String name = getInput(editText);
                firebaseManager.editProfile(uId, name);
            }
        });


        editNameButton = view.findViewById(R.id.editNameButton);

        editNameButton.setOnClickListener(v -> {
            editText.setText(editText.getText());
            dialog.show();
        });


        //onClickListener for other profiles
        otherProfiles = view.findViewById(R.id.other_profiles_button);
        otherProfiles.setOnClickListener(v -> {

            firebaseManager.retrieveSubprofileNameAndID(value -> {

                Bundle bundle = new Bundle();
                bundle.putSerializable("hashMap", value);
                Fragment myFragment = new OtherProfilesFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                myFragment.setArguments(bundle);
            }, uId);
        });

        aboutUs = view.findViewById(R.id.about_us_button);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment myFragment = new AboutUsFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
        //onClickListen for about us

        sign_out = view.findViewById(R.id.sign_out_button);
        sign_out.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class)));

        return view;
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = myContext.getSupportFragmentManager().beginTransaction();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
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

    public String getInput(EditText editText) {
        return editText.getText().toString().trim();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        mIMainActivity.setToolbarTitle(TAG);
    }



}