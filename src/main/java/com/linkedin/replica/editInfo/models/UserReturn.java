package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

public  class UserReturn  {
    private String _key;
    public String userId;
    public String firstName;
    public String lastName;
    public String headline;
    public String industry;
    public ArrayList<Position> positions;
    public String profilePictureUrl;

    public void set_key(String _key) {
        this._key = _key;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
    }

    public void setFollowedCompanies(ArrayList<LightCompany> followedCompanies) {
        this.followedCompanies = followedCompanies;
    }

    public void setFriendslist(ArrayList<LightUser> friendslist) {
        this.friendsList = friendslist;
    }

    public String get_key() {

        return _key;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public ArrayList<Education> getEducations() {
        return educations;
    }

    public ArrayList<LightCompany> getFollowedCompanies() {
        return followedCompanies;
    }

    public ArrayList<LightUser> getFriendslist() {
        return friendsList;
    }

    public ArrayList<Education> educations;
    public String imageUrl;
    public String cvUrl;
    public ArrayList<String> skills;
    public ArrayList<LightCompany>followedCompanies;
    public ArrayList<LightUser> friendsList;
    public ArrayList<LightPost> bookmarkedPosts;


    public UserReturn(String _key, String userId, String firstName, String lastName, String headline, String industry, ArrayList<Position> positions, ArrayList<Education> educations, String imageUrl, String cvUrl, ArrayList<String> skills, ArrayList<LightCompany> followedCompanies, ArrayList<LightUser> friendslist, ArrayList<LightPost> bookmarkedPosts) {
        this._key = _key;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.headline = headline;
        this.industry = industry;
      //  this.positions = positions;
        this.educations = educations;
        this.imageUrl = imageUrl;
        this.cvUrl = cvUrl;
        this.skills = skills;
        this.followedCompanies = followedCompanies;
        this.friendsList = friendslist;
        this.bookmarkedPosts = bookmarkedPosts;
    }

    public UserReturn(String userId, String firstName, String lastName, String headline, String industry, ArrayList<Position>positions, ArrayList
                      <Education>educations, String imageUrl, String cvUrl, ArrayList<String>skills, ArrayList<LightCompany>followedCompanies,
                      ArrayList<LightUser>friendslist, ArrayList<LightPost>bookmarkedPosts) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.headline = headline;
        this.industry = industry;
      //  this.positions = positions;
        this.educations = educations;
        this.imageUrl = imageUrl;
        this.cvUrl = cvUrl;
        this.skills = skills;
        this.bookmarkedPosts = bookmarkedPosts;
        this._key=userId;
    }

    public UserReturn() {};



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


    public String getImageUrl() {
        return imageUrl;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }



    public ArrayList<LightPost> getBookmarkedPosts() {
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


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }


    public void setBookmarkedPosts(ArrayList<LightPost> bookmarkedPosts) {
        this.bookmarkedPosts = bookmarkedPosts;
    }
}

