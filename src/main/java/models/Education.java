package models;

public class Education {
    private String schoolName;
    private String fieldOfStudy;
    private String startDate;
    private String endDate;
    private String degree;

    public  Education(){};

    public Education(String schoolName, String fieldOfStudy, String startDate, String endDate, String degree) {
        this.schoolName = schoolName;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.degree = degree;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
