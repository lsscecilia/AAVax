package entity;

public class Vaccine {
    private String name;
    private String date;

    public Vaccine(String name, String date) {
        this.name = name;
        this.date = date;
    private String detail;
    private int numMonths;
    private Boolean oneTime;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
