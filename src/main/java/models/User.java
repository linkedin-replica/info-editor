package models;


public  class User {
   private  String userId;
   private String firstName;
   private String lastName;
   private String headline;
   private String industry;
   private PersonalInfo personalInfo;
   private int numConnections;
   private int numFollowers;
   private String summary;
   private Position[] positions;
   private Education[] educations;
   private String imageUrl;
   private String cvUrl;
   private String[] skills;
   private FriendsList[] friendsList;
   private String[] bookmarkedPosts;


    public User(String userId, String firstName, String lastName, String headline, String industry,
                PersonalInfo personalInfo, int numConnections, int numFollowers, String summary,
                Position[] positions, Education[] educations, String imageUrl, String cvUrl, String[] skills,
                FriendsList[] friendsList, String[] bookmarkedPosts) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.headline = headline;
        this.industry = industry;
        this.personalInfo = personalInfo;
        this.numConnections = numConnections;
        this.numFollowers = numFollowers;
        this.summary = summary;
        this.positions = positions;
        this.educations = educations;
        this.imageUrl = imageUrl;
        this.cvUrl = cvUrl;
        this.skills = skills;
        this.friendsList = friendsList;
        this.bookmarkedPosts = bookmarkedPosts;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHeadline() {
        return headline;
    }

    public String getIndustry() {
        return industry;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public int getNumConnections() {
        return numConnections;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public String getSummary() {
        return summary;
    }

    public Position[] getPositions() {
        return positions;
    }

    public Education[] getEducations() {
        return educations;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public String[] getSkills() {
        return skills;
    }

    public FriendsList[] getFriendsList() {
        return friendsList;
    }

    public String[] getBookmarkedPosts() {
        return bookmarkedPosts;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public void setNumConnections(int numConnections) {
        this.numConnections = numConnections;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPositions(Position[] positions) {
        this.positions = positions;
    }

    public void setEducations(Education[] educations) {
        this.educations = educations;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public void setFriendsList(FriendsList[] friendsList) {
        this.friendsList = friendsList;
    }

    public void setBookmarkedPosts(String[] bookmarkedPosts) {
        this.bookmarkedPosts = bookmarkedPosts;
    }
}
