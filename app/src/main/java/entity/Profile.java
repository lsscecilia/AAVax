package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for Object Profile
 * Contains profile's name , date of birth, list of vaccine log entry and boolean var to indicate if this is current profile
 */
public class Profile {
    private String name;
    private String dateOfBirth;
    private List<VaccineLogEntry> vaccineLogEntries;
    private boolean thisProfile;


    public Profile() {
    }

    public Profile(String name, String dateOfBirth, ArrayList<VaccineLogEntry> vaccineLogEntries) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.vaccineLogEntries = vaccineLogEntries;
        this.thisProfile = true;
    }

    public Profile(String name, String dateOfBirth)
    {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        vaccineLogEntries = new ArrayList<>();
        thisProfile = true;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<VaccineLogEntry> getVaccineLogEntries() {
        return vaccineLogEntries;
    }

    public void setVaccineLogEntries(List<VaccineLogEntry> vaccineLogEntries) {
        this.vaccineLogEntries = vaccineLogEntries;
    }

    public boolean getThisProfile() {
        return thisProfile;
    }

    public void setThisProfile(boolean thisProfile) {
        this.thisProfile = thisProfile;
    }
}
