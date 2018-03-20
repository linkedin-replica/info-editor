import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import database.DatabaseSeed;
import commands.GetUserProfileCommand;
import models.Command;
import models.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class EditUserProfileCommandTest {
    private static Command command;
    private static ArangoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;
    private static DatabaseSeed databaseSeed;



    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException{
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoHandler();
        databaseSeed = new DatabaseSeed();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
        databaseSeed.insertUsers();
    }


    @Test
    public void execute() throws IOException {
        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;
        args.put("userId", "110265");
        args.put("firstName", "Baher");
        args.put("headline", "Graduate");
        args.put("numConnections","7");
        command = new GetUserProfileCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        User myUser = (User) response.get("results");
        assertEquals("Expected matching first name", "Bebo" , myUser.getFirstName());
        assertEquals("Expected matching last name", "Abdelmalek" , myUser.getLastName());
        assertEquals("Expected matching headline", "Student" , myUser.getHeadline());
    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllUsers();
    }
}
