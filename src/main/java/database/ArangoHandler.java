package database;
import java.util.*;
import models.*;
import java.io.IOException;
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

    public void updateProfile(HashMap<String, String> updates, String userID){
        String UsersCollectionName = config.getConfig("collection.users.name");
        User user = getUserProfile(userID);
        PersonalInfo personalInfo = user.getPersonalInfo();
        if(personalInfo == null)
            personalInfo = new PersonalInfo();
        Location location = personalInfo.getLocation();
        ArrayList<String> bookmarks = user.getBookmarkedPosts();
        if(bookmarks == null)
            bookmarks = new ArrayList<String>();
        if(updates.containsKey("firstName"))
            user.setFirstName(updates.get("firstName"));

        if(updates.containsKey("lastName"))
            user.setLastName(updates.get("lastName"));

        if(updates.containsKey("headline"))
            user.setHeadline(updates.get("headline"));
        if(updates.containsKey("personalInfo.phone"))
            personalInfo.setPhone((String)(updates.get("personalInfo.phone")));
        if(updates.containsKey("personalInfo.email"))
            personalInfo.setEmail((String)(updates.get("personalInfo.email")));

        if(updates.containsKey("personalInfo.dob"))
            personalInfo.setDob((String)(updates.get("personalInfo.dob")));

        if(updates.containsKey("personalInfo.location.address"))
            location.setAddress(updates.get("personalInfo.location.address"));

        if(updates.containsKey("personalInfo.location.country"))
            location.setCountry(updates.get("personalInfo.location.country"));

        if(updates.containsKey("personalInfo.location.country"))
            location.setCountry(updates.get("personalInfo.location.country"));

        if(updates.containsKey("personalInfo.location.code"))
            location.setCountry(updates.get("personalInfo.location.code"));

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
        String UsersCollectionName = config.getConfig("collection.users.name");
        User user = getUserProfile(userID);
        ArrayList<Education> educations  = user.getEducations();
        if(educations == null)
            educations = new ArrayList<Education>();
        for (Map.Entry<String, String> entry: updates.entrySet()){
                    String[] values = entry.getKey().split(".");
                    int idx = Integer.parseInt(values[1]);
                    HashMap<String, Integer> Types = new HashMap<String, Integer>();
                    Types.put("schoolName", 1);
                    Types.put("fieldOfStudy", 2);
                    Types.put("startDate", 3);
                    Types.put("endDate", 4);
                    Types.put("degree", 5);
                     int type = Types.get(values[0]);
                     if(educations.get(idx) == null)
                         educations.set(idx,new Education());
                    switch (type){
                        case 1: educations.get(idx).setSchoolName(entry.getKey());break;
                        case 2: educations.get(idx).setFieldOfStudy(entry.getKey());break;
                        case 3 : educations.get(idx).setStartDate(entry.getKey());break;
                        case 4 : educations.get(idx).setEndDate(entry.getKey());break;
                        case 5: educations.get(idx).setDegree(entry.getKey());
                        default: break;
                    }
        }
        user.setEducations(educations);
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);
    }

    public void UpdatePositions(HashMap<String, String> updates, String userID){
        String UsersCollectionName = config.getConfig("collection.users.name");
        User user = getUserProfile(userID);
        ArrayList<Position> positions  = user.getPositions();
        if(positions == null)
            positions = new ArrayList<Position>();
        for (Map.Entry<String, String> entry: updates.entrySet()){
            String[] values = entry.getKey().split(".");
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
            if(positions.get(idx) == null)
                positions.set(idx,new Position());
            switch (type){
                case 1: positions.get(idx).setTitle(entry.getKey());break;
                case 2: positions.get(idx).setSummary(entry.getKey());break;
                case 3 : positions.get(idx).setStartDate(entry.getKey());break;
                case 4 :  positions.get(idx).setEndDate(entry.getKey());break;
                case 5: if(entry.getKey().equals("false"))
                    positions.get(idx).setCurrent(false);
                else
                    positions.get(idx).setCurrent(true);
                    break;
                case 6: positions.get(idx).setCompanyName(entry.getKey());break;
                case 7: positions.get(idx).setCompanyID(entry.getKey());break;
                default: break;
            }
        }
        user.setPositions(positions);
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);
    }

    public void UpdateFriendsList(HashMap<String, String> updates, String userID){
        String UsersCollectionName = config.getConfig("collection.users.name");
        User user = getUserProfile(userID);
        ArrayList<FriendsList> friendsLists  = user.getFriendsList();
        for (Map.Entry<String, String> entry: updates.entrySet()){
            String[] values = entry.getKey().split(".");
            int idx = Integer.parseInt(values[1]);
            HashMap<String, Integer> Types = new HashMap<String, Integer>();
            Types.put("userId", 1);
            Types.put("firstName", 2);
            Types.put("lastName", 3);
            Types.put("imageURL", 4);
            Types.put("headline", 5);
            int type = Types.get(values[0]);
            switch (type){
                case 1: friendsLists.get(idx).setUserId(entry.getKey());break;
                case 2: friendsLists.get(idx).setFirstName(entry.getKey());break;
                case 3 : friendsLists.get(idx).setLastName(entry.getKey());break;
                case 4 :  friendsLists.get(idx).setImageURL(entry.getKey());break;
                case 5: friendsLists.get(idx).setHeadline(entry.getKey());break;
                default: break;
            }
        }
        user.setFriendsList(friendsLists);
        dbInstance.collection(UsersCollectionName).updateDocument(userID, user);

    }

//


    /*
     * *
     * Add new skill in the user profile
     * @param userId : the id of the user and the new skill
     */
    public void addSkill(String userID, String Skill){
        User user = getUserProfile(userID);
        String UsersCollectionName = config.getConfig("collection.users.name");
        ArrayList<String>  skills =  user.getSkills();
        if(skills == null)
            skills = new ArrayList<String>();
        skills.add(Skill);
        BaseDocument newProfile = new BaseDocument();
        newProfile.addAttribute("skills", skills);
//
//        String query = "For t in " + UsersCollectionName + " FILTER " +
//                "t.userId == @userId" + " UPDATE t WITH{ skills:@skills} IN users";
//        Map<String, Object> bindVars = new HashMap<String, Object>();
//        bindVars.put("userId", userID);
//        bindVars.put("skills",skills);
        dbInstance.collection(UsersCollectionName).updateDocument(userID, newProfile);
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

    public void deleteCV(String userID) {
        String nil = "";
        BaseDocument baseDocument = new BaseDocument();
        baseDocument.addAttribute("cv", "");
        dbInstance.collection("users").updateDocument(userID, baseDocument);
    }

    /*
    * *
     * Get the profile of the user
     * @param userId : the id of the user
     * @return the queried user profile
     */

    public User getUserProfile(String UserID){
        String UsersCollectionName = config.getConfig("collection.users.name");

//        String query = "For t in " + UsersCollectionName + " FILTER " +
//                "t.userId == @userId" +
//                " RETURN t";
//        Map<String, Object> bindVars = new HashMap<String,Object>();
//        bindVars.put("userId", UserID);
        // process query
        User UserProfile = dbInstance.collection(UsersCollectionName).getDocument(UserID,
                User.class);
        return UserProfile;
    }




    public ArangoHandler()throws IOException {
        config = new ConfigReader("arango_names");
        // init db
        ArangoDB arangoDriver = DatabaseConnection.getDBConnection().getArangoDriver();
        collectionName = config.getConfig("collection.users.name");
        dbInstance = arangoDriver.db(config.getConfig("db.name"));
        collection = dbInstance.collection(collectionName);
    }
    public static void main(String [] srgs){
    }
}

