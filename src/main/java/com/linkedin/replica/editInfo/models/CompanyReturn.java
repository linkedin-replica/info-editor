package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public class CompanyReturn {
    private String _key;
    public String companyName;
    public String companyProfilePicture;
    public String companyId;
    public String adminUserName;
    public String adminUserID;
    public String industryType;
    public String companyLocation;
    public String companytype;
    String aboutUs;

    public String get_key() {
        return _key;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public ArrayList<LightPost> getPosts() {
        return posts;
    }

    public ArrayList<LightUser> getJobListing() {
        return jobListing;
    }

    public ArrayList<LightPost> posts;
    public ArrayList<LightUser> jobListing;
    public CompanyReturn(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID,
                   String industryType,String companyLocation,String companytype,ArrayList<String>posts,ArrayList<String> JobListings) {
        this.companyName = companyName;
        this.adminUserName=adminUserName;
        this.companyId = companyID;
        this.companyProfilePicture = companyProfilePicture;
        this.adminUserID = adminUserID;
        this.industryType = industryType;
        this.companyLocation = companyLocation;
        this.companytype = companytype;
    }
    public CompanyReturn(){
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




    public void setCompanyID(String companyID) {
        this.companyId = companyID;
    }

    public String getCompanyID() {
        return companyId;
    }

    public String getCompanytype() {
        return companytype;
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

}