package com.linkedin.replica.editInfo.models;

import java.util.ArrayList;

/*


    "profilePictureUrl": "String",
    "cvUrl": "String",
    "skills": "Array" (Strings),
    "friendsList": "Array" (ids),
    "followedCompanies": "Array" (ids)
    "bookmarkedPosts": "Array" (ids)

    "positions": "Array" (Position object (has company name and company picture url.))
 */

public  class UserReturn  {
    public String userId;
    public String firstName;
    public String lastName;
    public String email;
    public String headline;
    public String industry;
    public ArrayList<Position> positions;
    public ArrayList<Education> education;
    public String profilePictureUrl;
    public String cvUrl;
    public ArrayList<String> skills;
    public ArrayList<String> friendList, bookmarkedPosts;

    public UserReturn() { }

    public UserReturn(String userId, String firstName, String lastName, String email, String headline, String industry, ArrayList<Position> positions,
                      ArrayList<Education> education, String profilePictureUrl, String cvUrl, ArrayList<String> skills, ArrayList<String> friendList,
                      ArrayList<String> bookmarkedPosts) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.headline = headline;
        this.industry = industry;
        this.positions = positions;
        this.education = education;
        this.profilePictureUrl = profilePictureUrl;
        this.cvUrl = cvUrl;
        this.skills = skills;
        this.friendList = friendList;
        this.bookmarkedPosts = bookmarkedPosts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getHeadline() {
        return headline;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public String getCvUrl() {
        return cvUrl;
    }
}

