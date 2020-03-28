package com.example.aavax.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aavax.R;
import com.example.aavax.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import model.Account;

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
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

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
                //final ProgressBar progressBar = findViewById(R.id.loading);

                        /*
                        Log.d(TAG, "onClick: Attempting to submit to database: \n" +
                                "name: " + name + "\n" +
                                "email: " + email + "\n" +
                                "phone number: " + phoneNum + "\n"
                        );*/

                //handle the exception if the EditText fields are null
                if (!email.equals("") && !confirmPassword.equals("") && !firstName.equals("") && !lastName.equals("")) {
                    Account user = new Account(email, confirmPassword, firstName, lastName, dob);
                    //UserInformation userInformation = new UserInformation(email, name, phoneNum);
                    myRef.child("users").child(userID).setValue(user);
                    //toastMessage("New Information has been saved.");
                    //mName.setText("");
                    //mEmail.setText("");
                    //mPhoneNum.setText("");

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

}

        /*
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = getInput(emailText);
                //final String password = passwordText.getText().toString();
                final String confirmPassword = getInput(confirmPasswordText);
                final String firstName = getInput(firstNameText);
                final String lastName = getInput(lastNameText);
                final String dob = getInput(dateOfBirthText);
                final ProgressBar progressBar = findViewById(R.id.loading);

                /*
                if(TextUtils.isEmpty(email)){
                    emailText.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    passwordText.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    passwordText.setError("Password Must be >= 6 Characters");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)){
                    confirmPasswordText.setError("password confirmation is Required.");
                    return;
                }

                if (TextUtils.isEmpty(firstName)){
                    firstNameText.setError("first name is Required.");
                    return;
                }

                if (TextUtils.isEmpty(lastName)){
                    lastNameText.setError("last name is Required.");
                    return;
                }

                if (TextUtils.isEmpty(dob)){
                    dateOfBirthText.setError("Date of birth is Required.");
                    return;
                }*/


                //progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase





                /*
                mAuth.createUserWithEmailAndPassword(email,confirmPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("email",email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }else {
                            Toast.makeText(CreateAccountActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                }); */

/*
        // back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });*/

        // create account button
        /*
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}*/

