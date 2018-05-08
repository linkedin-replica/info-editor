package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.commands.impl.RemoveSkillCommand;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.UserReturn;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RemoveSkillCommandTest {

    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static Configuration config;
    private static DatabaseSeed databaseSeed;


    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException, SQLException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();

        databaseSeed = new DatabaseSeed();
        arangoHandler = new ArangoEditInfoHandler();
        arangoDb = DatabaseConnection.getInstance().getArangoDriver().db(
                config.getArangoConfigProp("db.name")
        );
        // databaseSeed.insertUsers();
    }


    @Test
    public void execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        HashMap<String, Object> args = new HashMap();
        Object response;
        args.put("userId", "103");
        args.put("skill", "Java4");
        command = new RemoveSkillCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();

        command = new GetUserProfileCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        UserReturn myUser = (UserReturn) response;
        assertEquals("Expected LastSkill", false , myUser.getSkills().contains("Java4"));
    }

    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");
        //   databaseSeed.deleteAllUsers();
    }

}
