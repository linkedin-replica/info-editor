import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import models.User;
import org.junit.*;
import utils.ConfigReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
    private static ArangoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;

    @BeforeClass
    public static void init() throws IOException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
    }

//    @Before
//    public void initBeforeTest() throws IOException {
//        arangoDb.createCollection(
//                config.getArangoConfig("collection.users.name")
//        );
//    }

    @Test
    public void testGetProfile() throws IOException {

        User myUser = arangoHandler.getUserProfile("112725");
        assertEquals("Expected matching first name", "Omar", myUser.getFirstName());
        assertEquals("Expected matching last name", "Radwan", myUser.getLastName());
        assertEquals("Expected matching headline", "Software Engineer at DFKI", myUser.getHeadline());
        assertEquals("Expected matching skills", 0, myUser.getNumConnections());
        assertEquals("Expected matching skills", "Ahmed", myUser.getFriendsList().get(0).getFirstName());
    }
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
        updates.put("firstName","baher");
        updates.put("lastName","Abdou");
        updates.put("numConnections", "7");
        updates.put("personalInfo.email","bebo@gmail.com");
        updates.put("bookmarkedPosts", "udcity course");
        updates.put("imageUrl", "www.image.com");
        updates.put("numFollowers", "10");
        arangoHandler.updateProfile(updates,"112725");
        User myUser = arangoHandler.getUserProfile("112725");
        assertEquals("Expected matching first name", "baher" , myUser.getFirstName());
        assertEquals("Expected matching last name", "Abdou" , myUser.getLastName());
        assertEquals("Expected matching numConnections", "7" , myUser.getNumConnections());
        assertEquals("Expected matching personalInfo Email", "bebo@gmail.com" ,
                myUser.getPersonalInfo().getEmail());
        assertEquals("Expected matching numFollower", "10" , myUser.getNumFollowers());
        assertEquals("Expected matching imageUrl", "www.image.com" , myUser.getImageUrl());
        assertEquals("Expected matching bookmarks", "udcity course" , myUser.getBookmarkedPosts().get(0));
    }


    @Test
    public void testUpdateProfileEducation() throws IOException {
        HashMap<String, String> updates = new HashMap<String, String>();
        updates.put("schoolName.0", "Future");
        updates.put("fieldOfStudy.1", "Mathmatical");
        arangoHandler.updateEducation(updates, "112725");
        User myUser = arangoHandler.getUserProfile("112725");
        assertEquals("Expected matching school Name", "Future" , myUser.getEducations().get(0).getSchoolName());
        assertEquals("Expected matching fieldOfStudy", "Mathmatical" , myUser.getEducations().get(1).getFieldOfStudy());
    }

//    @AfterClass
//    public static void cleanAfterTest() throws IOException {
//        arangoDb.collection(
//                config.getArangoConfig("collection.users.name")
//        ).drop();
//    }
//
//    @AfterClass
//    public static void clean() throws IOException {
//        ConfigReader.isTesting = false;
//        DatabaseConnection.getDBConnection().closeConnections();
//    }

}