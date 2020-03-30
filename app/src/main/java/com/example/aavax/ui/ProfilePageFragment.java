package com.example.aavax.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aavax.R;
//import com.example.aavax.ui.ProfileRVAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.ProfileRV;


public class ProfilePageFragment extends Fragment  {

    private static final String TAG = "ProfileFragment";
    //widgets

    //vars
    private IMainActivity mIMainActivity;
    private RecyclerView recyclerView;
    //private ProfileRVAdapter adapter;
    private ArrayList<ProfileRV> profileRVArrayList;
    private ImageView editNameButton;
    private AlertDialog dialog;
    private EditText editText;
    private TextView usersName;
    private TextView otherProfiles;
    private TextView aboutUs;
    private TextView sign_out;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);
        profileRVArrayList = new ArrayList<>();

        //createListData();

        usersName = view.findViewById(R.id.users_name);
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

        editNameButton = view.findViewById(R.id.editNameButton);
        editNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText());
                dialog.show();
            }
        });
    otherProfiles = view.findViewById(R.id.other_profiles_button);
    otherProfiles.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            mIMainActivity.inflateFragment(otherProfiles.getText().toString(), otherProfiles.getText().toString());
        }
        });
    aboutUs = view.findViewById(R.id.about_us_button);
    aboutUs.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIMainActivity.inflateFragment(aboutUs.getText().toString(), aboutUs.getText().toString());
        }
    }));
    sign_out = view.findViewById(R.id.sign_out_button);
    sign_out.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        mIMainActivity.inflateFragment(sign_out.getText().toString(), sign_out.getText().toString());
        }
    });

        return view;
    }

    //@Override
    //public void onAttach(@NonNull Context context) {
       // super.onAttach(context);
       // mIMainActivity = (IMainActivity) getActivity();
   // }

    //@Override
    //public void onClick(View v) {
       // mIMainActivity.inflateFragment(String.valueOf(v.get));
    //}

    //@Override
    //public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //recyclerView = getView().findViewById(R.id.profile_recycler);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // add line after each row
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //adapter = new ProfileRVAdapter(getActivity(), profileRVArrayList);
        //recyclerView.setAdapter(adapter);


        //}
    //private void createListData() {
        //ProfileRV profileRV1 = new ProfileRV("Other Profiles");
        //profileRVArrayList.add(profileRV1);
        //ProfileRV profileRV2 = new ProfileRV("About Us");
        //profileRVArrayList.add(profileRV2);
        //ProfileRV profileRV3 = new ProfileRV("Sign Out");
        //profileRVArrayList.add(profileRV3);

    //}
}

