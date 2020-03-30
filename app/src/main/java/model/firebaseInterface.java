package model;

import com.example.aavax.ui.FirebaseManager;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;

public interface firebaseInterface {
    public void deleteVaccineLogEntry(final String userId, final String vaccineName);
    public void updateVaccineLogEntry(final String userId, final String vaccineName, final Date dateTaken, final Date dateDue, final String reminder);
    public void addVaccineLogEntry(final String userId,final Date date, final String vaccineName);
    public void retrieveVaccineLogWithReminder(final FirebaseManager.MyCallbackVaccineLog myCallback, final String Uid);
    public void retrieveVaccineLog(final FirebaseManager.MyCallbackVaccineLog myCallback, final String Uid);
    public void setDefaultProfile(final String Uid);
    public void addProfile(final String Uid, String name, String dob);
    public void changeProfile(final String Uid,final String profileId);
    public void deleteProfile(String Uid, String profileId);
    public void retrieveUserVaccine(final FirebaseManager.MyCallback myCallback, final String Uid);
    public void retrieveVaccines(final FirebaseManager.MyCallback myCallback);


}
