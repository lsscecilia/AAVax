package com.example.aavax.ui;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import model.Profile;
import model.Vaccine;
import model.VaccineLogEntry;
import model.firebaseInterface;

public class FirebaseManager implements firebaseInterface {

    private FirebaseDatabase database;
    private DatabaseReference vaccinesRef;
    private DatabaseReference userRef;
    private ArrayList<Vaccine> vaccines;
    //private String profileId;

    public FirebaseManager() {
    }

    /**
     * delete vaccineLogEntry
     * @param userId
     * @param vaccineName
     */
    @Override
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

        vaccinesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vaccine vaccine = dataSnapshot.child(vaccineName).getValue(Vaccine.class);
                final VaccineLogEntry vaccineLogEntry = new VaccineLogEntry(date, vaccine);
                System.out.println(userId + "user IDDDDDD");
                final String key = userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").push().getKey();
                userRef.child(userId).child("profiles").child("0").child("vaccineLogEntries").child(key).setValue(vaccineLogEntry);
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
                        System.out.println("finding profile....." + data.getValue(Profile.class).getName());

                        //then error here how
                        if (data.getValue(Profile.class).getThisProfile())
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
}
