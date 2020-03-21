package model;

import java.util.Date;

public class Profile {
    private String name;
    private Date dateOfBirth;
    private VaccineLog vaccineLog;

    public Profile(String name, Date dateOfBirth, VaccineLog vaccineLog) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.vaccineLog = vaccineLog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public VaccineLog getVaccineLog() {
        return vaccineLog;
    }

    public void setVaccineLog(VaccineLog vaccineLog) {
        this.vaccineLog = vaccineLog;
    }
}
