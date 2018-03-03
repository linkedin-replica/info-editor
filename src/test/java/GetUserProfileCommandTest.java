
import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import infoEditor.*;
import models.Command;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class GetUserProfileCommandTest {

    private static Command command;
    private static ArangoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;


    @BeforeClass
    public static void init() throws IOException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );

    }

    
    @Test
    public void execute() {
        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;
        args.put("userId", "110265");
        command = new GetUserProfileCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        User myUser = (User) response.get("results");
        assertEquals("Expected matching first name", "Bebo" , myUser.getFirstName());
        assertEquals("Expected matching last name", "Abdelmalek" , myUser.getLastName());
        assertEquals("Expected matching headline", "Student" , myUser.getHeadline());
    }
}