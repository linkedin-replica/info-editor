package com.linkedin.replica.editInfo.database.handlers.impl;
import java.util.*;

import com.arangodb.*;
import com.arangodb.util.MapBuilder;
import com.google.gson.Gson;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import com.linkedin.replica.editInfo.models.*;
import java.io.IOException;

import com.arangodb.entity.BaseDocument;

import javax.management.Query;

public class ArangoEditInfoHandler implements EditInfoHandler {

    ArangoDB arangoDB;
    private Configuration config;
    private ArangoDatabase dbInstance;
    private ArangoCollection collection;
    private String collectionName;

    public void connect() {
        // TODe
        arangoDB = new ArangoDB.Builder().build();
    }


    public void disconnect() {
        // TODO
    }
    public CompanyReturn getCompany(String companyId){
        Map <String, Object> bindVars = new HashMap<String ,Object>();
        bindVars.put("companyId",companyId);
        String companyCollectionName = config.getArangoConfigProp("collection.companies.name");

        String Query = "FOR company in " + companyCollectionName  + "\n" +
                "filter company._key == @companyId\n" +
                "let Posts = (" +
                "    for post in posts\n" +
                "    filter post.postId in company.posts\n" +
                "    return post\n" +
                ")\n" +
                "let jobListings = (\n" +
                "    for jobListingtemp in jobs\n" +
                "    filter jobListingtemp._key in company.jobListing\n" +
                "    return jobListingtemp\n" +
                ")\n" +
                "return  MERGE_RECURSIVE (\n" +
                "                    company, \n" +
                "                    {\"posts\": Posts, \"jobListing\": jobListings}\n" +
                "                    \n" +
                "                )\n" +
                "          ";
        ArangoCursor<CompanyReturn> cursor = dbInstance.query(Query, bindVars,null, CompanyReturn.class);
        CompanyReturn company = cursor.next();
        return company;
    }

    /**
     * Insert company in collection companies.
     * @param companyName : name of inserted company
     * @param companyId id generated
     * @param companyProfilePicture profile picture url
     * @param industryType industry type of inserted company
     */
    public String insertCompany(String companyName, String companyId, String companyProfilePicture, String userId, String industryType){
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(companyId);
            myObject.addAttribute("companyName", companyName);
            myObject.addAttribute("companyId", companyId);
            myObject.addAttribute("companyProfilePicture", companyProfilePicture);
            myObject.addAttribute("industryType", industryType);
        try {
            dbInstance.collection("companies").insertDocument(myObject);
        } catch (ArangoDBException e) {
           return "Failed to create company. ";
        }
        return "Company is created successfully.";
    }

    public String updateCompany(HashMap<String,Object> args) {
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        Map<String, Object> bindVars = new MapBuilder().get();

        String Query = "FOR company IN "+ collectionName +"  UPDATE { _key:"+"@companyId" ;
        Query +="} WITH{ ";
        for(String key :args.keySet()){
            Class c = args.get(key).getClass();
            bindVars.put(key,args.get(key));
            if(c.getName().equals("java.lang.String")){
                Query += key +": "+"@"+key+" , ";
            }
            else{
                Query += key +": "+"APPEND(company."+key+","+"@"+key+")"+" ,";
            }
        }
        if(args.keySet().size() > 0)
            Query = Query.substring(0, Query.length() - 2);

        Query+="  }   IN "+ collectionName;

        try {
         dbInstance.query(Query, bindVars,null, String.class);

        } catch (ArangoDBException e) {
            return "Failed to update company.";
        }
        return "Company is updated successfully.";
    }


