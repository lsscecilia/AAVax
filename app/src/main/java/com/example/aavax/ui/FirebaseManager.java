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

    /**
     * delete vaccineLogEntry
     * @param userId
     * @param vaccineName
     */

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
                        final VaccineLogEntry vaccineLogEntry = new VaccineLogEntry(date, vaccine);
                        System.out.println(userId + "user IDDDDDD");
                        final String key = userRef.child(userId).child("profiles").child(pId).child("vaccineLogEntries").push().getKey();
                        userRef.child(userId).child("profiles").child(pId).child("vaccineLogEntries").child(key).setValue(vaccineLogEntry);
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
