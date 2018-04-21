package com.linkedin.replica.editInfo.database;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.linkedin.replica.editInfo.config.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class DatabaseSeed {

    private static Configuration config;
    private ArangoDB arangoDriver;
    private com.linkedin.replica.editInfo.database.handlers.DatabaseHandler dbHandler;

    public DatabaseSeed() throws FileNotFoundException, IOException {
        config = Configuration.getInstance();
    }


    /**
     * Method to get json data from json file.
     *
     * @param filePath path to the json file containing the json data
     * @return Iterator of json data
     * @throws IOException
     */
    public static JSONArray getJSONData(String filePath) throws IOException, ParseException, ParseException {
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(new Scanner(new File(filePath)).useDelimiter("\\Z").next());
    }

    /**
     * feed the database with users specified in json file users.json
     *
     * @throws IOException
     * @throws ParseException
     */
    public static void insertUsers() throws IOException, ParseException {

        ArangoDB arangoDB = DatabaseConnection.getDBConnection().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.users.name");
        try {
            arangoDB.db(dbName).createCollection(collectionName);
        } catch (ArangoDBException exception) {
            if (exception.getErrorNum() == 1228) {
                arangoDB.createDatabase(dbName);
                arangoDB.db(dbName).createCollection(collectionName);
            } else if (exception.getErrorNum() == 1207) {
                //NoOP
            } else {
                throw exception;
            }
        }

        int id = 0;
        BaseDocument userDocument;
        JSONArray users = getJSONData("src/main/resources/data/users.json");
        for (Object user : users) {
            JSONObject userObject = (JSONObject) user;
            userDocument = new BaseDocument();
            userDocument.setKey(userObject.get("userId") + "");
            userDocument.addAttribute("userId", userObject.get("userId"));
            userDocument.addAttribute("firstName", userObject.get("firstName"));
            userDocument.addAttribute("lastName", userObject.get("lastName"));
            userDocument.addAttribute("headline", userObject.get("headline"));
            userDocument.addAttribute("industry", userObject.get("industry"));
            userDocument.addAttribute("skills", userObject.get("skills"));
            userDocument.addAttribute("numConnections", userObject.get("numConnections"));
            userDocument.addAttribute("friendsList", userObject.get("friendsList"));
            arangoDB.db(dbName).collection(collectionName).insertDocument(userDocument);
            System.out.println("New user document insert with key = " + userDocument.getId());
        }
    }

    /**
     * Insert articles specified in articles.json file to the database collection articles
     *
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void insertArticles() throws IOException, ClassNotFoundException, SQLException, ParseException {

        ArangoDB arangoDB = DatabaseConnection.getDBConnection().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.articles.name");

        try {
            arangoDB.db(dbName).
                    createCollection(collectionName);

        } catch (ArangoDBException exception) {
            //database not found exception
            if (exception.getErrorNum() == 1228) {
                arangoDB.createDatabase(dbName);
                arangoDB.db(dbName).createCollection(collectionName);
            } else if (exception.getErrorNum() == 1207) { // duplicate name error
                // NoOP
            } else {
                throw exception;
            }
        }
        BaseDocument articleDocument;
        JSONArray articles = getJSONData("src/main/resources/data/articles.json");
        for (Object article : articles) {
            JSONObject articleObject = (JSONObject) article;
            articleDocument = new BaseDocument();
            articleDocument.addAttribute("postId", articleObject.get("postId"));
            articleDocument.addAttribute("authorId", articleObject.get("authorId"));
            articleDocument.addAttribute("headline", articleObject.get("headline"));
            articleDocument.addAttribute("timestamp", articleObject.get("timestamp"));
            articleDocument.addAttribute("text", articleObject.get("text"));
            articleDocument.addAttribute("likesCount", articleObject.get("likesCount"));
            articleDocument.addAttribute("commentsCount", articleObject.get("commentsCount"));
            articleDocument.addAttribute("shares", articleObject.get("shares"));
            arangoDB.db(dbName).collection(collectionName).insertDocument(articleDocument);
            System.out.println("New article document insert with key = " + articleDocument.getId());
        }
    }

    /**
     * Insert jobs specified in jobs.json file to the database collection jobs
     *
     * Insert jobs specified in jobs.json file to the database collection jobs
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void insertJobs() throws IOException, ClassNotFoundException, SQLException, ParseException {

        ArangoDB arangoDB = DatabaseConnection.getDBConnection().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.jobs.name");
        try {
            arangoDB.db(dbName).
                    createCollection(collectionName);

        } catch (ArangoDBException exception) {
            //database not found exception
            if (exception.getErrorNum() == 1228) {
                arangoDB.createDatabase(dbName);
                arangoDB.db(dbName).createCollection(collectionName);
            } else if (exception.getErrorNum() == 1207) { // duplicate name error
                // NoOP
            } else {
                throw exception;
            }
        }
        int id = 0;
        BaseDocument jobDocument;
        JSONArray jobs = getJSONData("src/main/resources/data/jobs.json");
        for (Object job : jobs) {
            JSONObject jobObject = (JSONObject) job;
            jobDocument = new BaseDocument();
//            jobDocument.addAttribute("JobID", id++);
            jobDocument.addAttribute("JobID", id++);
            jobDocument.addAttribute("positionName", jobObject.get("positionName"));
            jobDocument.addAttribute("companyName", jobObject.get("companyName"));
            jobDocument.addAttribute("companyId", jobObject.get("companyId"));
            jobDocument.addAttribute("requiredSkills", jobObject.get("requiredSkills"));
            arangoDB.db(dbName).collection(collectionName).insertDocument(jobDocument);
            System.out.println("New job document insert with key = " + jobDocument.getId());
        }
    }

    public static void insertCompanies() throws IOException, ClassNotFoundException, SQLException, ParseException {

        ArangoDB arangoDB = DatabaseConnection.getDBConnection().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.companies.name");

        try {
            arangoDB.db(dbName).
                    createCollection(collectionName);

        } catch (ArangoDBException exception) {
            //database not found exception
            if (exception.getErrorNum() == 1228) {
                arangoDB.createDatabase(dbName);
                arangoDB.db(dbName).createCollection(collectionName);
            } else if (exception.getErrorNum() == 1207) { // duplicate name error
                // NoOP
            } else {
                throw exception;
            }
        }
        BaseDocument companyDocument;
        JSONArray companies = getJSONData("src/main/resources/data/companies.json");
        for (Object company : companies) {
            JSONObject companyObject = (JSONObject) company;
            companyDocument = new BaseDocument();
            companyDocument.setKey((String)companyObject.get("companyID"));
            companyDocument.addAttribute("companyName", companyObject.get("companyName"));
            companyDocument.addAttribute("companyID", companyObject.get("companyID"));
            companyDocument.addAttribute("companyProfilePicture", companyObject.get("companyProfilePicture"));
            companyDocument.addAttribute("adminUserName", companyObject.get("adminUserName"));
            companyDocument.addAttribute("adminUserID", companyObject.get( "adminUserID"));
            companyDocument.addAttribute("industryType", companyObject.get("industryType"));
            companyDocument.addAttribute("companyLocation", companyObject.get("companyLocation"));
            companyDocument.addAttribute("companytype",companyObject.get("companytype"));
            companyDocument.addAttribute("relatedConnections", companyObject.get( "relatedConnections"));
            companyDocument.addAttribute("posts",companyObject.get("posts"));
            companyDocument.addAttribute("jobListings", companyObject.get( "jobListings"));
            arangoDB.db(dbName).collection(collectionName).insertDocument(companyDocument);
            System.out.println("New article document insert with key = " + companyDocument.getId());


        }
    }
    /**
     * Delete jobs collection from the database if it exists
     *
     * @throws ArangoDBException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */

    public static void deleteAllJobs() throws ArangoDBException, IOException{
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.jobs.name");
        try {
            DatabaseConnection.getDBConnection().getArangoDriver().db(dbName).collection(collectionName).drop();
        } catch (ArangoDBException exception) {
            if (exception.getErrorNum() == 1228) {
                System.out.println("Database not found");
            }
        }
        System.out.println("Jobs collection is dropped");
    }



    /**
     * Delete users collection from the database if it exists
     * @throws ArangoDBException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public static void deleteAllUsers() throws ArangoDBException, IOException{
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.users.name");
        try {
            DatabaseConnection.getDBConnection().getArangoDriver().db(dbName).collection(collectionName).drop();
        } catch(ArangoDBException exception) {
            if(exception.getErrorNum() == 1228) {
                System.out.println("Database not found");
            }
        }
    }
    public static void deleteAllCompanies() throws ArangoDBException, IOException{
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        try {
            DatabaseConnection.getDBConnection().getArangoDriver().db(dbName).collection(collectionName).drop();
        } catch(ArangoDBException exception) {
            if(exception.getErrorNum() == 1228) {
                System.out.println("Database not found");
            }
        }
    }


    /**
     * Drop specified database from Arango Driver
     *
     * @param dbName Database name to be dropped
     * @throws IOException
     */

    public static void dropDatabase(String dbName) throws IOException {
        try {
            DatabaseConnection.getDBConnection().getArangoDriver().db(dbName).drop();
        } catch (ArangoDBException exception) {
            if (exception.getErrorNum() == 1228) {
                System.out.println("Database not found");
            } else
                throw exception;
        }
    }

    /**
     * Closing connection to the database.
     *
     * @throws ArangoDBException
     * @throws IOException
     */
    public static void closeDBConnection() throws ArangoDBException, IOException {

        DatabaseConnection.getDBConnection().getArangoDriver().shutdown();
    }

}