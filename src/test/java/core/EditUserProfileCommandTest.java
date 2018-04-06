package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.cache.handlers.impl.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.cache.handlers.impl.JedisCacheHandler;
import com.linkedin.replica.editInfo.commands.impl.EditProfileDetailsCommand;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class EditUserProfileCommandTest {
    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static Configuration config;
    private static DatabaseSeed databaseSeed;
    private  static JedisCacheHandler cacheEditInfoHandler;



    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        databaseSeed = new DatabaseSeed();
        cacheEditInfoHandler = new JedisCacheHandler();
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
        args.put("userId", "0");
        args.put("firstName", "Baher");
        args.put("headline", "Graduate");
        String [] ids = new String[1];
        ids[0]="1";
        ArrayList<User> users = new ArrayList<User>();
        User user = new User("0","bahber","ahmed","","Software",new PersonalInfo(),"","","",new ArrayList<Position>(),new ArrayList<Education>(),"","",new ArrayList<String>(),new ArrayList<FriendsList>(),new ArrayList<String>());
        users.add(user);
        cacheEditInfoHandler.saveUsersInCache(ids,users);
        Command command2 = new EditProfileDetailsCommand(args);
        command2.setDbHandler(arangoHandler);
        command2.setCacheHandler(cacheEditInfoHandler);
        command2.execute();
        command = new GetUserProfileCommand(args);
        command.setDbHandler(arangoHandler);
        command.setCacheHandler(cacheEditInfoHandler);
        response = command.execute();
        ArrayList<User> users2 =(ArrayList<User>)response;
        User myUser = (User) users2.get(0) ;
        assertEquals("Expected matching first name", "Baher" , myUser.getFirstName());
        assertEquals("Expected matching last name", "Radwan" , myUser.getLastName());
        assertEquals("Expected matching headline", "Graduate" , myUser.getHeadline());
    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");
        databaseSeed.deleteAllUsers();
    }
}
