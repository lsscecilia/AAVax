package model;

import java.util.ArrayList;
import java.util.Date;

public class VaccineLog {
    private Date lastUpdated;
    private ArrayList<VaccineLogEntry> vaccineLogEntries;
    private String profile;

    public VaccineLog(Date lastUpdated, ArrayList<VaccineLogEntry> vaccineLogEntries, String profile) {
        this.lastUpdated = lastUpdated;
        this.vaccineLogEntries = vaccineLogEntries;
        this.profile = profile;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<VaccineLogEntry> getVaccineLogEntries() {
        return vaccineLogEntries;
    }

    public void setVaccineLogEntries(ArrayList<VaccineLogEntry> vaccineLogEntries) {
        this.vaccineLogEntries = vaccineLogEntries;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
