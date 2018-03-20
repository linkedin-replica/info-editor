package com.linkedin.replica.editInfo.models;

public class PersonalInfo {

    private  String phone;
    private  String email;
    private  Location location;
    private String dob;
    private  String website;

    public PersonalInfo(){};

    public PersonalInfo(String phone, String email, Location location, String dob, String website) {
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.dob = dob;
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Location getLocation() {
        return location;
    }

    public String getDob() {
        return dob;
    }

    public String getWebsite() {
        return website;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
