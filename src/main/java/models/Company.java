import java.util.Date;

public class Company {
    private String companyName;
    private int companyID
    private String companyProfilePicture;
    private String adminUserName;
    private int adminUserID;
    private int adminUserIDMongo;
    private String industryType;
    private String companyLocation;
    private Connection[] relatedConnections;
    private String aboutUs;
    private String website;
    private Date yearFounded;
    private String[] headquarters;
    private String companytype;
    private int companySize;
    private String[] specialities;
    private Post[] posts;
    private Job[] jobListings;

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

    public void setRelatedConnections(Connection[] relatedConnections) {
        this.relatedConnections = relatedConnections;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setYearFounded(Date yearFounded) {
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

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    public void setJobListings(Job[] jobListings) {
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

    public Connection[] getRelatedConnections() {
        return relatedConnections;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public String getWebsite() {
        return website;
    }

    public Date getYearFounded() {
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

    public Post[] getPosts() {
        return posts;
    }

    public Job[] getJobListings() {
        return jobListings;
    }
}