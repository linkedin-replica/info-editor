package com.linkedin.replica.editInfo.database.handlers;

import com.linkedin.replica.editInfo.models.Company;
import com.linkedin.replica.editInfo.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface EditInfoHandler extends DatabaseHandler {

    /**
     * Initiate a connection with the database
     */
    void connect();
    Company getCompany(String companyID);
    User getUserProfile(String UserID);
    void addSkill(String userID, String Skill);
    void updateProfile(HashMap<String, String> updates, String UserId);
    void addCV(String userID,String cv);
    void deleteCV(String userID);
    void updateCompany(String companyName, String companyID, String companyProfilePicture, String adminUserName, String adminUserID, String industryType, String companyLocation
            , String companytype, ArrayList<String> specialities, ArrayList<String> posts, ArrayList<String>jobListings);
    void disconnect();
    void createProfile(HashMap<String, String> profileAttributes, String userID);
    public void insertCompany(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID, String industryType,String companyLocation
            ,String companytype,ArrayList<String> specialities,ArrayList<String> posts,ArrayList<String> jobListings);

}
