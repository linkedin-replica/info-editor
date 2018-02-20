package database;


import java.util.List;

public interface DatabaseHandler {
    /**
     * Initiate a connection with the database
     */
    void connect();

    /**
     * Send a new notification to the user
     */
    void disconnect();
}
