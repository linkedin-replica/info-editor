package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
//import models.testUser;
import org.junit.*;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.linkedin.replica.editInfo.models.*;


import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    private static DatabaseSeed databaseSeed;
    static Configuration config;

    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException, SQLException, ClassNotFoundException {
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

    }



    @Test
    public void testGetProfile() throws IOException {
        UserReturn myUser = arangoHandler.getUserProfile("6");
        assertEquals("Expected matching first name", "baher", myUser.getFirstName());
        assertEquals("Expected matching last name", "Abdou", myUser.getLastName());
        assertEquals("Expected matching headline", "ACMER", myUser.getHeadline());
//        assertEquals("Expected matching skills", "Ahmed", myUser.getFriendslist().get(0));
    }

    @Test
    public void testGetCompany() throws IOException {
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        CompanyReturn companytemp = arangoHandler.getCompany("12");
//        System.out.println(companytemp);

        assertEquals("name should be update",companytemp.getCompanyID(),"12");
//    }x`
    }


        @Test
        public void testDeleteCv() throws IOException {
            String collectionName = config.getArangoConfigProp("collection.users.name");
            arangoHandler.deleteCV(6+"");
            UserReturn user = arangoHandler.getUserProfile("6");
            assertEquals("the two cvs should matches",user.getCvUrl(),"");
        }





    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");

    }
//

}