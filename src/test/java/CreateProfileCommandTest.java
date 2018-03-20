import com.arangodb.ArangoDatabase;
import commands.GetUserProfileCommand;
import database.ArangoHandler;
import database.DatabaseConnection;
import database.DatabaseSeed;
import infoEditor.*;
import models.Command;
import models.User;
import org.junit.*;
import utils.ConfigReader;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class CreateProfileCommandTest {

    private static Command command;
    private static ArangoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    private static DatabaseSeed databaseSeed;
    static ConfigReader config;


    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException {
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
    public void execute()  throws IOException{
        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;
        args.put("userId", "10");
        args.put("firstName", "Loa2y");
        args.put("lastName", "Zobeidy");
        command = new CreateProfileCommand(args);
        command.setDbHandler(arangoHandler);
        command.execute();
        command = new GetUserProfileCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        User myUser = (User) response.get("results");
        assertEquals("Expected matching first name", "Loa2y" , myUser.getFirstName());
        assertEquals("Expected matching last name", "Zobeidy" , myUser.getLastName());
    }

    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllUsers();
    }
}