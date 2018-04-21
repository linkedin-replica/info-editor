package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.cache.handlers.impl.JedisCacheHandler;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.commands.Command;

import org.junit.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class GetUserProfileCommandTest {

    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    private static DatabaseSeed databaseSeed;
    static Configuration config;
    private static JedisCacheHandler jedisCacheHandler;


    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        databaseSeed = new DatabaseSeed();
        jedisCacheHandler = new JedisCacheHandler();
        arangoHandler = new ArangoEditInfoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                config.getArangoConfigProp("db.name")
        );
        //databaseSeed.insertUsers();
    }

    
    @Test
    public void execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HashMap<String, Object> args = new HashMap();
        Object response;
        args.put("userId", "1");
        command = new GetUserProfileCommand(args);
        command.setDbHandler(arangoHandler);
        command.setCacheHandler(jedisCacheHandler);
        response = command.execute();
////        User myUser = (User) response.get("results");
//        assertEquals("Expected matching first name", "Omar" , myUser.getFirstName());
//        assertEquals("Expected matching last name", "Radwan" , myUser.getLastName());
//        assertEquals("Expected matching headline", "Software Engineer at DFKI" , myUser.getHeadline());
    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");
       // databaseSeed.deleteAllUsers();
    }
}