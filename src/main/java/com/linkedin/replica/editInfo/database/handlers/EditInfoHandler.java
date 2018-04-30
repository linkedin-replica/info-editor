package com.linkedin.replica.editInfo.database.handlers;

import com.linkedin.replica.editInfo.models.CompanyReturn;
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

    String addSkill(String userID, String Skill);

    String updateProfile(HashMap<String, Object> updates);

    String addCV(String userID, String cv);

    String deleteCV(String userID);

    String updateCompany(HashMap<String,Object>args);

    void disconnect();

    String deleteSkill(String userId, String skill);

    String createProfile(HashMap<String, Object> profileAttributes, String userID);

    String insertCompany(String companyName, String companyID, String companyProfilePicture, String adminUserName, String adminUserID,
                         ArrayList<String> specialities);
}
