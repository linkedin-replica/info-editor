package com.linkedin.replica.editInfo.database.handlers.impl;
import java.util.*;

import com.arangodb.*;
import com.arangodb.util.MapBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
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

    /* get the profile of the company*/
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


    /*inserting new company*/
    public void insertCompany(JsonObject args){
        BaseDocument myObject = new BaseDocument();
        String companyID = UUID.randomUUID().toString();
        myObject.setKey(companyID);
            for(String key :args.keySet()) {
                if(key.equals("commandName"))
                    continue;
                    if((key.equals("posts"))){
                        JsonArray js = args.get(key).getAsJsonArray();
                        String [] posts = new String[js.size()];
                        for (int i = 0; i < posts.length; i++){
                            posts[i] = js.get(i).getAsString();
                        }
                        myObject.addAttribute(key,posts);
                    }else if(key.equals("jobListing")) {
                        JsonArray js = args.get(key).getAsJsonArray();
                        String [] jobListing = new String[js.size()];
                        for (int i = 0; i < jobListing.length; i++){
                            jobListing[i] = js.get(i).getAsString();
                        }
                        myObject.addAttribute(key,jobListing);

                }else{
                        myObject.addAttribute(key,args.get(key).getAsString());
                    }
            }
            dbInstance.collection("companies").insertDocument(myObject);
    }


    /*updating company profile*/
    public void updateCompany(JsonObject args) {
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        String query = "For t in " + collectionName + " FILTER " +
                "t._key == @companyId" + " UPDATE t " + "WITH{";
        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("companyId",args.get("companyId").getAsString());
        int counter = 0;
        for (String key : args.keySet()) {
            if (!key.equals("companyId") && !key.equals("userId") && !key.equals("commandName")) {
                if(key.equals("posts")){
                    JsonArray Reqposts =  args.get("posts").getAsJsonArray();
                    String[] posts = new String[Reqposts.size()];
                    for (int i = 0; i < posts.length; i++){
                        posts[i] = Reqposts.get(i).toString();
                    }
                    query += key + ":@field" + counter + " ,";
                    bindVars.put("field" + counter, posts);
                    counter++;
                }else if(key.equals("jobListing")){
                    JsonArray ReqjobListing =  args.get("posts").getAsJsonArray();
                    String[] jobListing = new String[ReqjobListing.size()];
                    for (int i = 0; i < jobListing.length; i++){
                        jobListing[i] = ReqjobListing.get(i).toString();
                    }
                    query += key + ":@field" + counter + " ,";
                    bindVars.put("field" + counter, jobListing);
                    counter++;

                }else {
                    query += key + ":@field" + counter + " ,";
                    System.out.println(args.get(key).getAsString());
                    bindVars.put("field" + counter, args.get(key).getAsString());
                    counter++;
                }
            }
        }
        query = query.substring(0, query.length() - 1);
        query += "} IN " + collectionName;
        dbInstance.query(query, bindVars,null,null);
    }



    /*create profile */
    public void createProfile(JsonObject args){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        BaseDocument user = new BaseDocument();
        user.setKey(args.get("userId").getAsString());
       for (String key:args.keySet()){
           if(key.equals("positions")){
               Gson gson = new Gson();
               ArrayList<Position> positions = new ArrayList<>();
               JsonArray js = args.get("positions").getAsJsonArray();
               for (int i = 0; i < js.size(); i++ ){
                   positions.add(gson.fromJson(js.get(i).toString(),Position.class));
               }
               user.addAttribute(key,positions);
           }else if (key.equals("skills")){
               JsonArray js = args.get("skills").getAsJsonArray();
               String[] skills = new String[js.size()];
               for (int i = 0; i < js.size(); i++ ){
                   skills[i] = js.get(i).getAsString();
               }
               user.addAttribute(key,skills);
           }else if (key.equals("friendsList")) {
               JsonArray js = args.get("friendsList").getAsJsonArray();
               String[] friendsList = new String[js.size()];
               for (int i = 0; i < js.size(); i++) {
                   friendsList[i] = js.get(i).getAsString();
               }
               user.addAttribute(key, friendsList);
           }else if (key.equals("bookmarkedPosts")) {
               JsonArray js = args.get("bookmarkedPosts").getAsJsonArray();
               String[] bookmarkedPosts = new String[js.size()];
               for (int i = 0; i < js.size(); i++) {
                   bookmarkedPosts[i] = js.get(i).getAsString();
               }
               user.addAttribute(key, bookmarkedPosts);
           }
           else if (key.equals("followedCompanies")) {
               JsonArray js = args.get("followedCompanies").getAsJsonArray();
               String[] followedCompanies = new String[js.size()];
               for (int i = 0; i < js.size(); i++) {
                   followedCompanies[i] = js.get(i).getAsString();
               }
               user.addAttribute(key, followedCompanies);
           }
           else{
               System.out.println(key);
               if(!key.equals("commandName"))
                 user.addAttribute(key, args.get(key).getAsString());
           }
       }
            dbInstance.collection(UsersCollectionName).insertDocument(user);
    }


    /*update the user profile*/
    public void updateProfile(JsonObject args){
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Map<String, Object> bindVars = new MapBuilder().get();
        String query = "FOR user IN "+ collectionName +"  UPDATE { _key:"+"@userId" ;
        query +="} WITH{ ";
        int counter = 0;
        bindVars.put("userId",args.get("userId").getAsString());
        for(String key :args.keySet()){
            if (!key.equals("userId") && !key.equals("commandName")) {
                if(key.equals("followedCompanies")){
                    JsonArray fb =  args.get("followedCompanies").getAsJsonArray();
                    String[] followedCompanies = new String[fb.size()];
                    for (int i = 0; i < followedCompanies.length; i++){
                        followedCompanies[i] = fb.get(i).getAsString();
                    }
                    query += key + ":@field" + counter + " ,";
                    bindVars.put("field" + counter, followedCompanies);
                    counter++;
                }else if(key.equals("friendsList")) {
                    JsonArray fb =  args.get("friendsList").getAsJsonArray();
                    String[] friendsList = new String[fb.size()];
                    for (int i = 0; i < friendsList.length; i++){
                        friendsList[i] = fb.get(i).getAsString();
                    }
                    query += key + ":@field" + counter + " ,";
                    bindVars.put("field" + counter, friendsList);
                    counter++;
                }else if(key.equals("bookmarkedPosts")) {
                    JsonArray fb =  args.get("bookmarkedPosts").getAsJsonArray();
                    String[] bookmarkedPosts = new String[fb.size()];
                    for (int i = 0; i < bookmarkedPosts.length; i++){
                        bookmarkedPosts[i] = fb.get(i).getAsString();
                    }
                    query += key + ":@field" + counter + " ,";
                    bindVars.put("field" + counter, bookmarkedPosts);
                    counter++;
                }else if(key.equals("positions")) {
                    Gson gson = new Gson();
                    ArrayList<Position> positions = new ArrayList<>();
                    JsonArray js = args.get("positions").getAsJsonArray();
                    for (int i = 0; i < js.size(); i++ ){
                        positions.add(gson.fromJson(js.get(i).toString(),Position.class));
                    }
                    query += key + ":@field" + counter + " ,";
                    bindVars.put("field" + counter, positions);
                    counter++;
                }else{
                    query += key + ":@field" + counter + " ,";
                    bindVars.put("field" + counter, args.get(key).getAsString());
                    counter++;
                }
            }
        }
        query = query.substring(0, query.length() - 1);
        query += "} IN " + collectionName;
        dbInstance.query(query, bindVars, null, UserReturn.class);
    }




    /*
     * *
     * Add new skill in the user profile
     * @param userId : the id of the user and the new skill
     */

    public void addSkill(String userId, String skill){
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("userId", userId);
        bindVars.put("skill", skill);
        String Query ="FOR user IN " +collectionName+" FILTER user._key == @userId";
        Query+=" LET newSkills = PUSH(user.skills ,@skill)";
        Query+=" UPDATE user WITH { skills : newSkills } IN users";
        dbInstance.query(Query, bindVars, null, String.class);
    }

    public void deleteSkill(String userId, String skill){
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("userId", userId);
        bindVars.put("skill", skill);
        String Query ="FOR user IN " +collectionName+" FILTER user._key == @userId";
        Query+=" LET newSkills = REMOVE_VALUE(user.skills ,@skill)";
        Query+=" UPDATE user WITH { skills : newSkills } IN "+ collectionName;
        dbInstance.query(Query, bindVars, null, String.class);
}

    public void addCV(String userID, String cv) {
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("cv", cv);
        bindVars.put("userId", userID);
        String Query = "FOR user IN " + collectionName + "  UPDATE { _key:" + "@userId";
        Query += "} WITH{ ";
        Query += " cvUrl:@cv }   IN " + collectionName;
        dbInstance.query(Query, bindVars, null, String.class);
    }

    public void deleteCV(String userID) {
        String collectionName = config.getArangoConfigProp("collection.users.name");
        Map<String, Object> bindVars = new MapBuilder().get();
        bindVars.put("cv", "");
        bindVars.put("userId", userID);
        String Query = "FOR user IN " + collectionName + "  UPDATE { _key:" + "@userId";
        Query += "} WITH{ ";
        Query += " cvUrl:@cv }   IN " + collectionName;
        dbInstance.query(Query, bindVars, null, String.class);
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
                "    return {\"userId\":friend._key,\"firstName\":friend.firstName,\"profilePictureUrl\":friend.profilePictureUrl,\"lastName\":friend.lastName}\n" +
                ")\n" +
                "let followedCompanies = (\n" +
                "    for company in " + companiesCollectionName + "\n" +
                "    filter company._key in user.followedCompanies\n" +
                "    return {\"companyId\":company.companyId,\"companyName\":company.companyName,\"profilePictureUrl\":company.profilePictureUrl}\n" +
                ")\n" +
                "return  MERGE_RECURSIVE (\n" +
                "                  UNSET( user,\"friendsList\",\"bookmarkedPosts\",\"_id\",\"_rev\"),\n" +
                "                    {\"bookmarkedPosts\": BookMarkedPosts, \"friendsList\": friendlist, \"followedCompanies\":followedCompanies}\n" +
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
