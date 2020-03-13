package control;

import java.util.Date;

public class VaccineLogEntry {

    private Date dateTaken;
    private Vaccine vaccine;

    VaccineLogEntry(Date dateTaken, Vaccine vaccine) {
        this.dateTaken = dateTaken;
        this.vaccine = vaccine;
    }
}
