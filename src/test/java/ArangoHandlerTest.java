import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import models.User;
import org.junit.*;
import utils.ConfigReader;

import java.io.IOException;

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

        User myUser = arangoHandler.getUserProfile("0");
        assertEquals("Expected matching first name", "Omar", myUser.getFirstName());
        assertEquals("Expected matching last name", "Radwan", myUser.getLastName());
        assertEquals("Expected matching headline", "Software Engineer at DFKI", myUser.getHeadline());
        assertEquals("Expected matching skills", 0, myUser.getNumConnections());
        assertEquals("Expected matching skills", "Ahmed", myUser.getFriendsList().get(0).getFirstName());
    }

    @Test
    public void testAddNewSkill() throws IOException {
          arangoHandler.addSkill("3","ACMER");
        arangoHandler.addSkill("2","ACMER");
        User myUser = arangoHandler.getUserProfile("3");
        User myUser1 = arangoHandler.getUserProfile("2");
        assertEquals("Expected matching added skill", "ACMER" , myUser.getSkills().get(0));
        assertEquals("Expected matching skills count", 5 , myUser1.getSkills().size());

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