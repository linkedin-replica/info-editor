package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.cache.handlers.impl.JedisCacheHandler;
import com.linkedin.replica.editInfo.commands.impl.CreateProfileCommand;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.User;
import org.junit.*;
import com.linkedin.replica.editInfo.database.DatabaseSeed;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CreateProfileCommandTest {

    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    private static JedisCacheHandler jedisCacheHandler;
    private static DatabaseSeed databaseSeed;
    static Configuration config;


    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        jedisCacheHandler = new JedisCacheHandler();
        databaseSeed = new DatabaseSeed();
        arangoHandler = new ArangoEditInfoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                config.getArangoConfigProp("db.name")
        );
        databaseSeed.insertUsers();
    }


    @Test
    public void execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HashMap<String, Object> args = new HashMap();
        Object response;
        args.put("userId", "10");
        args.put("firstName", "Loa2y");
        args.put("lastName", "Zobeidy");
        command = new CreateProfileCommand(args);
        command.setDbHandler(arangoHandler);
        command.setCacheHandler(jedisCacheHandler);
        command.execute();
        command = new GetUserProfileCommand(args);
        command.setCacheHandler(jedisCacheHandler);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        User myUser = (User) response;
        assertEquals("Expected matching first name", "Loa2y" , myUser.getFirstName());
        assertEquals("Expected matching last name", "Zobeidy" , myUser.getLastName());
    }

    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");
        databaseSeed.deleteAllUsers();
    }
}