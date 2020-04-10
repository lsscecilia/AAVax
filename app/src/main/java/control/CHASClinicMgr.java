package control;

import android.app.Activity;

import com.example.aavax.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPoint;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import entity.CHASClinic;
import entity.CHASClinicMgrInterface;

/**
 * implements CHASClinicMgrInterface
 * allows the app to interact with database, acting as an control class for CHASClinic (entity class)
 */
public class CHASClinicMgr implements CHASClinicMgrInterface {


    /**
     * get list of CHAS clinic
     * @param mMap
     * @param activity
     * @return
     */
    public ArrayList<CHASClinic> getClinic(GoogleMap mMap, Activity activity)
    {
        ArrayList<CHASClinic> clinicArrayList = new ArrayList<>();
        GeoJsonLayer layer;
        String desc;
        int index, clinicIndex, endIndex;
        CHASClinic clinic;
        GeoJsonPoint point;
        String details, clinicName;
        try {
            layer = new GeoJsonLayer(mMap, R.raw.chas_clinics, activity.getApplicationContext());
            for (GeoJsonFeature feature : layer.getFeatures()) {

                desc = feature.getProperty("Description");
                index = desc.indexOf("<th>HCI_NAME</th>");
                clinicIndex = index + 22;
                details = desc.substring(clinicIndex);
                endIndex = details.indexOf("<");
                clinicName = details.substring(0, endIndex);

                point = (GeoJsonPoint) feature.getGeometry();
                clinic = new CHASClinic(clinicName, new LatLng(point.getCoordinates().latitude, point.getCoordinates().longitude));
                clinicArrayList.add(clinic);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return clinicArrayList;
    }

    /**
     * set markers of CHAS Clinic on google maps
     * @param mMap
     * @param clinics
     */
    public void setClinicsMarker(GoogleMap mMap, ArrayList<CHASClinic> clinics)
    {
        for (CHASClinic clinic: clinics)
        {
            mMap.addMarker(new MarkerOptions().position(clinic.getLatLng()).title(clinic.getName()));

        }
    }
}
