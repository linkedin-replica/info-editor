import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import database.DatabaseSeed;
import models.Company;
import models.User;
import org.json.simple.parser.ParseException;
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
    static DatabaseSeed databaseSeed;
    @BeforeClass
    public static void init() throws IOException, ParseException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        databaseSeed = new DatabaseSeed();
        arangoHandler = new ArangoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
        databaseSeed.insertUsers();
    }





    @Test
    public void AddCvexecute()throws IOException {

        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;

        args.put("userId", "0");
        args.put("cv","user12URL");
        AddCvCommand command = new AddCvCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        User user = arangoHandler.getUserProfile("0");
        System.out.println((Company) response.get("results"));

        assertEquals("response should be true", user.getCvUrl(), "user12URL");

    }
    @Test
    public void DeleteCvexecute()throws IOException {

        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;

        args.put("userId", "0");

        DeleteCvCommand command = new DeleteCvCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();

        System.out.println((Company) response.get("results"));
        User user = arangoHandler.getUserProfile("0");
//
        assertEquals("response should be true", user.getCvUrl(), "");

    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllUsers();
    }
}