package model;
import java.util.ArrayList;

public class Country {
    private String name;
    private ArrayList<Vaccine> vaccineRequired;
    private ArrayList<Vaccine> vaccineRecommended;
    //private CDCThreatLevel cdcThreatLevel;

    public Country(String name, ArrayList<Vaccine> vaccineRequired, ArrayList<Vaccine> vaccineRecommended) {
        this.name = name;
        this.vaccineRequired = vaccineRequired;
        this.vaccineRecommended = vaccineRecommended;
        //this.cdcThreatLevel = cdcThreatLevel;
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

//    public CDCThreatLevel getCdcThreatLevel() {
//        return cdcThreatLevel;
//    }
//
//    public void setCdcThreatLevel(CDCThreatLevel cdcThreatLevel) {
//        this.cdcThreatLevel = cdcThreatLevel;
//    }
}
