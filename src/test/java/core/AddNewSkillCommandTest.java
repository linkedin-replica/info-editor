package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.commands.impl.AddNewSkillCommand;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.models.User;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.commands.*;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigReader;
import com.linkedin.replica.editInfo.database.DatabaseSeed;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class AddNewSkillCommandTest {

    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;
    private static DatabaseSeed databaseSeed;


    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        databaseSeed = new DatabaseSeed();
        arangoHandler = new ArangoEditInfoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
        databaseSeed.insertUsers();
    }


    @Test
    public void execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HashMap<String, Object> args = new HashMap();
        Object response;
        args.put("userId", "0");
        args.put("Skill", "Java");
        command = new AddNewSkillCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();

        command = new GetUserProfileCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        User myUser = (User) response;
        assertEquals("Expected LastSkill", "Java" , myUser.getSkills().get(myUser.getSkills().size()-1));
    }

    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
          databaseSeed.deleteAllUsers();
    }

}
