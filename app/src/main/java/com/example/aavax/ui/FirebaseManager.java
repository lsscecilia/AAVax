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
import model.Country;
import model.Vaccine;
import model.VaccineLog;
import model.VaccineLogEntry;
import model.firebaseInterface;

public class FirebaseManager implements firebaseInterface {

    private FirebaseDatabase database;
    private DatabaseReference vaccinesRef;
    private DatabaseReference userRef;
    private DatabaseReference countriesRef;
    private ArrayList<Vaccine> vaccines;

    private static Vaccine bcg, cholera, hpv, hep_a, hep_b, flu, japanese_encephalitis, measles, polio, shingles, tdap, typhoid, varicella, yellow_fever;

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
    public void storeCountry(final Country country) {
        database = FirebaseDatabase.getInstance();
        //users = database.getReference("Users");
        vaccinesRef = database.getReference("Countries");
        vaccinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vaccinesRef.child(country.getName()).setValue(country);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteVaccineLogEntry(final String userId, final String vaccineName)
    {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        userRef = database.getReference("users");
        //vaccines = new ArrayList<>();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("vaccine log entry count" + dataSnapshot.child(userId).child("profiles").child("0").child("vaccineLogEntries").getChildrenCount());
                for (DataSnapshot data : dataSnapshot.child(userId).child("profiles").child("0").child("vaccineLogEntries").getChildren()) {
                    System.out.println(data.getValue(VaccineLogEntry.class).getVaccine().getName() + "vaccineee nam eeeee");
                    if (data.getValue(VaccineLogEntry.class).getVaccine().getName() == vaccineName) {
                        userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").child(data.getKey()).removeValue();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateVaccineLogEntry(final String userId, final String vaccineName, final Date dateTaken, final Date dateDue, final String reminder)
    {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        userRef = database.getReference("users");
        //vaccines = new ArrayList<>();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key="";
                System.out.println("vaccine log entry count"+ dataSnapshot.child(userId).child("profiles").child("0").child("vaccineLogEntries").getChildrenCount());
                for (DataSnapshot data: dataSnapshot.child(userId).child("profiles").child("0").child("vaccineLogEntries").getChildren())
                {
                    System.out.println(data.getValue(VaccineLogEntry.class).getVaccine().getName() + "vaccineee nam eeeee");
                    if (data.getValue(VaccineLogEntry.class).getVaccine().getName()==vaccineName)
                    {
                        key =data.getKey();
                    }
                }
                System.out.println("keyy for vaccine entry log: "+key);
                Vaccine vaccine = dataSnapshot.child(userId).child("profiles").child("0").child("vaccineLogEntries").child(key).getValue(VaccineLogEntry.class).getVaccine();
                userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").child(key).removeValue();
                VaccineLogEntry vaccineLogEntry;
                if (reminder.compareTo("true")==0)
                    vaccineLogEntry = new VaccineLogEntry(dateTaken, vaccine, dateDue, true);
                else
                    vaccineLogEntry = new VaccineLogEntry(dateTaken, vaccine, dateDue, false);


                final String key1 = userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").push().getKey();
                //hash.put(key, vaccineLogEntry);
                System.out.println(key + "KEYYYY");
                userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").child(key1).setValue(vaccineLogEntry);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void retrieveVaccineLogWithReminder(final MyCallbackVaccineLog myCallback, final String Uid)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<VaccineLogEntry> vaccineLogArrayList = new ArrayList<>();
                System.out.println("user id in firebase manager: " + Uid);
                for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").child("0").child("vaccineLogEntries").getChildren())
                {
                    if (data.getValue(VaccineLogEntry.class).getReminder())
                    {
                        vaccineLogArrayList.add(data.getValue(VaccineLogEntry.class));
                        System.out.println(data.getValue(VaccineLogEntry.class).getVaccine().getName() + "usre vaccine log");
                    }

                }
                myCallback.onCallback(vaccineLogArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void retrieveVaccineLog(final MyCallbackVaccineLog myCallback, final String Uid){
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<VaccineLogEntry> vaccineLogArrayList = new ArrayList<>();
                System.out.println("user id in firebase manager: " + Uid);
                for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").child("0").child("vaccineLogEntries").getChildren())
                {
                    vaccineLogArrayList.add(data.getValue(VaccineLogEntry.class));
                    System.out.println(data.getValue(VaccineLogEntry.class).getVaccine().getName() + "usre vaccine log");
                }
                myCallback.onCallback(vaccineLogArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface MyCallbackVaccineLog {
        void onCallback(ArrayList<VaccineLogEntry> value);
    }



    public void retrieveUserVaccine(final MyCallback myCallback, final String Uid)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Vaccine> vaccineArrayList = new ArrayList<>();
                System.out.println("user id in firebase manager: " + Uid);
                for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").child("0").child("vaccineLogEntries").getChildren())
                {
                    vaccineArrayList.add(data.getValue(VaccineLogEntry.class).getVaccine());
                    System.out.println(data.getValue(VaccineLogEntry.class).getVaccine().getName() + "usre vaccine");
                }
                myCallback.onCallback(vaccineArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void retrieveVaccines(final MyCallback myCallback) {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");



        vaccinesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Vaccine> vaccineArrayList = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                    vaccineArrayList.add(data.getValue(Vaccine.class));
                }
                myCallback.onCallback(vaccineArrayList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //System.out.println("Trail" + vaccines.get(0).getName());
    }

    public interface MyCallback {
        void onCallback(ArrayList<Vaccine> value);
    }

    public interface MyCallBackVaccine {
        void onCallback(Vaccine value);
    }

    public void retrieveVaccine(final MyCallBackVaccine myCallback, final String vaccineName){
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        vaccinesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vaccine vaccine;
                vaccine = dataSnapshot.child(vaccineName).getValue(Vaccine.class);
                myCallback.onCallback(vaccine);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public interface MyCallBackCountry {
        void onCallback(Country value);
    }

    public void retrieveCountry(final MyCallBackCountry myCallback, final String countryName){
        database = FirebaseDatabase.getInstance();
        countriesRef = database.getReference("Countries");
        countriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Country country;
                country = dataSnapshot.child(countryName).getValue(Country.class);
                myCallback.onCallback(country);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void initCountries() {
        retrieveVaccines(new MyCallback() {

            @Override
            public void onCallback(ArrayList<Vaccine> value) {
                vaccines = value;
                for (Vaccine vaccine : vaccines) {
                    if (vaccine.getName().equals("Bacille Calmette-Guerin")) {
                        bcg = vaccine;
                        System.out.println("Success bcg");
                    }
                    if (vaccine.getName().equals("Cholera")){
                        cholera = vaccine;
                        System.out.println("Success cholera");
                    }
                    if (vaccine.getName().equals("Hepatitis A")){
                        hep_a = vaccine;
                        System.out.println("Success hep a");
                    }
                    if (vaccine.getName().equals("Human Papillomavirus"))
                        hpv = vaccine;
                    if (vaccine.getName().equals("Hepatitis B"))
                        hep_b = vaccine;
                    if (vaccine.getName().equals("Influenza (Flu)"))
                        flu = vaccine;
                    if (vaccine.getName().equals("Japanese Encephalitis"))
                        japanese_encephalitis = vaccine;
                    if (vaccine.getName().equals("Measles"))
                        measles = vaccine;
                    if (vaccine.getName().equals("Polio"))
                        polio = vaccine;
                    if (vaccine.getName().equals("Shingles"))
                        shingles = vaccine;
                    if (vaccine.getName().equals("Tdap"))
                        tdap = vaccine;
                    if (vaccine.getName().equals("Typhoid"))
                        typhoid = vaccine;
                    if (vaccine.getName().equals("Varicella"))
                        varicella = vaccine;
                    if (vaccine.getName() == "Yellow Fever")
                        yellow_fever = vaccine;
                }
            }
        });

        ArrayList<Vaccine> manVMalaysia = new ArrayList<>();
        ArrayList<Vaccine> recVMaylaysia = new ArrayList<>();
        manVMalaysia.add(flu);
        manVMalaysia.add(measles);
        manVMalaysia.add(polio);
        manVMalaysia.add(varicella);

        recVMaylaysia.add(hep_a);
        recVMaylaysia.add(typhoid);

        Country malaysia = new Country("Malaysia", manVMalaysia, recVMaylaysia);
        storeCountry(malaysia);


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
}
