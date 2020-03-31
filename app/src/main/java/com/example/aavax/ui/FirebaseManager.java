package com.example.aavax.ui;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import model.Profile;
import model.Account;
import model.CDCThreatLevel;
import model.Country;
import model.Vaccine;
import model.VaccineLogEntry;
import model.firebaseInterface;

public class FirebaseManager implements firebaseInterface {

    private FirebaseDatabase database;
    private DatabaseReference vaccinesRef;
    private DatabaseReference userRef;
    private DatabaseReference countriesRef;
    private ArrayList<Vaccine> vaccines;
    //private String profileId;

    private static Vaccine bcg, cholera, hpv, hep_a, hep_b, flu, japanese_encephalitis, measles, polio, shingles, tdap, typhoid, varicella, yellow_fever;

    public FirebaseManager() {
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

    /**
     * delete vaccineLogEntry
     * @param userId
     * @param vaccineName
     */

    public void deleteVaccineLogEntry(final String userId, final String vaccineName)
    {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        userRef = database.getReference("users");
        //vaccines = new ArrayList<>();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileId="";
                //get profile id
                if (dataSnapshot.child(userId).child("profiles").getChildrenCount()!=1)
                {
                    for (DataSnapshot data: dataSnapshot.child(userId).child("profiles").getChildren())
                    {
                        System.out.println("finding profile....." + data.child("thisProfile").getValue(boolean.class));

                        //then error here how
                        if (data.child("thisProfile").getValue(boolean.class))
                        {
                            profileId = data.getKey();
                            System.out.println("profile id: "+profileId);
                            break;

                        }
                    }
                }
                else
                    profileId = "0";

                System.out.println("vaccine log entry count" + dataSnapshot.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").getChildrenCount());
                for (DataSnapshot data : dataSnapshot.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").getChildren()) {
                    System.out.println(data.getValue(VaccineLogEntry.class).getVaccine().getName() + "vaccineee nam eeeee");
                    if (data.getValue(VaccineLogEntry.class).getVaccine().getName() == vaccineName) {
                        userRef.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").child(data.getKey()).removeValue();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * update vaccine log entry
     * @param userId
     * @param vaccineName
     * @param dateTaken
     * @param dateDue
     * @param reminder
     */
    @Override
    public void updateVaccineLogEntry(final String userId, final String vaccineName, final Date dateTaken, final Date dateDue, final String reminder)
    {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        userRef = database.getReference("users");
        //vaccines = new ArrayList<>();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileId="";
                //get profile id
                if (dataSnapshot.child(userId).child("profiles").getChildrenCount()!=1)
                {
                    for (DataSnapshot data: dataSnapshot.child(userId).child("profiles").getChildren())
                    {
                        System.out.println("finding profile....." + data.child("thisProfile").getValue(boolean.class));

                        //then error here how
                        if (data.child("thisProfile").getValue(boolean.class))
                        {
                            profileId = data.getKey();
                            System.out.println("profile id: "+profileId);
                            break;

                        }
                    }
                }
                else
                    profileId = "0";

                String key="";
                System.out.println("vaccine log entry count"+ dataSnapshot.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").getChildrenCount());
                for (DataSnapshot data: dataSnapshot.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").getChildren())
                {
                    System.out.println(data.getValue(VaccineLogEntry.class).getVaccine().getName() + "vaccineee nam eeeee");
                    if (data.getValue(VaccineLogEntry.class).getVaccine().getName()==vaccineName)
                    {
                        key =data.getKey();
                    }
                }
                System.out.println("keyy for vaccine entry log: "+key);
                Vaccine vaccine = dataSnapshot.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").child(key).getValue(VaccineLogEntry.class).getVaccine();
                userRef.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").child(key).removeValue();
                VaccineLogEntry vaccineLogEntry;
                if (reminder.compareTo("true")==0)
                    vaccineLogEntry = new VaccineLogEntry(dateTaken, vaccine, dateDue, true);
                else
                    vaccineLogEntry = new VaccineLogEntry(dateTaken, vaccine, dateDue, false);


                final String key1 = userRef.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").push().getKey();
                //hash.put(key, vaccineLogEntry);
                System.out.println(key + "KEYYYY");
                userRef.child(userId).child("profiles").child(profileId).child("vaccineLogEntries").child(key1).setValue(vaccineLogEntry);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * add vaccineLogEntry
     * @param userId
     * @param date
     * @param vaccineName
     */
    @Override
    public void addVaccineLogEntry(final String userId,final Date date, final String vaccineName) {
        database = FirebaseDatabase.getInstance();
        vaccinesRef = database.getReference("Vaccines");
        userRef = database.getReference("users");


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileId="0";
                //get profile id
                if (dataSnapshot.child(userId).child("profiles").getChildrenCount()!=1)
                {
                    for (DataSnapshot data: dataSnapshot.child(userId).child("profiles").getChildren())
                    {
                        System.out.println("finding profile....." + data.child("thisProfile").getValue(boolean.class));

                        //then error here how
                        if (data.child("thisProfile").getValue(boolean.class))
                        {
                            profileId = data.getKey();
                            System.out.println("profile id: "+profileId);
                            break;

                        }
                    }
                }
                final String pId = profileId;
                vaccinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println("final profile id to be added" + pId);
                        Vaccine vaccine = dataSnapshot.child(vaccineName).getValue(Vaccine.class);
                        if (dataSnapshot.child(vaccineName).child("oneTime").getValue(boolean.class))
                        {
                            System.out.println("no got reminder");
                            final VaccineLogEntry vaccineLogEntry = new VaccineLogEntry(date, vaccine);
                            System.out.println(userId + "user IDDDDDD");
                            final String key = userRef.child(userId).child("profiles").child(pId).child("vaccineLogEntries").push().getKey();
                            userRef.child(userId).child("profiles").child(pId).child("vaccineLogEntries").child(key).setValue(vaccineLogEntry);
                        }
                        else
                        {
                            System.out.println("got reminder");
                            Date nextDue;
                            if (date.getMonth()+vaccine.getNumMonths()<=12)
                            {
                                int mth = date.getMonth()+vaccine.getNumMonths();
                                nextDue = new Date(date.getYear(), mth, date.getDate());
                            }
                            else
                            {
                                int mth = date.getMonth()+vaccine.getNumMonths()-12;
                                int year = date.getYear()+1;
                                nextDue = new Date(year, mth , date.getDate());
                            }

                            final VaccineLogEntry vaccineLogEntry= new VaccineLogEntry(date, vaccine, nextDue,true);
                            System.out.println(userId + "user IDDDDDD");
                            final String key = userRef.child(userId).child("profiles").child(pId).child("vaccineLogEntries").push().getKey();
                            userRef.child(userId).child("profiles").child(pId).child("vaccineLogEntries").child(key).setValue(vaccineLogEntry);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * retrieve vaccineLogEntries that require reminder
     * @param myCallback
     * @param Uid
     * @return ArrayList<VaccineLogEntry> through MyCallbackVaccineLog interface
     */
    @Override
    public void retrieveVaccineLogWithReminder(final MyCallbackVaccineLog myCallback, final String Uid)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileId="";
                //get profile id
                if (dataSnapshot.child(Uid).child("profiles").getChildrenCount()!=1)
                {
                    for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").getChildren())
                    {
                        System.out.println("finding profile....." + data.child("thisProfile").getValue(boolean.class));

                        //then error here how
                        if (data.child("thisProfile").getValue(boolean.class))
                        {
                            profileId = data.getKey();
                            System.out.println("profile id: "+profileId);
                            break;

                        }
                    }
                }
                else
                    profileId = "0";

                ArrayList<VaccineLogEntry> vaccineLogArrayList = new ArrayList<>();
                System.out.println("user id in firebase manager: " + Uid);
                for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").child(profileId).child("vaccineLogEntries").getChildren())
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

    /**
     * retrieve vaccineLogEntries
     * @param myCallback
     * @param Uid
     * @return ArrayList<VaccineLogEntry> through MyCallbackVaccineLog interface
     */
    @Override
    public void retrieveVaccineLog(final MyCallbackVaccineLog myCallback, final String Uid){
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileId="";
                //get profile id
                if (dataSnapshot.child(Uid).child("profiles").getChildrenCount()!=1)
                {
                    for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").getChildren())
                    {
                        System.out.println("finding profile....." + data.child("thisProfile").getValue(boolean.class));

                        //then error here how
                        if (data.child("thisProfile").getValue(boolean.class))
                        {
                            profileId = data.getKey();
                            System.out.println("profile id: "+profileId);
                            break;

                        }
                    }
                }
                else
                    profileId = "0";

                ArrayList<VaccineLogEntry> vaccineLogArrayList = new ArrayList<>();
                System.out.println("user id in firebase manager: " + Uid);
                for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").child(profileId).child("vaccineLogEntries").getChildren())
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

    public void retrieveCurrentProfileName(final MyCallbackString myCallback, final String Uid)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileName;
                if (dataSnapshot.child(Uid).child("profiles").getChildrenCount()!=1) {
                    for (DataSnapshot data : dataSnapshot.child(Uid).child("profiles").getChildren()) {
                        if (data.child("thisProfile").getValue(boolean.class)) {
                            profileName = data.child("name").getValue(String.class);
                            myCallback.onCallback(profileName);
                        }
                    }
                }
                else{
                    myCallback.onCallback(dataSnapshot.child(Uid).child("profiles").child("0").child("name").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retrieveEmailAdress(final MyCallbackString myCallback, String Uid)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
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

    public void retrieveProfiles(final MyCallbackProfiles myCallback, final String Uid)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Profile> profiles = new ArrayList<>();
                if (dataSnapshot.child(Uid).child("profiles").getChildrenCount()==1)
                    myCallback.onCallback(null);
                else
                {
                    for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").getChildren())
                    {
                        if (!data.getValue(Profile.class).getThisProfile())
                            profiles.add(data.getValue(Profile.class));
                    }
                    myCallback.onCallback(profiles);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void deleteProfile(String Uid, String profileId){
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.child(Uid).child("profiles").child(profileId).removeValue();
        //change back to default profile
        setDefaultProfile(Uid);
    }

    /**
     * add new profile
     * @param Uid
     * @param name
     * @param dob
     */
    @Override
    public void addProfile(final String Uid, String name, String dob)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        final Profile profile = new Profile(name, dob);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numProfile = (int) dataSnapshot.child(Uid).child("profiles").getChildrenCount();
                userRef.child(Uid).child("profiles").child(Integer.toString(numProfile)).setValue(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * switch from one profile to another profile
     * @param Uid
     * @param profileId
     */
    @Override
    public void changeProfile(final String Uid,final String profileId)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        System.out.println("change profile to " + profileId);
        userRef.child(Uid).child("profiles").child(profileId).child("thisProfile").setValue(true);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numProfiles = (int) dataSnapshot.child(Uid).child("profiles").getChildrenCount();
                if (numProfiles!=1)
                {
                    for (int i=0; i<numProfiles;i++)
                    {
                        if (i==Integer.parseInt(profileId))
                            continue;
                        userRef.child(Uid).child("profiles").child(Integer.toString(i)).child("thisProfile").setValue(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    /**
     * set first profile to be default profile, first profile to be shown when user first enter his account
     * @param Uid
     */
    @Override
    public void setDefaultProfile(final String Uid)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.child(Uid).child("profiles").child("0").child("thisProfile").setValue(true);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numProfiles = (int) dataSnapshot.child(Uid).child("profiles").getChildrenCount();
                if (numProfiles!=1)
                {
                    for (int i=1; i<numProfiles;i++)
                    {
                        userRef.child(Uid).child("profiles").child(Integer.toString(i)).child("thisProfile").setValue(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /**
     * retrieve vaccines in user VaccineLogEntries
     * @param myCallback
     * @param Uid
     */
    @Override
    public void retrieveUserVaccine(final MyCallback myCallback, final String Uid)
    {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        System.out.println("retrieve user vaccine firebase manager");

        //get user vaccine
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Vaccine> vaccineArrayList = new ArrayList<>();
                String profileId="";
                System.out.println("user id in firebase manager: " + Uid);

                //find profile
                System.out.println("number of profiles" +dataSnapshot.child(Uid).child("profiles").getChildrenCount() );
               // ArrayList<Profile> profiles = dataSnapshot.child(Uid).child("profiles").getValue();
                if (dataSnapshot.child(Uid).child("profiles").getChildrenCount()!=1)
                {
                    for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").getChildren())
                    {
                        System.out.println("finding profile....." + data.child("thisProfile").getValue(boolean.class));

                        //then error here how
                        if (data.child("thisProfile").getValue(boolean.class))
                        {
                            profileId = data.getKey();
                            System.out.println("profile id: "+profileId);
                            break;

                        }
                    }
                }
                else
                    profileId = "0";


                for (DataSnapshot data: dataSnapshot.child(Uid).child("profiles").child(profileId).child("vaccineLogEntries").getChildren())
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


    public interface MyCallBackVaccines {
        void onCallback(ArrayList<String> vaccines);
    }

    public void retrieveMandatoryVaccines(final MyCallBackVaccines myCallback, final String countryName){
        database = FirebaseDatabase.getInstance();
        countriesRef = database.getReference("Countries");
        countriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String manVacData;
                manVacData = dataSnapshot.child(countryName).child("mandatoryVaccines").getValue(String.class);
                System.out.println(manVacData);
                ArrayList<String> mandatoryVaccines = new ArrayList<String>(Arrays.asList(manVacData.split("\\s*,\\s*")));
                myCallback.onCallback(mandatoryVaccines);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void retrieveRecommendedVaccines(final MyCallBackVaccines myCallback, final String countryName){
        database = FirebaseDatabase.getInstance();
        countriesRef = database.getReference("Countries");
        countriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String recVacData;
                recVacData = dataSnapshot.child(countryName).child("recommendedVaccines").getValue(String.class);
                System.out.println(recVacData);
                ArrayList<String> recommendedVaccines = new ArrayList<String>(Arrays.asList(recVacData.split("\\s*,\\s*")));
                myCallback.onCallback(recommendedVaccines);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * retrieve all vaccines in the database
     * @param myCallback
     */
    @Override
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

    //interface to faciliate return of value TODO: consider to create another class for these
    public interface MyCallback {
        void onCallback(ArrayList<Vaccine> value);
    }

    public interface MyCallBackVaccine {
        void onCallback(Vaccine value);
    }

    public interface MyCallbackVaccineLog {
        void onCallback(ArrayList<VaccineLogEntry> value);
    }

    public interface MyCallbackProfiles{
        void onCallback(ArrayList<Profile> value);
    }

    public interface MyCallbackString {
        void onCallback(String value);
    }

    public interface MyCallBackCdcLevels {
        void onCallback(ArrayList<CDCThreatLevel> levels);
    }

    public void retrieveCDCThreatLevels(final MyCallBackCdcLevels myCallback, final String countryName){
        database = FirebaseDatabase.getInstance();
        countriesRef = database.getReference("Countries");
        countriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CDCThreatLevel> cdcEntries = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.child(countryName).child("cdcThreatLevels").getChildren())
                {
                    cdcEntries.add(data.getValue(CDCThreatLevel.class));
                }
                myCallback.onCallback(cdcEntries);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
