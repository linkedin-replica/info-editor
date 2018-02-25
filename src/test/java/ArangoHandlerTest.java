import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
//import models.testUser;
import org.junit.*;
import utils.ConfigReader;
import models.Company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.User;
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
    public void testGetCompany() throws IOException {
        String collectionName = config.getArangoConfig("collection.companies.name");
        long time = System.currentTimeMillis();
//        Company company =
//                new Company("Microsoft", 14, "profilepicture", "bill gates", 12,
//                        "software1", "cairo", "software", null, null);
        int userId = 12345;
        ArangoHandler arangoHandler = new ArangoHandler();
        arangoHandler.insertCompany("Microsoft", 14, "profilepicture", "bill gates", 12,
                "software1", "cairo", "software", new String[1], new String[1],new String[1]);
        Company companytemp = arangoHandler.getCompany(14 + "");
        System.out.println(companytemp.toString());
        assertEquals("companies name are the same", "Microsoft", companytemp.getCompanyName());
        assertEquals("companies profile picture are the same", "profilepicture",companytemp.getCompanyProfilePicture());
//        assertEquals("companies admin users are the same","bill gates",companytemp.getAdminUserName());
//        assertEquals("companies admin users names are the same","bill gates",companytemp.getAdminUserName());
//        assertEquals("companies name are the same", "Microsoft", companytemp.getCompanyName());
//        assertEquals("companies profile picture are the same", "profilepicture",companytemp.getCompanyProfilePicture());
//        assertEquals("companies admin users are the same","bill gates",companytemp.getAdminUserName());
//        assertEquals("companies admin users names are the same","bill gates",companytemp.getAdminUserName());
//    }
    }


//    @AfterClass
//    public void cleanAfterTest() throws IOException {
//        arangoDb.collection(
//                config.getArangoConfig("collection.users.name")
//        ).drop();
//    }drop
//
//    @AfterClass
//    public static void clean() throws IOException {
//        ConfigReader.isTesting = false;
//        DatabaseConnection.getDBConnection().closeConnections();
//    }


//
//    @Before
//    public void initBeforeTest() throws IOException {
//        arangoDb.createCollection(
//                config.getArangoConfig("collection.notifications.name")
//        );
//    }

    @Test
    public void testaddCv() throws IOException {
        String collectionName = config.getArangoConfig("collection.users.name");
        ArangoHandler arangoHandler = new ArangoHandler();
       arangoHandler.addCV(12+"","my cv");
       User user = arangoHandler.getUser(12+"");
       assertEquals("the two cvs should matches",user.cv,"my cv");
    }
    @Test
    public void testDeleteCv() throws IOException {
        String collectionName = config.getArangoConfig("collection.users.name");
        ArangoHandler arangoHandler = new ArangoHandler();
        arangoHandler.deleteCV(12+"");
        User user = arangoHandler.getUser(12+"");
        assertEquals("the two cvs should matches",user.cv,"");
    }
//    @After
//    public void cleanAfterTest()throws IOException {
//        arangoDb.collection(
//                config.getArangoConfig("collection.notifications.name")
//        ).drop();
//    }

    @AfterClass
    public static void clean()throws IOException{
        ConfigReader.isTesting = false;
        DatabaseConnection.getDBConnection().closeConnections();
    }
}