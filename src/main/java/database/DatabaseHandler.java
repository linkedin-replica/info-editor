package database;

import models.Company;

public interface DatabaseHandler {
    /**
     * Initiate a connection with the database
     */
    void connect();
     Company getCompany(String companyID);
    /**
     * Send a new notification to the user
     */
    void addCV(String userID,String cv);
    void deleteCV(String userID);
    void disconnect();
}
