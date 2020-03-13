package control;

public class Vaccine {

    private String name;
    private String details;

    public Vaccine(String name, String details) {
        setName(name);
        setDetails(details);
    }

    // setter
    private void setName(String name) {
        this.name = name;
    }

    private void setDetails(String details) {
        this.details = details;
    }

    // getter
    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }
}
