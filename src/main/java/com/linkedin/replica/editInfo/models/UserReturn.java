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
    public ArrayList<Education> educations;
    public String imageUrl;
    public String cvUrl;
    public ArrayList<String> skills;
    public ArrayList<LightCompany>followedCompanies;
    public ArrayList<LightUser> friendsList;
    public ArrayList<LightPost> bookmarkedPosts;

    public UserReturn() {};

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

