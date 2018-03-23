package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.commands.impl.AddCvCommand;
import com.linkedin.replica.editInfo.commands.impl.AddNewSkillCommand;
import com.linkedin.replica.editInfo.commands.impl.DeleteCvCommand;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.models.Company;
import com.linkedin.replica.editInfo.models.User;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.commands.*;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class CvCommandTest{
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static utils.ConfigReader config;
    static DatabaseSeed databaseSeed;
    @BeforeClass
    public static void init() throws IOException, ParseException, org.json.simple.parser.ParseException {
        utils.ConfigReader.isTesting = true;
        config = utils.ConfigReader.getInstance();
        databaseSeed = new DatabaseSeed();
        arangoHandler = new ArangoEditInfoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                utils.ConfigReader.getInstance().getArangoConfig("db.name")
        );
        databaseSeed.insertUsers();
    }





    @Test
    public void AddCvexecute()throws IOException {

        HashMap<String, Object> args = new HashMap();
       Object response;

        args.put("userId", "0");
        args.put("cv","user12URL");
        AddCvCommand command = new AddCvCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        User user = arangoHandler.getUserProfile("0");
        System.out.println((User) user);

        assertEquals("response should be true", user.getCvUrl(), "user12URL");

    }
    @Test
    public void DeleteCvexecute()throws IOException {

        HashMap<String, Object> args = new HashMap<String, Object>();
       Object response;


        args.put("userId", "0");

        DeleteCvCommand command = new DeleteCvCommand(args);
        command.setDbHandler(arangoHandler);
        GetUserProfileCommand command2 = new GetUserProfileCommand(args);
        command2.setDbHandler(arangoHandler);
        command.execute();
        response = command2.execute();

        System.out.println((User) response);
        User user = arangoHandler.getUserProfile("0");
//
        assertEquals("the cv should be null because it is deleted", "", user.getCvUrl());



    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllUsers();
    }
}