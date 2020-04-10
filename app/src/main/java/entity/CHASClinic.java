package entity;

import com.google.android.gms.maps.model.LatLng;

/**
 * Class for object CHAS clinic
 * contains clinic's name and coordinates in the form of latitude and longitude
 */
public class CHASClinic {
    private String name;
    private LatLng latLng;


    public CHASClinic(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
