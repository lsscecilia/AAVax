package entity;
import java.util.ArrayList;

/**
 * Class for Object country
 * Contains country's name, list of required vaccines, list of recommended vaccines
 */
public class Country {
    private String name;
    private ArrayList<Vaccine> vaccineRequired;
    private ArrayList<Vaccine> vaccineRecommended;

    public Country(String name, ArrayList<Vaccine> vaccineRequired, ArrayList<Vaccine> vaccineRecommended) {
        this.name = name;
        this.vaccineRequired = vaccineRequired;
        this.vaccineRecommended = vaccineRecommended;
    }

    public Country(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Vaccine> getVaccineRequired() {
        return vaccineRequired;
    }

    public void setVaccineRequired(ArrayList<Vaccine> vaccineRequired) {
        this.vaccineRequired = vaccineRequired;
    }

    public ArrayList<Vaccine> getVaccineRecommended() {
        return vaccineRecommended;
    }

    public void setVaccineRecommended(ArrayList<Vaccine> vaccineRecommended) {
        this.vaccineRecommended = vaccineRecommended;
    }

}
