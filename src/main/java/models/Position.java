package models;

public class Position {
    private String title;
    private  String summary;
    private String startDate;
    private String endDate;
    private  boolean isCurrent;
    private LightCompany company;

    public Position(String title, String summary, String startDate, String endDate, boolean isCurrent, LightCompany company) {
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCurrent = isCurrent;
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public LightCompany getCompany() {
        return company;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public void setCompany(LightCompany company) {
        this.company = company;
    }
}
