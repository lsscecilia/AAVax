package entity;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

public interface CHASClinicMgrInterface {
    public ArrayList<CHASClinic> getClinic(GoogleMap mMap, Activity activity);
    public void setClinicsMarker(GoogleMap mMap, ArrayList<CHASClinic> clinics);
}
