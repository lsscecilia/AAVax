package control;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.aavax.R;
import com.example.aavax.ui.CustomMessageEvent;
import com.example.aavax.ui.MainActivity;
import com.example.aavax.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import entity.Account;
import entity.AccountMgrInterface;

public class AccountMgr implements AccountMgrInterface {

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private String userID;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;

    public AccountMgr()
    {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
    }

    /**
     * This method is called when user sign in. Will go to HomePageFragment in MainActivity if authentication is successful
     * @param email
     * @param password
     * @param activity
     */

    public void signIn(String email, String password, Activity activity)
    {
        final ProgressBar progressBar = activity.findViewById(R.id.loading);
        final EditText emailEditText = activity.findViewById(R.id.username);
        final EditText passwordEditText = activity.findViewById(R.id.password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            //set first profile to be default profile
                            setDefaultProfile(user.getUid());

                            //send userid on event bus
                            onEventBus(user.getUid());

                            //clear input
                            clearInput(emailEditText);
                            clearInput(passwordEditText);

                            //updateUI(user);
                            setDefaultProfile(user.getUid());

                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });
    }


    @Override
    public void createAccount(String email, String password, String firstName, String lastName, String dob, Activity activity)
    {
        if (!email.equals("") && !password.equals("") && !firstName.equals("") && !lastName.equals("")) {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(activity, "User Created.", Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        System.out.println("userid" + userID);
                        Account user = new Account(email, password, firstName, lastName, dob);
                        myRef.child("users").child(userID).setValue(user);

                    }else {
                        Toast.makeText(activity, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            activity.startActivity(new Intent(activity.getApplicationContext(), LoginActivity.class));
        }
    }

    @Override
    public void changePassword(String password, String uId) {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        System.out.println("uid in change password"+uId);
        userRef.child(uId).child("password").setValue(password);
    }

    @Override
    public void retrieveCurrentProfileName(MyCallbackString myCallback, String Uid) {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileName;
                for (DataSnapshot data : dataSnapshot.child(Uid).child("profiles").getChildren()) {
                    if (data.child("thisProfile").getValue(boolean.class)) {
                        profileName = data.child("name").getValue(String.class);
                        myCallback.onCallback(profileName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void retrieveEmailAdress(MyCallbackString myCallback, String Uid) {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child(Uid).child("email").getValue(String.class);
                myCallback.onCallback(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void retrieveSubprofileNameAndID(MyCallbackHashMap myCallback, String Uid) {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> profileName = new HashMap<>();
                if (dataSnapshot.child(Uid).child("profiles").getChildrenCount()!=1) {
                    for (DataSnapshot data : dataSnapshot.child(Uid).child("profiles").getChildren()) {
                        if (!data.child("thisProfile").getValue(boolean.class)) {
                            profileName.put(data.child("name").getValue(String.class), data.getKey());
                        }
                    }
                    myCallback.onCallback(profileName);
                }
                else
                    myCallback.onCallback(profileName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setDefaultProfile(String Uid) {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean flag=false;
                String profileId;
                int numProfiles = (int) dataSnapshot.child(Uid).child("profiles").getChildrenCount();

                for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").getChildren())
                {
                    if (!flag)
                    {
                        userRef.child(Uid).child("profiles").child(data.getKey()).child("thisProfile").setValue(true);
                        flag = true;
                        continue;
                    }
                    else
                    {
                        profileId = data.getKey();
                        userRef.child(Uid).child("profiles").child(profileId).child("thisProfile").setValue(false);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /**
     * Clears any form of input that the user has typed
     * @param editText EditText id
     */
    private void clearInput(EditText editText){
        editText.getText().clear();
    }


    /**
     * Based on Observer Pattern design.
     * send userID through event bus
     * @param userID
     */
    private void onEventBus(String userID){
        CustomMessageEvent event = new CustomMessageEvent(userID);
        EventBus.getDefault().postSticky(event);
    }


    public interface MyCallbackHashMap{
        void onCallback(HashMap<String,String> value);
    }

    public interface MyCallbackString {
        void onCallback(String value);
    }

}
