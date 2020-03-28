package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Profile {
    private String name;
    private String dateOfBirth;
    private List<VaccineLogEntry> vaccineLogEntries;
   // private VaccineLog vaccineLog;


    public Profile(String name, String dateOfBirth, ArrayList<VaccineLogEntry> vaccineLogEntries) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.vaccineLogEntries = vaccineLogEntries;
    }

    public Profile(String name, String dateOfBirth)
    {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        vaccineLogEntries = new ArrayList<>();
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
}
