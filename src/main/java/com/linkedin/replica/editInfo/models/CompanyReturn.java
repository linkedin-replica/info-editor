package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public class CompanyReturn {
    public String companyName;
    public String companyProfilePicture;
    public String companyId;
    public String userId;
    public String adminUserId;
    public String industryType;
    public String companyLocation;
    public String companytype;
    String aboutus;
    public ArrayList<LightPost> posts;
    public ArrayList<LightUser> jobListing;

    public CompanyReturn(){
    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }
    public String getAboutUs() {
        return aboutus;
    }
    public ArrayList<LightPost> getPosts() {
        return posts;
    }
    public ArrayList<LightUser> getJobListing() {
        return jobListing;
    }

    public void setCompanyProfilePicture(String companyProfilePicture) {
        this.companyProfilePicture = companyProfilePicture;
    }

    public void setAdminUserName(String adminUserName) {
        this.userId = adminUserName;
    }

    public void setAdminUserID(String adminUserID) {
        this.adminUserId = adminUserID;
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
        return userId;
    }

    public String getAdminUserID() {
        return adminUserId;
    }


    public String getIndustryType() {
        return industryType;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

}