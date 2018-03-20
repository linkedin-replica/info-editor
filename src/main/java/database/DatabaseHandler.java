package database;

import models.Company;
import models.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public interface DatabaseHandler {
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
     void createProfile(HashMap<String, String> profileAttributes, String UserId);

    void disconnect();
}
