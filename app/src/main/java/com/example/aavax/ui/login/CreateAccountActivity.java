package com.example.aavax.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import entity.Account;

public class CreateAccountActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();

        final EditText emailText = findViewById(R.id.emailText);
        final EditText passwordText = findViewById(R.id.password);
        final EditText confirmPasswordText = findViewById(R.id.confirmPassword);
        final EditText firstNameText = findViewById(R.id.firstName);
        final EditText lastNameText = findViewById(R.id.lastName);
        final EditText dateOfBirthText = findViewById(R.id.DOB);
        final Button createAccount = findViewById(R.id.create_account_button);

        Toolbar toolbar = findViewById(R.id.create_account_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        /*
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();*/

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit pressed.");
                final String email = getInput(emailText);
                //final String password = passwordText.getText().toString();
                final String confirmPassword = getInput(confirmPasswordText);
                final String firstName = getInput(firstNameText);
                final String lastName = getInput(lastNameText);
                final String dob = getInput(dateOfBirthText);

                //handle the exception if the EditText fields are null
                if (!email.equals("") && !confirmPassword.equals("") && !firstName.equals("") && !lastName.equals("")) {
                    mAuth.createUserWithEmailAndPassword(email,confirmPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CreateAccountActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                                userID = mAuth.getCurrentUser().getUid();
                                System.out.println("userid" + userID);
                                Account user = new Account(email, confirmPassword, firstName, lastName, dob);
                                myRef.child("users").child(userID).setValue(user);
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else {
                                Toast.makeText(CreateAccountActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    //toastMessage("Fill out all the fields");
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public String getInput(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * Based on Observer Pattern design.
     * Offers a subscription mechanism for each class Activity/Fragment to subscribe to
     * @param event Event to be sent through EventBus
     */
    @Subscribe
    public void onEvent(CustomMessageEvent event){
        Log.d("MAINACTIVITY EB SENDER","Username :\"" + event.getCustomMessage() + "\" Successfully Fired!");

    }

    public void onEventBus(String username){
        CustomMessageEvent event = new CustomMessageEvent(username);
        EventBus.getDefault().postSticky(event);
    }




}
