package database;

import models.Company;
import models.User;

import java.util.ArrayList;
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
    void updateCompany(String companyName, String companyID, String companyProfilePicture, String adminUserName, String adminUserID, String industryType, String companyLocation
            , String companytype, ArrayList<String> specialities, ArrayList<String> posts, ArrayList<String>jobListings);
    void disconnect();
    public void insertCompany(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID, String industryType,String companyLocation
            ,String companytype,ArrayList<String> specialities,ArrayList<String> posts,ArrayList<String> jobListings);
}
