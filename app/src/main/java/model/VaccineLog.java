package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class VaccineLog {
    private Date lastUpdated;
    private ArrayList<VaccineLogEntry> vaccineLogEntries;
    //private String profile;  kiv do we need this?


    public VaccineLog(Date lastUpdated, ArrayList<VaccineLogEntry> vaccineLogEntries) {
        this.lastUpdated = lastUpdated;
        this.vaccineLogEntries = vaccineLogEntries;
    }

    public VaccineLog()
    {
        //how u gonna do this
        lastUpdated = new Date();
        vaccineLogEntries = new ArrayList<>();
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


}
