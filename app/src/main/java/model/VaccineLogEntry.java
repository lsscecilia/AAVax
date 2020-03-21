package model;
import java.util.Date;

public class VaccineLogEntry {
    private Date dateTaken;
    private Vaccine vaccine;
    private Date nextDue;
    private boolean reminder;

    public VaccineLogEntry(Date dateTaken, Vaccine vaccine, Date nextDue, boolean reminder) {
        this.dateTaken = dateTaken;
        this.vaccine = vaccine;
        this.nextDue = nextDue;
        this.reminder = reminder;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Date getNextDue() {
        return nextDue;
    }

    public void setNextDue(Date nextDue) {
        this.nextDue = nextDue;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
