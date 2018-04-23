package com.linkedin.replica.editInfo.database.handlers;

import com.linkedin.replica.editInfo.models.Company;
import com.linkedin.replica.editInfo.models.CompanyReturn;
import com.linkedin.replica.editInfo.models.User;
import com.linkedin.replica.editInfo.models.UserReturn;

import java.util.ArrayList;
import java.util.HashMap;

public interface EditInfoHandler extends DatabaseHandler {

    /**
     * Initiate a connection with the database
     */
    void connect();
    CompanyReturn getCompany(String companyID);
    UserReturn getUserProfile(String UserID);
    void addSkill(String userID, String Skill);
    void updateProfile(HashMap<String, Object> updates);
    void addCV(String userID,String cv);
    void deleteCV(String userID);
    void updateCompany(HashMap<String,Object>args);
    void disconnect();
    public void deleteSkill(String userId,String skill);
    void createProfile(HashMap<String, Object> profileAttributes, String userID);
    public void insertCompany(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID, String industryType,String companyLocation
            ,String companytype,ArrayList<String> specialities,ArrayList<String> posts,ArrayList<String> jobListings);

}
