package database;
import java.util.*;

import models.*;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
//import com.arangodb.ArangoDB;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;

public class ArangoHandler implements DatabaseHandler{
ArangoDB arangoDB;

    public void connect() {
        // TODO
        arangoDB = new ArangoDB.Builder().build();
    }

  
    public void disconnect() {
        // TODO
    }
    public Company getCompany(String companyID){
        Company company = arangoDB.db("Linked-in").collection("Companies").getDocument(companyID, Company.class);
        return company;
    }
    public void updateCompany(String companyName,int companyID,String companyProfilePicture,String adminUserName,int adminUserID,
            int adminUserIDMongo, String industryType,String companyLocation,lightUser[] relatedConnections,String aboutUs, String website,Date yearFounded
            ,String[] headquarters,String companytype,int companySize,String[] specialities,lightPost[] posts,lightJobListing[] jobListings){
        BaseDocument myObject = new BaseDocument();
        if(companyName!=null)
        myObject.addAttribute("companyName", companyName);
        if(companyProfilePicture!=null)
            myObject.addAttribute("companyProfilePicture", companyProfilePicture);
        if(companyLocation!=null)
            myObject.addAttribute("companyLocation", companyLocation);
        if(companySize==-1)
            myObject.addAttribute("companySize", companySize);
        if(companytype==null)
            myObject.addAttribute("companyType", companytype);
        if(headquarters==null)
            myObject.addAttribute("headquarters",headquarters);
        if(website!=null)
            myObject.addAttribute("website", website);
        if(aboutUs!=null)
            myObject.addAttribute("aboutUS", aboutUs);
        if(industryType!=null)
            myObject.addAttribute("industryType", industryType);
        if(yearFounded!=null)
            myObject.addAttribute("yeadFounded",yearFounded);
        try {
            arangoDB.db("Linked-in").collection("Companies").updateDocument(companyID+"", myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    public void updateProfile(LinkedHashMap<String, Object> updates, String UserId){
        String getUserQuery = "FOR t IN @userCollection FILTER t.userId == @userId RETURN t";
        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("userId", UserId);
        BaseDocument newProfile = arangoDB.db(dbName).collection(userCollection).
                getDocument(getUserQuery, BaseDocument.class);
        if(updates.containsKey("firstName"))
            newProfile.addAttribute("firstName", updates.get("firstName"));
        if(updates.containsKey("lastName"))
            newProfile.addAttribute("lastName", updates.get("lastName"));
        if(updates.containsKey("headline"))
            newProfile.addAttribute("headline", updates.get("headline"));
        if(updates.containsKey("personalInfo"))
            newProfile.addAttribute("personalInfo", updates.get("personalInfo"));
        if(updates.containsKey("numConnections"))
            newProfile.addAttribute("numConnections", updates.get("numConnections"));
        if(updates.containsKey("numFollowers"))
            newProfile.addAttribute("numFollowers", updates.get("numFollowers"));
        if(updates.containsKey("summary"))
            newProfile.addAttribute("summary", updates.get("summary"));
        if(updates.containsKey("positions"))
            newProfile.addAttribute("positions", updates.get("positions"));
        if(updates.containsKey("educations"))
            newProfile.addAttribute("educations", updates.get("educations"));
        if(updates.containsKey("imageUrl"))
            newProfile.addAttribute("imageUrl", updates.get("imageUrl"));
        if(updates.containsKey("cvUrl"))
            newProfile.addAttribute("cvUrl", updates.get("cvUrl"));
        if(updates.containsKey("skills"))
            newProfile.addAttribute("skills", updates.get("skills"));
        if(updates.containsKey("friendsList"))
            newProfile.addAttribute("friendsList", updates.get("friendsList"));
        if(updates.containsKey("bookmarkedPosts"))
            newProfile.addAttribute("bookmarkedPosts", updates.get("bookmarkedPosts"));
        arangoDB.db(dbName).collection(userCollection).updateDocument("bookmarks", l);

    }


    public void addSkill(String userID, String Skill){
        String getUserQuery = "FOR t IN @userCollection FILTER t.userId == @userId RETURN t";
        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("userId", userID);
        User newProfile = arangoDB.db(dbName).collection(userCollection).
                getDocument(getUserQuery, User.class);
        newProfile.getSkills().add(Skill);


    }

    public void addCV(String userID,String cv){
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(userID);
        myObject.addAttribute("cv",cv);
        try {
            arangoDB.db("Linked-in").collection("Users").updateDocument(userID,myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to Insert cv. " + e.getMessage());
        }

    }

    public void deleteCV(String userID){
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(userID);
        myObject.addAttribute("cv",null);
        try {
            arangoDB.db("Linked-in").collection("Users").updateDocument(userID,myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to delete cv. " + e.getMessage());
        }

    }

    /**
     * Get the profile of the user
     * @param userId : the id of the user
     * @return the queried user profile
     */

    public User getUserProfile(String UserID){

        String query = "For t in " + collectionName + " FILTER t.userId == @userId RETURN t";

        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("userId", UserID);

        // process query
        ArangoCursor<User> cursor = dbInstance.query(query, bindVars, null, User.class);

        return cursor.next();
    }

    public static void main(String [] srgs){

    }
}
