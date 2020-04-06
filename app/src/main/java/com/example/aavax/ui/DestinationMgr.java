package com.example.aavax.ui;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.View;
import com.example.aavax.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import entity.CDCThreatLevel;
import entity.DestinationInterface;
import entity.Vaccine;
import entity.VaccineLogEntry;

public class DestinationMgr extends Fragment implements DestinationInterface {

    private String[] popular_countries;
    private String[] all_countries;
    private String[] asia_countries;
    private String[] north_america_countries;
    private String[] south_america_countries;
    private String[] europe_countries;
    private String[] africa_countries;
    private String[] oceania_countries;

    private FirebaseDatabase database;
    private DatabaseReference countriesRef;
    private DatabaseReference vaccinesRef;
    private DatabaseReference userRef;



    public String[] getPopularCountries(String message, Resources res){
        switch(message){
            case "Asia":
                popular_countries = res.getStringArray(R.array.asia_popular_countries);
                break;
            case "Europe":
                popular_countries = res.getStringArray(R.array.europe_popular_countries);
                break;
            case "North America":
                popular_countries = res.getStringArray(R.array.north_america_popular_countries);
                break;
            case "South America":
                popular_countries = res.getStringArray(R.array.south_america_popular_countries);
                break;
            case "Oceania":
                popular_countries = res.getStringArray(R.array.oceania_popular_countries);
                break;
            case "Africa":
                popular_countries = res.getStringArray(R.array.africa_popular_countries);
                break;
        }
        return popular_countries;
    }

    public String[] getAllCountries(String message, Resources res){
        switch(message){
            case "Asia":
                all_countries = res.getStringArray(R.array.asia_all_countries);
                break;
            case "Europe":
                all_countries = res.getStringArray(R.array.europe_all_countries);
                break;
            case "North America":
                all_countries = res.getStringArray(R.array.north_america_all_countries);
                break;
            case "South America":
                all_countries = res.getStringArray(R.array.south_america_all_countries);
                break;
            case "Oceania":
                all_countries = res.getStringArray(R.array.oceania_popular_countries);
                break;
            case "Africa":
                all_countries = res.getStringArray(R.array.africa_all_countries);
                break;
        }
        return all_countries;
    }

    public int findImage(View view, String countryName, Resources res){
        asia_countries = res.getStringArray(R.array.asia_all_countries);
        north_america_countries = res.getStringArray(R.array.north_america_all_countries);
        south_america_countries = res.getStringArray(R.array.south_america_all_countries);
        europe_countries = res.getStringArray(R.array.europe_all_countries);
        africa_countries = res.getStringArray(R.array.africa_all_countries);
        oceania_countries = res.getStringArray(R.array.oceania_popular_countries);

        if (Arrays.asList(asia_countries).contains(countryName))
            return R.drawable.asia;
        else if (Arrays.asList(north_america_countries).contains(countryName))
            return R.drawable.north_america;
        else if (Arrays.asList(south_america_countries).contains(countryName))
            return R.drawable.south_america;
        else if (Arrays.asList(europe_countries).contains(countryName))
            return R.drawable.europe;
        else if (Arrays.asList(africa_countries).contains(countryName))
            return R.drawable.africa;
        else if (Arrays.asList(oceania_countries).contains(countryName))
            return R.drawable.oceania;
        else
            return R.drawable.antarctica;

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

    @Override
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

    @Override
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


    @Override
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

    public interface MyCallback {
        void onCallback(ArrayList<Vaccine> value);
    }

    public interface MyCallbackVaccineLog {
        void onCallback(ArrayList<VaccineLogEntry> value);
    }

    public interface MyCallbackString {
        void onCallback(String value);
    }

    public interface MyCallBackCdcLevels {
        void onCallback(ArrayList<CDCThreatLevel> levels);
    }


}
