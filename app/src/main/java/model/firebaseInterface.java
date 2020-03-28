package model;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;

public interface firebaseInterface {
    public DatabaseReference getCurrentUser(String userId);
    public void storeVaccine(Vaccine vaccine);
    //public void retrieveVaccines();

}
