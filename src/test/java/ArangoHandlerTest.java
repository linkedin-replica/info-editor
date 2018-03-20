import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import database.DatabaseSeed;
//import models.testUser;
import org.junit.*;
import utils.ConfigReader;
import models.Company;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import models.User;


import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
    private static ArangoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    private static DatabaseSeed databaseSeed;
    static ConfigReader config;

    @BeforeClass
    public static void init() throws  IOException, org.json.simple.parser.ParseException{
        ConfigReader.isTesting = true;
        databaseSeed = new DatabaseSeed();
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
        databaseSeed.insertUsers();
    }



    @Test
    public void testCreateProfile() throws IOException {
        HashMap<String, String> profileAttributes = new HashMap<String, String>();
        profileAttributes.put("firstName", "Bebo");
        profileAttributes.put("lastName", "Elmalek");
        arangoHandler.createProfile(profileAttributes, "5");
        User myUser = arangoHandler.getUserProfile("5");
        assertEquals("Expected matching first name", "Bebo", myUser.getFirstName());
        assertEquals("Expected matching last name", "Elmalek", myUser.getLastName());
    }

    @Test
    public void testGetProfile() throws IOException {
        User myUser = arangoHandler.getUserProfile("0");
        assertEquals("Expected matching first name", "Omar", myUser.getFirstName());
        assertEquals("Expected matching last name", "Radwan", myUser.getLastName());
        assertEquals("Expected matching headline", "Software Engineer at DFKI", myUser.getHeadline());
        assertEquals("Expected matching skills", "Ahmed", myUser.getFriendsList().get(0).getFirstName());
    }

//    @Test
//    public void testGetCompany() throws IOException {
//        String collectionName = config.getArangoConfig("collection.companies.name");
//        long time = System.currentTimeMillis();
////        Company company =
////                new Company("Microsoft", 14, "profilepicture", "bill gates", 12,
////                        "software1", "cairo", "software", null, null);
//        int userId = 12345;
//        ArangoHandler arangoHandler = new ArangoHandler();
//        arangoHandler.insertCompany("Microsoft2", "1434", "profilepicture2", "bill gate2s", "1","Cairo",
//                "software1", "cair2o", new String[1],new String[1],null);
//        Company companytemp = arangoHandler.getCompany("1434");
//        System.out.println(companytemp.toString());
//        assertEquals("companies name are the same", "Microsoft2", companytemp.getCompanyName());
//        assertEquals("companies profile picture are the same", "profilepicture2",companytemp.getCompanyProfilePicture());
////        assertEquals("companies admin users are the same","bill gates",companytemp.getAdminUserName());
//        assertEquals("companies admin users names are the same","bill gates",companytemp.getAdminUserName());
//        assertEquals("companies name are the same", "Microsoft", companytemp.getCompanyName());
//        assertEquals("companies profile picture are the same", "profilepicture",companytemp.getCompanyProfilePicture());
//        assertEquals("companies admin users are the same","bill gates",companytemp.getAdminUserName());
//        assertEquals("companies admin users names are the same","bill gates",companytemp.getAdminUserName());
////    }
//    }
//
//    @Test
//    public void testAddNewSkill() throws IOException {
//          arangoHandler.addSkill("3","ACMER");
//        arangoHandler.addSkill("2","ACMER");
//        User myUser = arangoHandler.getUserProfile("3");
//        User myUser1 = arangoHandler.getUserProfile("2");
//        assertEquals("Expected matching added skill", "ACMER" , myUser.getSkills().get(0));
//        assertEquals("Expected matching skills count", "ACMER" , myUser1.getSkills().
//                get(myUser.getSkills().size()));
//    }

    @Test
    public void testUpdateProfile() throws IOException {
        HashMap<String, String> updates = new HashMap<String, String>();
        updates.put("firstName", "baher");
        updates.put("lastName", "Abdou");
        updates.put("numConnections", "7");
        updates.put("personalInfo.email", "bebo@gmail.com");
        updates.put("bookmarkedPosts", "udcity course");
        updates.put("imageUrl", "www.image.com");
        updates.put("numFollowers", "10");
        arangoHandler.updateProfile(updates, "0");
        User myUser = arangoHandler.getUserProfile("0");
        assertEquals("Expected matching first name", "baher", myUser.getFirstName());
        assertEquals("Expected matching last name", "Abdou", myUser.getLastName());
        assertEquals("Expected matching numConnections", "7", myUser.getNumConnections());
        assertEquals("Expected matching personalInfo Email", "bebo@gmail.com",
                myUser.getPersonalInfo().getEmail());
        assertEquals("Expected matching numFollower", "10", myUser.getNumFollowers());
        assertEquals("Expected matching imageUrl", "www.image.com", myUser.getImageUrl());
        assertEquals("Expected matching bookmarks", "udcity course", myUser.getBookmarkedPosts().get(0));
    }

//    @Test
//    public void testaddCv() throws IOException {
//        String collectionName = config.getArangoConfig("collection.users.name");
//        ArangoHandler arangoHandler = new ArangoHandler();
//       arangoHandler.addCV(12+"","my cv");
//       User user = arangoHandler.getUser(12+"");
//       assertEquals("the two cvs should matches",user.getCvUrl(),"my cv");
//    }
//    @Test
//    public void testDeleteCv() throws IOException {
//        String collectionName = config.getArangoConfig("collection.users.name");
//        ArangoHandler arangoHandler = new ArangoHandler();
//        arangoHandler.deleteCV(12+"");
//        User user = arangoHandler.getUser(12+"");
//        assertEquals("the two cvs should matches",user.getCvUrl(),"");
//    }


    @Test
    public void testUpdateProfileEducation() throws IOException {
        HashMap<String, String> updates = new HashMap<String, String>();
        updates.put("schoolName#0", "Future");
        updates.put("fieldOfStudy#1", "Mathmatical");
        arangoHandler.updateEducation(updates, "0");
        User myUser = arangoHandler.getUserProfile("0");
        assertEquals("Expected matching school Name", "Future", myUser.getEducations().get(0).getSchoolName());
        assertEquals("Expected matching fieldOfStudy", "Mathmatical", myUser.getEducations().get(1).getFieldOfStudy());
    }

    @Test
    public void testUpdateProfilePositions() throws IOException {
        HashMap<String, String> updates = new HashMap<String, String>();
        updates.put("title#0", "Manager");
        updates.put("startDate#1", "19.08.2018");
        arangoHandler.UpdatePositions(updates, "0");
        User myUser = arangoHandler.getUserProfile("0");
        assertEquals("Expected matching title", "Manager", myUser.getPositions().get(0).getTitle());
        assertEquals("Expected matching startDate", "19.08.2018", myUser.getPositions().get(1).getStartDate());
    }




    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllUsers();
    }


}