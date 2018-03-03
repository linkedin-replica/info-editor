package database;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import java.util.*;

import com.arangodb.model.DocumentCreateOptions;
import jdk.internal.util.xml.impl.Pair;
import models.User;
import utils.ConfigReader;
import com.arangodb.ArangoDatabase;
import models.Company;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
//import com.arangodb.ArangoDB;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;

public class ArangoHandler implements DatabaseHandler{
ArangoDB arangoDB;
    private ConfigReader config;
    private ArangoDatabase dbInstance;
    private ArangoCollection collection;
    private String collectionName;

    public void connect() {
        // TODe
        Point x ;
        TreeSet<Pair > paur = new TreeSet<Pair>();
        arangoDB = new ArangoDB.Builder().build();
    }

  
    public void disconnect() {
        // TODO
    }
    public Company getCompany(String companyID){

        Company baseDocument =dbInstance.collection("jobs").getDocument(companyID,
                Company.class);
//        new Company(baseDocument.getAttribute("companyName"),Integer.parseInt(companyID),baseDocument.getAttribute("companyProfilePicture"),baseDocument.getAttribute("adminUserName"),baseDocument.getAttribute("adminUserID"),
//                baseDocument.getAttribute("industryType"),baseDocument.getAttribute("companyLocation"),baseDocument.getAttribute("companyType"),
//                baseDocument.getAttribute("posts"),baseDocument.getAttribute("jobListings"));
//        System.out.println("Key: " + baseDocument.getKey());
//        System.out.println("AdminUserName: " + baseDocument.getAdminUserName());
//        System.out.println("adminUser: " + baseDocument.getAdminUserID());
//        System.out.println("getCompanyID: " + baseDocument.getCompanyID());
//        System.out.println("getCompanyName: " + baseDocument.getCompanyName());
//        System.out.println("getJobListings: " + baseDocument.getJobListings());
//        System.out.println("getIndustryType: " + baseDocument.getIndustryType());


        return baseDocument;
    }
    public void insertCompany(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID, String industryType,String companyLocation
            ,String companytype,String[] specialities,String[] posts,String[] jobListings){

        BaseDocument myObject = new BaseDocument();
        myObject.setKey(companyID+"");
            myObject.addAttribute("companyName", companyName);
            myObject.addAttribute("companyID", companyID);
            myObject.addAttribute("companyProfilePicture", companyProfilePicture);
            myObject.addAttribute("companyLocation", companyLocation);
            myObject.addAttribute("companyType", companytype);
            myObject.addAttribute("adminUserName", adminUserName);
            myObject.addAttribute("industryType", industryType);
            myObject.addAttribute("specatilities",specialities);
            myObject.addAttribute("JobListings",jobListings);
            myObject.addAttribute("posts",posts);
        System.out.println(arangoDB);
        try {
            dbInstance.collection("jobs").insertDocument(myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }
    public void updateCompany(String companyName,int companyID,String companyProfilePicture,String adminUserName,int adminUserID, String industryType,String companyLocation
           ,String companytype,String[] specialities,String[] posts,String[] jobListings){
        BaseDocument myObject = new BaseDocument();
        if(companyName!=null)
        myObject.addAttribute("companyName", companyName);
        if(companyProfilePicture!=null)
            myObject.addAttribute("companyProfilePicture", companyProfilePicture);
        if(companyLocation!=null)
            myObject.addAttribute("companyLocation", companyLocation);
        if(companytype==null)
            myObject.addAttribute("companyType", companytype);
        if(industryType!=null)
            myObject.addAttribute("industryType", industryType);
        if(specialities.length!=0)
            myObject.addAttribute("specatilities",specialities);
        if(jobListings.length!=0)
            myObject.addAttribute("jobListings",jobListings);
        if(posts.length!=0)
            myObject.addAttribute("posts",posts);
        try {
            dbInstance.collection("Companies").updateDocument(companyID+"", myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }
    public void addCV(String userID,String cv){
        BaseDocument baseDocument = new BaseDocument();
        baseDocument.addAttribute("cv",cv);
        dbInstance.collection("users").updateDocument(userID,baseDocument);
    }
    public User getUser(String userID){
        User user = dbInstance.collection("users").getDocument(userID,User.class);
        return user;
    }

    public void deleteCV(String userID){
        String nil= "";
        BaseDocument baseDocument = new BaseDocument();
        baseDocument.addAttribute("cv","");
        dbInstance.collection("users").updateDocument(userID,baseDocument);




    }
    public ArangoHandler()throws IOException {

        config = new ConfigReader("arango_names");

        // init db
        ArangoDB arangoDriver = DatabaseConnection.getDBConnection().getArangoDriver();
        collectionName = config.getConfig("collection.notifications.name");
        dbInstance = arangoDriver.db(config.getConfig("db.name"));
        collection = dbInstance.collection(collectionName);
    }
    public static void main(String [] srgs){

    }
}

