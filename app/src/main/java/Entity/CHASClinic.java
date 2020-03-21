package Entity;

public class CHASClinic {
    private String name;
    private String openingHours;
    private Location location;
    //private String detail;


    public CHASClinic(String name, String openingHours, Location location) {
        this.name = name;
        this.openingHours = openingHours;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
