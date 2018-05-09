package com.linkedin.replica.editInfo.models;

public class Education {
    private String schoolName;
    private String fieldOfStudy;
    private long startDate;
    private long endDate;
    private String degree;
    private boolean isCurrent ;
    public  Education(){};

    public Education(String schoolName, String fieldOfStudy, long startDate, long endDate, String degree) {
        this.schoolName = schoolName;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.degree = degree;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public String getDegree() {
        return degree;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
