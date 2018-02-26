package database;

import models.Company;
import models.User;

public interface DatabaseHandler {
    /**
     * Initiate a connection with the database
     */
    void connect();
     Company getCompany(String companyID);
     User getUserProfile(String UserID);
     void addSkill(String userID, String Skill);
    /**
     * Send a new notification to the user
     */
    void addCV(String userID,String cv);
    void deleteCV(String userID);
    void disconnect();
}
