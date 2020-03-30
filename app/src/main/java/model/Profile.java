package model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

import java.util.Date;

public class Profile {
    private String name;
    private Date dateOfBirth;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat date = new SimpleDateFormat("DD-MM-YYYY");

    public Profile(String name, Date dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {

        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {

        this.dateOfBirth = dateOfBirth;
    }


}
