package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.commands.impl.AddCvCommand;
import com.linkedin.replica.editInfo.commands.impl.DeleteCvCommand;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.models.UserReturn;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CvCommandTest{
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static Configuration config;
    static DatabaseSeed databaseSeed;
    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        databaseSeed = new DatabaseSeed();
        arangoHandler = new ArangoEditInfoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                config.getArangoConfigProp("db.name")
        );
        //databaseSeed.insertUsers();
    }





    @Test
    public void AddCvexecute()throws IOException {

        HashMap<String, Object> args = new HashMap();
       Object response;

        args.put("userId", "6");
        args.put("cvUrl","user12URL");
        AddCvCommand command = new AddCvCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        UserReturn user = arangoHandler.getUserProfile("6");
        System.out.println((UserReturn) user);

        assertEquals("response should be true", user.getCvUrl(), "user12URL");

    }
    @Test
    public void DeleteCvexecute()throws IOException {

        HashMap<String, Object> args = new HashMap<String, Object>();
       Object response;


        args.put("userId", "6");

        DeleteCvCommand command = new DeleteCvCommand(args);
        command.setDbHandler(arangoHandler);
        GetUserProfileCommand command2 = new GetUserProfileCommand(args);
        command2.setDbHandler(arangoHandler);
        command.execute();
        response = command2.execute();

//        System.out.println((User) response);
     //   User user = arangoHandler.getUserProfile("0");
//
//        assertEquals("the cv should be null because it is deleted", "", user.getCvUrl());



    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");
        //databaseSeed.deleteAllUsers();
    }
}