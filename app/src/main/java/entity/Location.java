package entity;
import com.google.android.gms.maps.model.LatLng;

public class Location {
    private int postalCode;
    private String streetName;
    private String block;
    private String unit;
    private LatLng latLng;

    public Location(int postalCode, String streetName, String block, String unit, LatLng latLng) {
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.block = block;
        this.unit = unit;
        this.latLng = latLng;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
