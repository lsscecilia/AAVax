package com.example.aavax.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aavax.R;
import com.example.aavax.ui.FirebaseManager;
import com.example.aavax.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.example.aavax.ui.CustomMessageEvent;

import entity.FirebaseInterface;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private FirebaseInterface firebaseManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseManager = new FirebaseManager();

        final EditText emailEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar progressBar = findViewById(R.id.loading);
        final TextView createAccount = findViewById(R.id.create_account);
        final TextView forgotPassword = findViewById(R.id.forgot_password);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = getInput(emailEditText);
                final String password = getInput(passwordEditText);

                if(TextUtils.isEmpty(email)){
                    emailEditText.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    passwordEditText.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    passwordEditText.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                signIn(email, password);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createAccount.getContext(), CreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * This method is called when user sign in. Will go to HomePageFragment in MainActivity if authentication is successful
     * @param email
     * @param password
     */
    private void signIn(String email, String password)
    {
        final ProgressBar progressBar = findViewById(R.id.loading);
        final EditText emailEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //set first profile to be default profile
                            FirebaseManager firebaseManager = new FirebaseManager();
                            firebaseManager.setDefaultProfile(user.getUid());

                            //send userid on event bus
                            onEventBus(user.getUid());

                            //clear input
                            clearInput(emailEditText);
                            clearInput(passwordEditText);

                            //updateUI(user);
                            firebaseManager.setDefaultProfile(user.getUid());
                            updateUi();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });
    }

    /**
     * Update UI when login is successful
     */
    private void updateUi() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

    public String getInput(EditText editText){
        return editText.getText().toString().trim();
    }

    /**
     * Clears any form of input that the user has typed
     * @param editText EditText id
     */
    public void clearInput(EditText editText){
        editText.getText().clear();
    }

}
