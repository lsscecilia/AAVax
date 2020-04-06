package com.example.aavax.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aavax.R;
import com.google.firebase.auth.FirebaseAuth;

import control.AccountMgr;
import control.ProfileMgr;
import entity.AccountMgrInterface;
import entity.ProfileMgrInterface;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private AccountMgrInterface accountMgr;
    private ProfileMgrInterface profileMgr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountMgr = new AccountMgr();
        profileMgr = new ProfileMgr();

        final EditText emailEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar progressBar = findViewById(R.id.loading);
        final TextView createAccount = findViewById(R.id.create_account);
        final TextView forgotPassword = findViewById(R.id.forgot_password);


        loginButton.setOnClickListener(v -> {
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
            accountMgr.signIn(email, password, this);
        });

        createAccount.setOnClickListener(v -> {
            Intent intent = new Intent(createAccount.getContext(), CreateAccountActivity.class);
            startActivity(intent);
            finish();
        });
    }



    public String getInput(EditText editText){
        return editText.getText().toString().trim();
    }




}
