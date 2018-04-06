package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
//import models.testUser;
import org.junit.*;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.linkedin.replica.editInfo.models.*;


import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    private static DatabaseSeed databaseSeed;
    static Configuration config;

    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException, SQLException, ClassNotFoundException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        databaseSeed = new DatabaseSeed();
        arangoHandler = new ArangoEditInfoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                config.getArangoConfigProp("db.name")
        );
        databaseSeed.insertUsers();
        databaseSeed.insertCompanies();
    }



    @Test
    public void testCreateProfile() throws IOException {
        HashMap<String, Object> profileAttributes = new HashMap<String, Object>();
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

    @Test
    public void testGetCompany() throws IOException {
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        Company companytemp = arangoHandler.getCompany("1");
//        System.out.println(companytemp);

        assertEquals("name should be update",companytemp.getCompanyID(),"1");
//    }x`
    }
    @Test
    public void testUpdateCompany() throws IOException {
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        ArrayList<String>posts = new ArrayList<String>();
        posts.add("hello world");
//        Company companytemp = arangoHandler.getCompany("1");
//        System.out.println(companytemp);
       arangoHandler.updateCompany("microsoft3","1",null,null,null,null,null,null,null,posts,null);
        Company companytemp2 = arangoHandler.getCompany("1");

//        System.out.println(companytemp.toString());
         assertEquals("name should be update",companytemp2.getCompanyName(),"microsoft3");
        assertEquals("name should be update",companytemp2.getPosts().size(),4);

    //    }
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
    HashMap<String, Object> updates = new HashMap<String, Object>();
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
//    public void testUpdateProfile() throws IOException {
//        HashMap<String, String> updates = new HashMap<String, String>();
//        updates.put("firstName", "baher");
//        updates.put("lastName", "Abdou");
//        updates.put("numConnections", "7");
//        updates.put("personalInfo.email", "bebo@gmail.com");
//        updates.put("bookmarkedPosts", "udcity course");
//        updates.put("imageUrl", "www.image.com");
//        updates.put("numFollowers", "10");
//        arangoHandler.updateProfile(updates, "0");
//        User myUser = arangoHandler.getUserProfile("0");
//        assertEquals("Expected matching first name", "baher", myUser.getFirstName());
//        assertEquals("Expected matching last name", "Abdou", myUser.getLastName());
//        assertEquals("Expected matching numConnections", "7", myUser.getNumConnections());
//        assertEquals("Expected matching personalInfo Email", "bebo@gmail.com",
//                myUser.getPersonalInfo().getEmail());
//        assertEquals("Expected matching numFollower", "10", myUser.getNumFollowers());
    //        assertEquals("Expected matching imageUrl", "www.image.com", myUser.getImageUrl());
    //        assertEquals("Expected matching bookmarks", "udcity course", myUser.getBookmarkedPosts().get(0));
    //    }

    //    @Test
    //    public void testaddCv() throws IOException {
    //        String collectionName = config.getArangoConfig("collection.users.name");
    //        ArangoHandler arangoHandler = new ArangoHandler();
    //       arangoHandler.addCV(12+"","my cv");
    //       User user = arangoHandler.getUser(12+"");
    //       assertEquals("the two cvs should matches",user.getCvUrl(),"my cv");
    //    }
        @Test
        public void testDeleteCv() throws IOException {
            String collectionName = config.getArangoConfigProp("collection.users.name");
            arangoHandler.deleteCV(12+"");
            User user = arangoHandler.getUserProfile("12");
            assertEquals("the two cvs should matches",user.getCvUrl(),"");
        }


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
        String dbName = config.getArangoConfigProp("db.name");
        databaseSeed.deleteAllUsers();
        databaseSeed.deleteAllCompanies();
    }
//

}