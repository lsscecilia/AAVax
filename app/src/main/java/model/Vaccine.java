package model;

import java.time.Duration;

public class Vaccine {
    private String name;
    private String detail;
    private int numMonths;
    private Boolean oneTime;

    public Vaccine() {
    }

    public Vaccine(String name, String detail, int numMonths, Boolean oneTime) {
        this.name = name;
        this.detail = detail;
        this.numMonths = numMonths;
        this.oneTime = oneTime;
    }

    public int getNumMonths() {
        return numMonths;
    }

    public void setNumMonths(int numMonths) {
        this.numMonths = numMonths;
    }

    public Boolean getOneTime() {
        return oneTime;
    }

    public void setOneTime(Boolean oneTime) {
        this.oneTime = oneTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
