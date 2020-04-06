package entity;

import com.example.aavax.ui.FirebaseManager;

import java.util.Date;

public interface FirebaseInterface {

    public void changePassword(String password, String uId);
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

    public void retrieveCurrentProfileName(final FirebaseManager.MyCallbackString myCallback, final String Uid);
    public void retrieveEmailAdress(final FirebaseManager.MyCallbackString myCallback, String Uid);
    public void retrieveSubprofileNameAndID(final FirebaseManager.MyCallbackHashMap myCallback, final String Uid);
    public void retrieveProfile(FirebaseManager.MyCallbackProfile myCallback, String uId, String pId);
    public void editProfile(final String uId,String name);
    public void editProfile(final String uId,String pId ,String name, String dob);

//    public void retrieveMandatoryVaccines(final FirebaseManager.MyCallBackVaccines myCallback, final String countryName);
//    public void retrieveRecommendedVaccines(final FirebaseManager.MyCallBackVaccines myCallback, final String countryName);
//    public void retrieveCDCThreatLevels(final FirebaseManager.MyCallBackCdcLevels myCallback, final String countryName);


}
