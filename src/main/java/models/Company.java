package  models;
import java.util.Date;
import models.lightPost;
import  models.lightUser;
import models.lightJobListing;
public class Company {
    private String companyName;
    private int companyID;
    private String companyProfilePicture;
    private String adminUserName;
    private int adminUserID;
    private int adminUserIDMongo;
    private String industryType;
    private String companyLocation;
    private lightUser[] relatedConnections;
    private String aboutUs;
    private String website;
    private String yearFounded;
    private String[] headquarters;
    private String companytype;
    private int companySize;
    private String[] specialities;
    private lightPost[] posts;
    private lightJobListing[] jobListings;
    public Company(String companyName,int companyID,String companyProfilePicture,String adminUserName,int adminUserID,int adminUserIDMongo,
                   String industryType,String companyLocation,lightUser[]relatedConnections,String aboutUs,String website,String yearFounded,
                   String [] headquarters,String companytype,
                   int companySize,String [] specialities,lightPost[] posts,lightJobListing [] lightJobListings) {
        this.companyName = companyName;
        this.companyID = companyID;
        this.companyProfilePicture = companyProfilePicture;
        this.adminUserID = adminUserID;
        this.adminUserIDMongo = adminUserIDMongo;
        this.industryType = industryType;
        this.companyLocation = companyLocation;
        this.relatedConnections = relatedConnections;
        this.aboutUs = aboutUs;
        this.website = website;
        this.yearFounded = yearFounded;
        this.headquarters = headquarters;
        this.companytype = companytype;
        this.companySize = companySize;
        this.specialities = specialities;
        this.posts = posts;
        this.jobListings = lightJobListings;

    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public void setCompanyProfilePicture(String companyProfilePicture) {
        this.companyProfilePicture = companyProfilePicture;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public void setAdminUserID(int adminUserID) {
        this.adminUserID = adminUserID;
    }

    public void setAdminUserIDMongo(int adminUserIDMongo) {
        this.adminUserIDMongo = adminUserIDMongo;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public void setRelatedConnections(lightUser[] relatedConnections) {
        this.relatedConnections = relatedConnections;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setYearFounded(String yearFounded) {
        this.yearFounded = yearFounded;
    }

    public void setHeadquarters(String[] headquarters) {
        this.headquarters = headquarters;
    }

    public void setCompanytype(String companytype) {
        this.companytype = companytype;
    }

    public void setCompanySize(int companySize) {
        this.companySize = companySize;
    }

    public void setSpecialities(String[] specialities) {
        this.specialities = specialities;
    }

    public void setPosts(lightPost[] posts) {
        this.posts = posts;
    }

    public void setJobListings(lightJobListing[] jobListings) {
        this.jobListings = jobListings;
    }

    public String getCompanyName() {

        return companyName;
    }

    public int getCompanyID() {
        return companyID;
    }

    public String getCompanyProfilePicture() {
        return companyProfilePicture;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public int getAdminUserID() {
        return adminUserID;
    }

    public int getAdminUserIDMongo() {
        return adminUserIDMongo;
    }

    public String getIndustryType() {
        return industryType;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public lightUser[] getRelatedConnections() {
        return relatedConnections;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public String getWebsite() {
        return website;
    }

    public String getYearFounded() {
        return yearFounded;
    }

    public String[] getHeadquarters() {
        return headquarters;
    }

    public String getCompanytype() {
        return companytype;
    }

    public int getCompanySize() {
        return companySize;
    }

    public String[] getSpecialities() {
        return specialities;
    }

    public lightPost[] getPosts() {
        return posts;
    }

    public lightJobListing[] getJobListings() {
        return jobListings;
    }
}