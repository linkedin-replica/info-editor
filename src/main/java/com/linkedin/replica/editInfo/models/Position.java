package com.linkedin.replica.editInfo.models;

public class Position {
    private String title;
    private  String summary;
    private String startDate;
    private String endDate;
    private  boolean isCurrent;
    private String companyName;
    private String companyID;

    public Position(){};

    public Position(String title, String summary, String startDate, String endDate, boolean isCurrent, String companyName, String companyID) {
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCurrent = isCurrent;
        this.companyName = companyName;
        this.companyID = companyID;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
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

}
