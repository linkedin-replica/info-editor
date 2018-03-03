import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import models.Company;
import org.junit.*;
import utils.ConfigReader;

import infoEditor.DeleteCvCommand;
import infoEditor.GetCompanyProfileCommand;
import infoEditor.AddCvCommand;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CvCommandTest{
    private static ArangoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;
//    @BeforeClass
//    public static void init() throws IOException {
//        ConfigReader.isTesting = true;
//        config = ConfigReader.getInstance();
//        notificationService = new NotificationService();
//        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
//                ConfigReader.getInstance().getArangoConfig("db.name")
//        );
//    }

//    @Before
//    public void initBeforeTest() throws IOException {
//        arangoDb.createCollection(
//                config.getArangoConfig("collection.companies.name")
//        );
//    }


    @BeforeClass
    public static void init() throws IOException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
    }


    @Test
    public void AddCvexecute()throws IOException {

        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;

        args.put("userId", "12");
        AddCvCommand command = new AddCvCommand(args);
        response = command.execute();
        System.out.println((Company) response.get("results"));

        assertEquals("response should be true", ((Boolean) response.get("response")), true);

    }
    @Test
    public void DeleteCvexecute()throws IOException {

        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;

        args.put("userId", "12");
        DeleteCvCommand command = new DeleteCvCommand(args);
        response = command.execute();
        System.out.println((Company) response.get("results"));
//
        assertEquals("response should be true", ((Boolean) response.get("response")), true);

    }
//    @After
//    public void cleanAfterTest() throws IOException {
//        arangoDb.collection(
//                config.getArangoConfig("collection.companies.name")
//        ).drop();
//    }
//
//    @AfterClass
//    public static void clean() throws IOException {
//        ConfigReader.isTesting = false;
//        DatabaseConnection.getDBConnection().closeConnections();
//    }
}