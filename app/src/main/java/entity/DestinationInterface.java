package entity;

import com.example.aavax.ui.DestinationMgr;
import com.example.aavax.ui.FirebaseManager;

import java.util.Date;

public interface DestinationInterface {

    public void retrieveMandatoryVaccines(final DestinationMgr.MyCallBackVaccines myCallback, final String countryName);
    public void retrieveRecommendedVaccines(final DestinationMgr.MyCallBackVaccines myCallback, final String countryName);
    public void retrieveCDCThreatLevels(final DestinationMgr.MyCallBackCdcLevels myCallback, final String countryName);
    public void retrieveUserVaccine(final DestinationMgr.MyCallback myCallback, final String Uid);
    public void retrieveVaccineLog(final DestinationMgr.MyCallbackVaccineLog myCallback, final String Uid);



}