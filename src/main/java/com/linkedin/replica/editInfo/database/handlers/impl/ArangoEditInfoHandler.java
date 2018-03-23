package com.linkedin.replica.editInfo.database.handlers.impl;
import java.util.*;

import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.models.Company;
import com.linkedin.replica.editInfo.models.User;

import com.linkedin.replica.editInfo.models.*;
import java.io.IOException;

import com.arangodb.ArangoDatabase;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
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
    public Company getCompany(String companyID){
        String collectionName = config.getArangoConfigProp("collection.companies.name");
//        System.out.println(collectionName);
        Company company = dbInstance.collection(collectionName).getDocument(companyID,

                Company.class);
        return company;
    }
    public void insertCompany(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID, String industryType,String companyLocation
            ,String companytype,ArrayList<String> specialities,ArrayList<String> posts,ArrayList<String> jobListings){
            BaseDocument myObject = new BaseDocument();
            myObject.setKey(companyID);
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

    public void updateCompany(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID, String industryType,String companyLocation
           ,String companytype,ArrayList<String>specialities,ArrayList<String> posts,ArrayList<String>jobListings) {
        String collectionName = config.getArangoConfigProp("collection.companies.name");

       Company company = this.getCompany(companyID);
        System.out.println(company.getPosts());
        if(companyName!=null)
        company.setCompanyName(companyName);
        if(companyProfilePicture!=null)
            company.setCompanyProfilePicture(companyProfilePicture);
        if(companyLocation!=null)
            company.setCompanyLocation(companyLocation);
        if(companytype==null)
           company.setCompanytype(companytype);
        if(industryType!=null)
            company.setIndustryType(industryType);
        if(jobListings!=null&&jobListings.size()!=0)
           company.updateJobListings(jobListings);
        if(posts!=null&&posts.size()!=0)
            company.updatePosts(posts);
        try {
            dbInstance.collection(collectionName).updateDocument(companyID+"", company);
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
    public void updateProfile(HashMap<String, Object> updates, String userID){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        User user = getUserProfile(userID);
        PersonalInfo personalInfo = user.getPersonalInfo();
        if(personalInfo == null)
            personalInfo = new PersonalInfo();
        Location location = personalInfo.getLocation();
        ArrayList<String> bookmarks = user.getBookmarkedPosts();
        if(bookmarks == null)
            bookmarks = new ArrayList<String>();
        if(updates.containsKey("firstName"))
            user.setFirstName((String)updates.get("firstName"));

        if(updates.containsKey("lastName"))
            user.setLastName((String)updates.get("lastName"));

        if(updates.containsKey("headline"))
            user.setHeadline((String)updates.get("headline"));
        if(updates.containsKey("personalInfo.phone"))
            personalInfo.setPhone((String)(updates.get("personalInfo.phone")));
        if(updates.containsKey("personalInfo.email"))
            personalInfo.setEmail((String)(updates.get("personalInfo.email")));

        if(updates.containsKey("personalInfo.dob"))
            personalInfo.setDob((String)(updates.get("personalInfo.dob")));

        if(updates.containsKey("personalInfo.location.address"))
            location.setAddress((String)updates.get("personalInfo.location.address"));

        if(updates.containsKey("personalInfo.location.country"))
            location.setCountry((String)updates.get("personalInfo.location.country"));

        if(updates.containsKey("personalInfo.location.country"))
            location.setCountry((String)updates.get("personalInfo.location.country"));

        if(updates.containsKey("personalInfo.location.code"))
            location.setCountry((String)updates.get("personalInfo.location.code"));

        personalInfo.setLocation(location);
        if(updates.containsKey("personalInfo.website"))
            personalInfo.setWebsite((String)(updates.get("personalInfo.website")));
        user.setPersonalInfo(personalInfo);
        if(updates.containsKey("numConnections"))
            user.setNumConnections((String)updates.get("numConnections"));
        if(updates.containsKey("numFollowers"))
            user.setNumFollowers((String)updates.get("numFollowers"));
        if(updates.containsKey("summary"))
            user.setSummary((String)updates.get("summary"));
        if(updates.containsKey("imageUrl"))
            user.setImageUrl((String)updates.get("imageUrl"));
        if(updates.containsKey("cvUrl"))
            user.setCvUrl((String)updates.get("cvUrl"));
        if(updates.containsKey("bookmarkedPosts")) {
            bookmarks.add((String)updates.get("bookmarkedPosts"));
            user.setBookmarkedPosts(bookmarks);
        }
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);
    }

    public void updateEducation(HashMap<String, String> updates, String userID){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        User user = getUserProfile(userID);
        ArrayList<Education> educations  = user.getEducations();
        if(educations == null)
            educations = new ArrayList<Education>();
        for (Map.Entry<String, String> entry: updates.entrySet()){
                    String[] values = entry.getKey().split("#");
                    int idx = Integer.parseInt(values[1]);
                    HashMap<String, Integer> Types = new HashMap<String, Integer>();
                    Types.put("schoolName", 1);
                    Types.put("fieldOfStudy", 2);
                    Types.put("startDate", 3);
                    Types.put("endDate", 4);
                    Types.put("degree", 5);
                     int type = Types.get(values[0]);
                     if(educations.size() <= idx)
                         educations.add(new Education());
                    switch (type){
                        case 1: educations.get(idx).setSchoolName(entry.getValue());break;
                        case 2: educations.get(idx).setFieldOfStudy(entry.getValue());break;
                        case 3 : educations.get(idx).setStartDate(entry.getValue());break;
                        case 4 : educations.get(idx).setEndDate(entry.getValue());break;
                        case 5: educations.get(idx).setDegree(entry.getValue());
                        default: break;
                    }
        }
        user.setEducations(educations);
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);
    }

    public void UpdatePositions(HashMap<String, String> updates, String userID){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        User user = getUserProfile(userID);
        ArrayList<Position> positions  = user.getPositions();
        if(positions == null)
            positions = new ArrayList<Position>();
        for (Map.Entry<String, String> entry: updates.entrySet()){
            String[] values = entry.getKey().split("#");
            int idx = Integer.parseInt(values[1]);
            HashMap<String, Integer> Types = new HashMap<String, Integer>();
            Types.put("title", 1);
            Types.put("summary", 2);
            Types.put("startDate", 3);
            Types.put("endDate", 4);
            Types.put("isCurrent", 5);
            Types.put("companyName", 6);
            Types.put("companyID", 7);
            int type = Types.get(values[0]);
            if(positions.size() <= idx)
                positions.add(new Position());
            switch (type){
                case 1: positions.get(idx).setTitle(entry.getValue());break;
                case 2: positions.get(idx).setSummary(entry.getValue());break;
                case 3 : positions.get(idx).setStartDate(entry.getValue());break;
                case 4 :  positions.get(idx).setEndDate(entry.getValue());break;
                case 5: if(entry.getKey().equals("false"))
                    positions.get(idx).setCurrent(false);
                else
                    positions.get(idx).setCurrent(true);
                    break;
                case 6: positions.get(idx).setCompanyName(entry.getValue());break;
                case 7: positions.get(idx).setCompanyID(entry.getValue());break;
                default: break;
            }
        }
        user.setPositions(positions);
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);
    }

    public void UpdateFriendsList(HashMap<String, String> updates, String userID){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        User user = getUserProfile(userID);
        ArrayList<FriendsList> friendsLists  = user.getFriendsList();
        if(friendsLists == null)
            friendsLists = new ArrayList<FriendsList>();
        for (Map.Entry<String, String> entry: updates.entrySet()){
            String[] values = entry.getKey().split("#");
            int idx = Integer.parseInt(values[1]);
            HashMap<String, Integer> Types = new HashMap<String, Integer>();
            Types.put("userId", 1);
            Types.put("firstName", 2);
            Types.put("lastName", 3);
            Types.put("imageURL", 4);
            Types.put("headline", 5);
            int type = Types.get(values[0]);
            if(friendsLists.size() <= idx)
                friendsLists.add(new FriendsList());
            switch (type){
                case 1: friendsLists.get(idx).setUserId(entry.getValue());break;
                case 2: friendsLists.get(idx).setFirstName(entry.getValue());break;
                case 3 : friendsLists.get(idx).setLastName(entry.getValue());break;
                case 4 :  friendsLists.get(idx).setImageURL(entry.getValue());break;
                case 5: friendsLists.get(idx).setHeadline(entry.getValue());break;
                default: break;
            }
        }
        user.setFriendsList(friendsLists);
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);

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


    public void addCV(String userID,String cv){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        User user = getUserProfile(userID);
        user.setCvUrl(cv);
        dbInstance.collection(UsersCollectionName).updateDocument(userID,user);
    }

    public void deleteCV(String userID) {
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        User user = getUserProfile(userID);
        user.setCvUrl("");
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);
    }

    /*
    * *
     * Get the profile of the user
     * @param userId : the id of the user
     * @return the queried user profile
     */

    public User getUserProfile(String UserID){
        String UsersCollectionName = config.getArangoConfigProp("collection.users.name");
        User UserProfile = dbInstance.collection(UsersCollectionName).getDocument(UserID,
                User.class);

        return UserProfile;
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

