package com.example.aavax.ui;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.Account;
import model.Vaccine;
import model.VaccineLog;
import model.VaccineLogEntry;
import model.firebaseInterface;

public class FirebaseManager implements firebaseInterface {

    private FirebaseDatabase database;
    private DatabaseReference vaccinesRef;
    private DatabaseReference userRef;
    private ArrayList<Vaccine> vaccines;

    public FirebaseManager() {
    }

    //TODO
    @Override
    public DatabaseReference getCurrentUser(final String userId) {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Account account = dataSnapshot.child(userId).getValue(Account.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }

    //temporary
    public void updateVaccine()
    {
        Vaccine v1 = new Vaccine("Hepatitis A", "Hepatitis A is a viral infection that causes the liver to become enlarged, inflamed and tender. There is no chronic (long-term) infection. \n" +
                "\n" +
                "The virus is excreted in faeces and transmitted through contaminated food and water. Eating shellfish taken from sewage-contaminated water is a common means of contracting the Hepatitis A virus (HAV). It can also be acquired by close contact with individuals infected with the virus.\n" +
                "\n" +
                "A person is infectious for two to three weeks before he or she experiences symptoms, and during the first week of the illness.", 0, false);
        Vaccine v2 = new Vaccine("Measles","Measles, also known as rubeola, is a highly contagious infection that affects the respiratory system and often results in a skin rash. The infection is more common among children. However, it can be contracted at any age. \n" +
                "\n" +
                "Most people with measles recover completely after treatment, but there are instances when a person can fall very ill and develop health complications. In 2003, there were over 30 million cases of measles worldwide. About half a million of these cases ended in death.  \n" +
                "\n" +
                "Read on to learn more about this potentially life-threatening viral disease.", 0, false );



        Vaccine v3 = new Vaccine("Influenza (Flu)", "The flu vaccine is recommended every year for children 6 months and older:\n" +
                "Kids younger than 9 who get the flu vaccine for the first time (or who have only had one dose before July 2019) will get it in 2 separate doses at least a month apart.\n" +
                "Those younger than 9 who have had at least 2 doses of flu vaccine previously (in the same or different seasons) will only need 1 dose.\n" +
                "Kids older than 9 need only 1 dose.\n" +
                "The vaccine is given by injection with a needle (the flu shot) or by nasal spray. Both types of vaccine can be used this flu season (2019–2020) because they seem to work equally well. Your doctor will recommend which to use based on your child's age and general health. The nasal spray is only for healthy people ages 2–49. People with weak immune systems or some health conditions (such as asthma) and pregnant women should not get the nasal spray vaccine.",6, true );

        storeVaccine(v1);
        storeVaccine(v2);
        storeVaccine(v3);
    }

    @Override
    public void storeVaccine(final Vaccine vaccine) {
        database = FirebaseDatabase.getInstance();
        //users = database.getReference("Users");
        vaccinesRef = database.getReference("Vaccines");
        vaccinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vaccinesRef.child(vaccine.getName()).setValue(vaccine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    /*
    public void addVaccineLogEntry(final String userId, LocalDate date, String vaccineName)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        Vaccine vaccine = findVaccine(vaccineName);

        final VaccineLogEntry vaccineLogEntry = new VaccineLogEntry(date,vaccine);
        //final HashMap<String, VaccineLogEntry> hash = new HashMap<>();
        System.out.println(userId+"user IDDDDDD");
        final String key = userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").push().getKey();
        //hash.put(key, vaccineLogEntry);
        System.out.println(key+"KEYYYY");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").child(key).setValue(vaccineLogEntry);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/


    public void addVaccineLogEntry(final String userId,final Date date, final String vaccineName) {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        userRef = database.getReference("users");
        //vaccines = new ArrayList<>();

        vaccinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vaccine vaccine = dataSnapshot.child(vaccineName).getValue(Vaccine.class);
                System.out.println("VACCCCCINEEEE" + vaccine.getName() + "detail: " + vaccine.getDetail());
                final VaccineLogEntry vaccineLogEntry = new VaccineLogEntry(date, vaccine);
                //final HashMap<String, VaccineLogEntry> hash = new HashMap<>();
                System.out.println(userId + "user IDDDDDD");
                final String key = userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").push().getKey();
                //hash.put(key, vaccineLogEntry);
                System.out.println(key + "KEYYYY");
                userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").child(key).setValue(vaccineLogEntry);
                /*
                for (DataSnapshot data: dataSnapshot.getChildren() )
                {

                    System.out.println(data.getValue(Vaccine.class).toString());
                    vaccines.add(data.getValue(Vaccine.class));
                    System.out.println("vaccine name:" + data.getValue(Vaccine.class).getName() + "details: "+ data.getValue(Vaccine.class).getDetail() );
                    System.out.println("eh hello u spoil ah");
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Vaccine> retrieveVaccines() {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        vaccines = new ArrayList<>();


        vaccinesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren() )
                {
                    System.out.println(data.getValue(Vaccine.class).toString());
                    System.out.println("vaccine name:" + data.getValue(Vaccine.class).getName() + "details: "+ data.getValue(Vaccine.class).getDetail() );
                    vaccines.add(data.getValue(Vaccine.class));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("Trail" + vaccines.get(0).getName());
        return vaccines;
    }


    public void retrieveVaccinesName() {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");


        vaccinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> vaccines = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren() )
                {
                    System.out.println(data.getKey());
                    vaccines.add(data.getKey());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*
    public void addVaccineIntoProfile(final String vaccineName, final String userId, final Date date) {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        userRef = database.getReference("users");
        vaccinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vaccine vaccine = dataSnapshot.child(vaccineName).getValue(Vaccine.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {

            VaccineLogEntry vaccineLogEntry = new VaccineLogEntry(date, vaccine, )
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userRef.child(userId).child("profiles").setValue()
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/
}
