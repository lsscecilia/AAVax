package model;
import java.time.LocalDate;
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

    public VaccineLogEntry(Date dateTaken, Vaccine vaccine) {
        this.dateTaken = dateTaken;
        this.vaccine = vaccine;
        this.nextDue = new Date(0,0,0);
        this.reminder = false;
    }

    public VaccineLogEntry() {
    }


    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setNextDue(Date nextDue) {
        this.nextDue = nextDue;
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

    public boolean getReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
