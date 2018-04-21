package com.linkedin.replica.editInfo.database.handlers.impl;
import java.util.*;

import com.arangodb.*;
import com.arangodb.util.MapBuilder;
import com.google.gson.Gson;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.models.Company;
import com.linkedin.replica.editInfo.models.User;

import com.linkedin.replica.editInfo.models.*;
import java.io.IOException;

import com.arangodb.entity.BaseDocument;

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
    public Company getCompany(String companyId){
        Map <String, Object> bindVars = new HashMap<String ,Object>();
        bindVars.put("companyId",companyId);

        String Query = "FOR company in companies\n" +
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
        System.out.println(Query);
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        ArangoCursor<String> cursor = dbInstance.query(Query, bindVars,null, String.class);
        System.out.println(cursor.next());
        return null;
    }
    public void insertCompany(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID, String industryType,String companyLocation
            ,String companytype,ArrayList<String> specialities,ArrayList<String> posts,ArrayList<String> jobListings){
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(companyID);
            System.out.println(myObject.getId());
            myObject.addAttribute("companyName", companyName);
            myObject.addAttribute("companyId", companyID);
            myObject.addAttribute("companyProfilePicture", companyProfilePicture);
            myObject.addAttribute("companyLocation", companyLocation);
            myObject.addAttribute("companyType", companytype);
            myObject.addAttribute("adminUserName", adminUserName);
            myObject.addAttribute("industryType", industryType);
            myObject.addAttribute("specatilities",specialities);
            myObject.addAttribute("jobListings",jobListings);
            myObject.addAttribute("posts",posts);
        try {
            dbInstance.collection("companies").insertDocument(myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    public void updateCompany(HashMap<String,Object>args) {
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        Gson gson = new Gson();
       // System.out.println(args+"args");
        Map<String, Object> bindVars = new MapBuilder().get();

        //execute query
        String Query = "FOR company IN "+config.getArangoConfigProp("collection.companies.name")+"  UPDATE { _key:"+"@companyId" ;
        Query +="} WITH{ ";
        for(String key :args.keySet()){
            Class c = args.get(key).getClass();
            //System.out.println(c.getName());
            bindVars.put(key,args.get(key));
            if(c.getName().equals("java.lang.String")){

                Query +=key +": "+"@"+key+" , ";
            }
            else{
                Query +=key +": "+"APPEND(company."+key+","+"@"+key+")"+" ,";
            }
          //  System.out.println(bindVars);

        }
   if(args.keySet().size()>0)
       Query = Query.substring(0,Query.length()-2);
        Query+="  }   IN "+collectionName;
        System.out.println(Query);

        try {
        ArangoCursor<String> cursor = dbInstance.query(Query, bindVars,null, String.class);
        System.out.println(cursor);

//
//        if(companyName!=null)
//        company.addAttribute("companyName",companyName);
//        if(companyProfilePicture!=null)
//            company.setCompanyProfilePicture(companyProfilePicture);
//        if(companyLocation!=null)
//            company.setCompanyLocation(companyLocation);
//        if(companytype==null)
//           company.setCompanytype(companytype);
//        if(industryType!=null)
//            company.setIndustryType(industryType);
//        if(jobListings!=null&&jobListings.size()!=0)
//           company.updateJobListings(jobListings);
//        if(posts!=null&&posts.size()!=0)
//            company.updatePosts(posts);

//            dbInstance.collection(collectionName).updateDocument(companyID+"", company);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    public void createProfile(HashMap<String,Object> profileAttributes, String userID){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        BaseDocument user = new BaseDocument();
        user.setKey(userID);
        PersonalInfo personalInfo = new PersonalInfo();
        Location location = new Location();
        if(profileAttributes.containsKey("firstName"))
            user.addAttribute("firstName", profileAttributes.get("firstName"));
        if(profileAttributes.containsKey("lastName"))
            user.addAttribute("lastName", profileAttributes.get("lastName"));
        if(profileAttributes.containsKey("headline"))
            user.addAttribute("headline", profileAttributes.get("headline"));
        if(profileAttributes.containsKey("personalInfo.phone"))
            personalInfo.setPhone((String)(profileAttributes.get("personalInfo.phone")));
        if(profileAttributes.containsKey("personalInfo.email"))
            personalInfo.setEmail((String)(profileAttributes.get("personalInfo.email")));
        if(profileAttributes.containsKey("personalInfo.dob"))
            personalInfo.setDob((String)(profileAttributes.get("personalInfo.dob")));
        if(profileAttributes.containsKey("personalInfo.location.address"))
            location.setAddress((String)profileAttributes.get("personalInfo.location.address"));
        if(profileAttributes.containsKey("personalInfo.location.country"))
            location.setCountry((String)profileAttributes.get("personalInfo.location.country"));
        if(profileAttributes.containsKey("personalInfo.location.country"))
            location.setCountry((String)profileAttributes.get("personalInfo.location.country"));
        if(profileAttributes.containsKey("personalInfo.location.code"))
            location.setCountry((String)profileAttributes.get("personalInfo.location.code"));
        personalInfo.setLocation(location);
        if(profileAttributes.containsKey("personalInfo.website"))
            personalInfo.setWebsite((String)(profileAttributes.get("personalInfo.website")));
        user.addAttribute("personalInfo", personalInfo);
        if(profileAttributes.containsKey("numConnections"))
            user.addAttribute("numConnections", (String)profileAttributes.get("numConnections"));
        if(profileAttributes.containsKey("numFollowers"))
            user.addAttribute("numFollowers", (String)profileAttributes.get("numFollowers"));
        if(profileAttributes.containsKey("summary"))
            user.addAttribute("summary", (String)profileAttributes.get("summary"));
        if(profileAttributes.containsKey("imageUrl"))
            user.addAttribute("imageUrl", (String)profileAttributes.get("imageUrl"));
        if(profileAttributes.containsKey("cvUrl"))
            user.addAttribute("cvUrl", (String)profileAttributes.get("cvUrl"));
        dbInstance.collection(UsersCollectionName).insertDocument(user);
    }
    public void updateProfile(HashMap<String, Object> args){
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Gson gson = new Gson();
        // System.out.println(args+"args");
        Map<String, Object> bindVars = new MapBuilder().get();

        //execute query
        String Query = "FOR user IN "+config.getArangoConfigProp("collection.users.name")+"  UPDATE { _key:"+"@userId" ;
        Query +="} WITH{ ";
        for(String key :args.keySet()){
            Class c = args.get(key).getClass();
            //System.out.println(c.getName());
            bindVars.put(key,args.get(key));
            if(c.getName().equals("java.lang.String")){

                Query +=key +": "+"@"+key+" , ";
            }
            else{
                Query +=key +": "+"APPEND(user."+key+","+"@"+key+")"+" ,";
            }
            //  System.out.println(bindVars);

        }
        if(args.keySet().size()>0)
            Query = Query.substring(0,Query.length()-2);
        Query+="  }   IN "+collectionName;
        System.out.println(Query);

        try {
            ArangoCursor<String> cursor = dbInstance.query(Query, bindVars,null, String.class);
            System.out.println(cursor);

//
//        if(companyName!=null)
//        company.addAttribute("companyName",companyName);
//        if(companyProfilePicture!=null)
//            company.setCompanyProfilePicture(companyProfilePicture);
//        if(companyLocation!=null)
//            company.setCompanyLocation(companyLocation);
//        if(companytype==null)
//           company.setCompanytype(companytype);
//        if(industryType!=null)
//            company.setIndustryType(industryType);
//        if(jobListings!=null&&jobListings.size()!=0)
//           company.updateJobListings(jobListings);
//        if(posts!=null&&posts.size()!=0)
//            company.updatePosts(posts);

//            dbInstance.collection(collectionName).updateDocument(companyID+"", company);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }




    /*
     * *
     * Add new skill in the user profile
     * @param userId : the id of the user and the new skill
     */
    public void addSkill(String userID, String Skill){
        User user = getUserProfile(userID);
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        user.getSkills().add(Skill);
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);
    }


    public void addCV(String userID,String cv) {
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Gson gson = new Gson();
        // System.out.println(args+"args");
        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("cv", cv);
        bindVars.put("userId", userID);

        //execute query
        String Query = "FOR user IN " + config.getArangoConfigProp("collection.users.name") + "  UPDATE { _key:" + "@userId";
        Query += "} WITH{ ";


        Query += " cvUrl:@cv }   IN " + collectionName;
        System.out.println(Query);

        try {
            ArangoCursor<String> cursor = dbInstance.query(Query, bindVars, null, String.class);
            System.out.println(cursor.next());

        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    public void deleteCV(String userID) {
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Gson gson = new Gson();
        // System.out.println(args+"args");
        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("cv", "");
        bindVars.put("userId", userID);

        //execute query
        String Query = "FOR user IN " + config.getArangoConfigProp("collection.users.name") + "  UPDATE { _key:" + "@userId";
        Query += "} WITH{ ";


        Query += " cvUrl:@cv }   IN " + collectionName;
       // System.out.println(Query);

        try {
            ArangoCursor<String> cursor = dbInstance.query(Query, bindVars, null, String.class);
         //   System.out.println(cursor.next());

        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    /*
    * *
     * Get the profile of the user
     * @param userId : the id of the user
     * @return the queried user profile
     */

    public User getUserProfile(String UserID){
        Map <String, Object> bindVars = new HashMap<String ,Object>();
        bindVars.put("userId",UserID);
        System.out.println("here");
        String Query = "FOR user in users\n" +
                "filter user._key == @userId\n" +
                "let BookMarkedPosts = (" +
                "    for post in posts\n" +
                "    filter post.postId in user.bookmarkedPosts\n" +
                "    return {\"authorId\":post.authorId ,\"postId\":post.postId,\"text\":post.text}\n" +
                ")\n" +
                "let friendlist = (\n" +
                "    for friend in users\n" +
                "    filter friend._key in friend.friendsList\n" +
                "    return {\"friend.userId\":friend.userId,\"friend.userName\":friend.firstName,\"friend.lastName\":friend.lastName}\n" +
                ")\n" +
                "return  MERGE_RECURSIVE (\n" +
                "                    user, \n" +
                "                    {\"bookmarkedPosts\": BookMarkedPosts, \"connections\": friendlist}\n" +
                "                    \n" +
                "                )\n" +
                "          ";
        System.out.println(Query);
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        ArangoCursor<String> cursor = dbInstance.query(Query, bindVars,null, String.class);
        System.out.println(cursor.next());
        System.out.println(cursor.next());
        return null;
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

