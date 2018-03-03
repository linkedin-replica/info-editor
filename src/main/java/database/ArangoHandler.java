package database;
import java.util.*;
import models.*;
import java.io.IOException;
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
        // TODO
        arangoDB = new ArangoDB.Builder().build();
    }


    public void disconnect() {
        // TODO
    }
    public Company getCompany(String companyID){
        String query = "For t in " + collectionName + " FILTER t.companyId == @companyId RETURN t";
        Map<String, Object> bindVars = new HashMap<String, Object>();
        bindVars.put("companyId", companyID);
        ArangoCursor<Company> cursor = dbInstance.query(query, bindVars, null, Company.class);

        return cursor.next();
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
        String query = "FOR u IN users\n "+"FILTER u.userId ==  @userID"+"UPDATE u WITH{ Cv:@cv} IN users";
        Map<String, Object> bindVars = new HashMap<String, Object>();
        bindVars.put("userId", userID);
        bindVars.put("cv",cv);

        dbInstance.query(query, bindVars, null, Company.class);

    }

    public void deleteCV(String userID){
        String nil= "";
        String result = "";
            String query = "FOR u IN users\n "+"FILTER u.userId ==  @userID"+"UPDATE u WITH{ "+result+"Cv:@nil} IN users";
            Map<String, Object> bindVars = new HashMap<String, Object>();
            bindVars.put("userId", userID);
            bindVars.put("nil",nil);
           dbInstance.query(query, bindVars, null, Company.class);
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

