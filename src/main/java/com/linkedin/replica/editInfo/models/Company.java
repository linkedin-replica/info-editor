package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public class Company {
    private String _key;
    public String companyName;
    public String companyProfilePicture;
    public String companyId;
    public String adminUserName;
    public String userId;
    public String industryType;
    public String companyLocation;
    public String companytype;
    String aboutUs;
    public ArrayList<String> posts;
    public ArrayList<String> jobListings;
    public ArrayList<String> Posts;
    public ArrayList<String> joblistings;
    public Company(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID,
                   String industryType,String companyLocation,String companytype,ArrayList<String>posts,ArrayList<String> JobListings) {
        this.companyName = companyName;
        this.adminUserName=adminUserName;
        this.companyId = companyID;
        this.companyProfilePicture = companyProfilePicture;
        this.userId = adminUserID;
        this.industryType = industryType;
        this.companyLocation = companyLocation;
        this.companytype = companytype;
    }
    public Company(){
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public void setCompanyProfilePicture(String companyProfilePicture) {
        this.companyProfilePicture = companyProfilePicture;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public void setAdminUserID(String adminUserID) {
        this.userId = adminUserID;
    }


    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }


    public void setCompanytype(String companytype) {
        this.companytype = companytype;
    }




    public void setPosts(ArrayList<String >posts){
        this.posts = posts;
    }
    public void setCompanyID(String companyID) {
        this.companyId = companyID;
    }

    public String getCompanyID() {
        return companyId;
    }

    public String getCompanytype() {
        return companytype;
    }

    public void setJobListings(ArrayList<String> jobListings) {
        this.jobListings =jobListings;
    }

    public String getCompanyName() {

        return companyName;
    }

    public ArrayList<String> getPosts() {
        return posts;
    }

    public String getCompanyProfilePicture() {
        return companyProfilePicture;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public String getAdminUserID() {
        return userId;
    }


    public String getIndustryType() {
        return industryType;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

}