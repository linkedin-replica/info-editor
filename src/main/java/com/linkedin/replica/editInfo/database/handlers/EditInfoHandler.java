package com.linkedin.replica.editInfo.database.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.models.CompanyReturn;
import com.linkedin.replica.editInfo.models.UserReturn;

import java.sql.SQLException;
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
    void updateProfile(JsonObject updates);
    void addCV(String userID, String cv);
    void deleteCV(String userID);
    void updateCompany(JsonObject args);
    void deleteSkill(String userId, String skill);
    void createProfile(JsonObject args);
    void insertCompany(JsonObject args) throws SQLException;

    void disconnect();

}
