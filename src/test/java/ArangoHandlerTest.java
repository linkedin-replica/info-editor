import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;

import models.Company;
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

    @Before
    public void initBeforeTest() throws IOException {
        arangoDb.createCollection(
                config.getArangoConfig("collection.notifications.name")
        );
    }

    public void testSendNotification() throws IOException {
        String collectionName = config.getArangoConfig("collection.notifications.name");
        long time = System.currentTimeMillis();
        Company company =
                new Company("Microsoft",
        12344,
       "url/test",
        "billGates",
        1,
        1,
        "software","Silicon Valley",null,
        "About us","www.microsoft.com","1293",null,"software",2000,null,null,null);
        int userId = 12345;

//        assertEquals("Expected matching Com text", "notification text", .getNotificationText());
//        assertEquals("Expected matching notification link", "notification link", newNotification.getLink());
//        assertEquals("Expected matching notification time", time, newNotification.getTimeStamp());
//        assertEquals("Expected notification to be unread", false, newNotification.isRead());
    }

    @After
    public void cleanAfterTest() throws IOException {
        arangoDb.collection(
                config.getArangoConfig("collection.notifications.name")
        ).drop();
    }

    @AfterClass
    public static void clean() throws IOException {
        ConfigReader.isTesting = false;
        DatabaseConnection.getDBConnection().closeConnections();
    }
}