    public String createProfile(HashMap<String,Object> args, String userId){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        BaseDocument user = new BaseDocument();
        user.setKey(userId);
        if(args.containsKey("firstName"))
            user.addAttribute("firstName", args.get("firstName"));
        if(args.containsKey("lastName"))
            user.addAttribute("lastName", args.get("lastName"));
        if(args.containsKey("headline"))
            user.addAttribute("headline", args.get("headline"));
        if(args.containsKey("email"))
            user.addAttribute("email", (args.get("email")));
        if(args.containsKey("profilePictureUrl"))
            user.addAttribute("profilePictureUrl", args.get("profilePictureUrl"));
        if(args.containsKey("cvUrl"))
            user.addAttribute("cvUrl", args.get("cvUrl"));
        try {
            dbInstance.collection(UsersCollectionName).insertDocument(user);
        } catch(ArangoDBException e) {
            return "Failed to create profile.";
        }
        return "Profile is created successfully.";
    }

    public String updateProfile(HashMap<String, Object> args){
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Map<String, Object> bindVars = new MapBuilder().get();

        //execute query
        String Query = "FOR user IN "+ collectionName +"  UPDATE { _key:"+"@userId" ;
        Query +="} WITH{ ";
        for(String key :args.keySet()){
            Class c = args.get(key).getClass();
            bindVars.put(key, args.get(key));
            if(c.getName().equals("java.lang.String")){

                Query += key +": "+"@"+key+" , ";
            }
            else{
                Query += key +": "+"APPEND(user."+key+","+"@"+key+")"+" ,";
            }
        }
        if(args.keySet().size() > 0)
            Query = Query.substring(0, Query.length() - 2);
        Query+="  }   IN "+collectionName;

        try {
            dbInstance.query(Query, bindVars,null, String.class);
        } catch (ArangoDBException e) {
            return "Failed to update profile. ";
        }
        return "Profile updated successfully.";
    }




    /*
     * *
     * Add new skill in the user profile
     * @param userId : the id of the user and the new skill
     */
    public String addSkill(String userId, String skill){
        String collectionName = config.getArangoConfigProp("collection.users.name");

        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("userId", userId);
        bindVars.put("skill", skill);

        String Query = "FOR user IN " + collectionName + "  UPDATE { _key:" + "@userId";
        Query += "} WITH{ ";


        Query += "skills : PUSH(user.skills , @skill)";
        Query+="  }   IN "+ collectionName;

        try {
         dbInstance.query(Query, bindVars, null, String.class);
        } catch (ArangoDBException e) {
            return "Failed to add skill.";
        }
        return "Skill is added successfully.";
    }

    public String deleteSkill(String userId, String skill){
        String collectionName = config.getArangoConfigProp("collection.users.name");

        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("userId", userId);
        bindVars.put("skill", skill);

        String Query = "FOR user IN " + collectionName + "  UPDATE { _key:" + "@userId";
        Query += "} WITH{ ";


        Query += "skills : REMOVE_VALUE(user.skills ,@skill)";
        Query+="  }   IN "+collectionName;

    try {
        dbInstance.query(Query, bindVars, null, String.class);
    } catch (ArangoDBException e) {
        return "Failed to delete skill.";
    }
    return "skill is deleted successfully.";
}

    public String addCV(String userID, String cv) {
        String collectionName = config.getArangoConfigProp("collection.users.name");

        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("cv", cv);
        bindVars.put("userId", userID);

        String Query = "FOR user IN " + collectionName + "  UPDATE { _key:" + "@userId";
        Query += "} WITH{ ";

        Query += " cvUrl:@cv }   IN " + collectionName;

        try {
           dbInstance.query(Query, bindVars, null, String.class);
        } catch (ArangoDBException e) {
            return "Failed to add CV.";
        }
        return "CV is added successfully.";
    }

    public String deleteCV(String userID) {
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("cv", "");
        bindVars.put("userId", userID);

        String Query = "FOR user IN " + collectionName + "  UPDATE { _key:" + "@userId";
        Query += "} WITH{ ";


        Query += " cvUrl:@cv }   IN " + collectionName;

        try {
            dbInstance.query(Query, bindVars, null, String.class);
        } catch (ArangoDBException e) {
            return "Failed to delete CV.";
        }
        return "CV is deleted successfully.";
    }

    /*
    * *
     * Get the profile of the user
     * @param userId : the id of the user
     * @return the queried user profile
     */

