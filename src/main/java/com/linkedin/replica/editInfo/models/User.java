package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public  class User {
   public String userId;
   public String firstName;
   public String lastName;
   public String headline;
   public String industry;
   public PersonalInfo personalInfo;
   public String numConnections;
   public String numFollowers;
   public String summary;
   public ArrayList<Position> positions;
   public ArrayList<Education> educations;
   public String imageUrl;
   public String cvUrl;
   public ArrayList<String> skills;
   public ArrayList<FriendsList> friendsList;
   public ArrayList<String> bookmarkedPosts;


   public User() {};

    public User(String userId, String firstName, String lastName, String headline, String industry, PersonalInfo personalInfo, String numConnections,
                String numFollowers, String summary, ArrayList<Position> positions, ArrayList<Education> educations,
                String imageUrl, String cvUrl, ArrayList<String> skills,
                ArrayList<FriendsList> friendsList, ArrayList<String> bookmarkedPosts) {
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

    public String getNumConnections() {
        return numConnections;
    }

    public String getNumFollowers() {
        return numFollowers;
    }

    public String getSummary() {
        return summary;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public ArrayList<Education> getEducations() {
        return educations;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public ArrayList<FriendsList> getFriendsList() {
        return friendsList;
    }

    public ArrayList<String> getBookmarkedPosts() {
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

    public void setNumConnections(String numConnections) {
        this.numConnections = numConnections;
    }


    public void setNumFollowers(String numFollowers) {
        this.numFollowers = numFollowers;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void setFriendsList(ArrayList<FriendsList> friendsList) {
        this.friendsList = friendsList;
    }

    public void setBookmarkedPosts(ArrayList<String> bookmarkedPosts) {
        this.bookmarkedPosts = bookmarkedPosts;
    }
}
