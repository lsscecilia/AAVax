package entity;


import java.util.Date;

import control.VaccineLogMgr;

public interface VaccineLogMgrInterface {
    public void deleteVaccineLogEntry(final String userId, final String vaccineName);
    public void updateVaccineLogEntry(final String userId, final String vaccineName, final Date dateTaken, final Date dateDue, final String reminder);
    public void addVaccineLogEntry(final String userId,final Date date, final String vaccineName);
    public void retrieveVaccineLogWithReminder(final VaccineLogMgr.MyCallbackVaccineLog myCallback, final String Uid);
    public void retrieveVaccineLog(final VaccineLogMgr.MyCallbackVaccineLog myCallback, final String Uid);

    public void retrieveUserVaccine(final VaccineLogMgr.MyCallback myCallback, final String Uid);
    public void retrieveVaccines(final VaccineLogMgr.MyCallback myCallback);
}