    public UserReturn getUserProfile(String UserID){
        Map <String, Object> bindVars = new HashMap<>();
        String usersCollectionName = config.getArangoConfigProp("collection.users.name");
        String companiesCollectionName = config.getArangoConfigProp("collection.companies.name");

        bindVars.put("userId",UserID);
        String Query = "FOR user in " + usersCollectionName + "\n" +
                "filter user._key == @userId\n" +
                "let BookMarkedPosts = (" +
                "    for post in posts\n" +
                "    filter post.postId in user.bookmarkedPosts\n" +
                "    return {\"authorId\":post.authorId ,\"postId\":post.postId,\"text\":post.text}\n" +
                ")\n" +
                "let friendlist = (\n" +
                "    for friend in " + usersCollectionName + "\n" +
                "    filter friend._key in user.friendsList\n" +
                "    return {\"friend.userId\":friend.userId,\"friend.firstName\":friend.firstName,\"friend.lastName\":friend.lastName,\"friend.userName\":friend.userName}\n" +
                ")\n" +
                "let followedCompanies = (\n" +
                "    for company in " + companiesCollectionName + "\n" +
                "    filter company._key in user.followedCompanies\n" +
                "    return {\"company.companyId\":company.companyId,\"company.companyName\":company.companyName,\"company.profilePictureUrl\":company.profilePictureUrl}\n" +
                ")\n" +
                "return  MERGE_RECURSIVE (\n" +
                "                  UNSET( user,\"friendsList\",\"bookmarkedPosts\",\"_id\",\"_rev\"),\n" +
                "                    {\"bookmarkedPosts\": BookMarkedPosts, \"friendslist\": friendlist, \"followedCompanies\":followedCompanies}\n" +
                "                    \n" +
                "                )\n" +
                "          ";
        ArangoCursor<UserReturn> cursor = dbInstance.query(Query, bindVars,null, UserReturn.class);
        return cursor.next();
    }




    public ArangoEditInfoHandler()throws IOException {
        config = Configuration.getInstance();
        // init db
        ArangoDB arangoDriver = DatabaseConnection.getDBConnection().getArangoDriver();
        collectionName = config.getArangoConfigProp("collection.users.name");
        dbInstance = arangoDriver.db(config.getArangoConfigProp("db.name"));
        collection = dbInstance.collection(collectionName);
    }
    public static void main(String [] srgs){
    }
}

/*{"_id":"users\/1","_key":"1","_rev":"_Wsj44J---F","bookmarkedPosts":[{"authorId":"12","postId":"1","text":"How to calculate LCA with LCM?"},{"authorId":"1","postId":"2","text":"This is sooooooooooooo boring"},{"authorId":"2","postId":"3","text":"Another boring post"}],"cvUrl":"user12URL","education":[{"degree":"Bachelor","fieldOfStudy":"Computer Science","isCurrent":true,"schoolName":"German University in Cairo","startDate":123213213},{"degree":"Highschool","endDate":122213213,"schoolName":"MSA school","startDate":121213213}],"firstName":"Ahmed","followedCompanies":[],"friendslist":[{"friend.firstName":"Ahmed","friend.lastName":"Soliman","friend.userId":"4"},{"friend.firstName":"Hatem","friend.lastName":"Morgan","friend.userId":"3"},{"friend.firstName":"Mostafa","friend.lastName":"Karim","friend.userId":"2"}],"headline":"Software Engineer at Goofle","industry":"Computer Science","lastName":"Mohamed","positions":[{"companyId":"11","isCurrent":true,"startDate":-563580575,"title":"Software Engineer"},{"companyId":"12","endDate":1136419425,"startDate":536419425,"title":"Software Engineering Intern"}],"profilePictureUrl":"linkedin.com\/1.png","skills":["Hard worker","Work under pressure"],"userId":"1"}
 */

/*"positions":[{"companyId":"11","isCurrent":true,"startDate":-563580575,"title":"Software Engineer"},{"companyId":"12","endDate":1136419425,"startDate":536419425,"title":"Software Engineering Intern"}]*/