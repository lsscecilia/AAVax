package entity;

public class CDCThreatLevel {
    private int level;
    private String detail;

    public CDCThreatLevel(int level, String detail) {
        this.level = level;
        this.detail = detail;
    }

    public CDCThreatLevel() {}

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
