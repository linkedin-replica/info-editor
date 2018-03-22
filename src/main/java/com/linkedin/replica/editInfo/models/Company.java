package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public class Company {
    private String companyName;
    private String companyProfilePicture;
    private String companyID;
    private String adminUserName;
    private String adminUserID;
    private String industryType;
    private String companyLocation;
    private String companytype;
    private ArrayList<String> posts;
    private ArrayList<String> jobListings;
    public Company(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID,
                   String industryType,String companyLocation,String companytype,ArrayList<String>posts,ArrayList<String> JobListings) {
        this.companyName = companyName;
        this.adminUserName=adminUserName;
        this.companyID = companyID;
        this.companyProfilePicture = companyProfilePicture;
        this.adminUserID = adminUserID;
        this.industryType = industryType;
        this.companyLocation = companyLocation;
        this.companytype = companytype;
        this.posts =posts;
        this.jobListings = JobListings;

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
        this.adminUserID = adminUserID;
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


    public void updatePosts(ArrayList<String> posts) {
        this.posts.addAll(posts);
    }
    public void updateJobListings(ArrayList<String> jobListings){
            this.jobListings.addAll(jobListings);
        }
    public void setPosts(ArrayList<String>posts){
        this.posts = posts;
    }
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyID() {
        return companyID;
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

    public String getCompanyProfilePicture() {
        return companyProfilePicture;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public String getAdminUserID() {
        return adminUserID;
    }


    public String getIndustryType() {
        return industryType;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }


    public ArrayList<String> getPosts() {
        return posts;
    }

    public ArrayList<String> getJobListings() {
        return jobListings;
    }
}