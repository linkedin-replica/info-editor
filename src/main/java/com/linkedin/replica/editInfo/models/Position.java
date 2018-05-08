package com.linkedin.replica.editInfo.models;

public class Position {


    private String title;
    private  String summary;
    private long startDate;
    private long endDate;
    private  boolean isCurrent;
    private String companyId;
    public Position(){};
    public Position(String title, String summary, long startDate, long endDate, boolean isCurrent, String companyName, String companyID) {
        this.title = title;
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCurrent = isCurrent;
        this.companyId = companyID;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public boolean isCurrent() {
        return isCurrent;
    }


    public String getCompanyID() {
        return companyId;
    }
    public void setCompanyID(String companyID) {
        this.companyId = companyID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

}
