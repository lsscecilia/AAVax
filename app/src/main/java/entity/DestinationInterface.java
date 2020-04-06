package entity;

import control.DestinationMgr;

public interface DestinationInterface {

    public void retrieveMandatoryVaccines(final DestinationMgr.MyCallBackVaccines myCallback, final String countryName);
    public void retrieveRecommendedVaccines(final DestinationMgr.MyCallBackVaccines myCallback, final String countryName);
    public void retrieveCDCThreatLevels(final DestinationMgr.MyCallBackCdcLevels myCallback, final String countryName);
    public void retrieveVaccineLog(final DestinationMgr.MyCallbackVaccineLog myCallback, final String Uid);



}
