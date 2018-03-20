import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import models.Company;
import org.junit.*;
import utils.ConfigReader;

import commands.GetCompanyProfileCommand;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class CompanyServiceTest {
    private static ArangoHandler arangoHandler;
        private static ArangoDatabase arangoDb;
        static ConfigReader config;
//    @BeforeClass
//    public static void init() throws IOException {
//        ConfigReader.isTesting = true;
//        config = ConfigReader.getInstance();
//        notificationService = new NotificationService();
//        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
//                ConfigReader.getInstance().getArangoConfig("db.name")
//        );
//    }

//    @Before
//    public void initBeforeTest() throws IOException {
//        arangoDb.createCollection(
//                config.getArangoConfig("collection.companies.name")
//        );
//    }


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
    public void execute()throws IOException {

        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;

        args.put("companyId", "1434");
        GetCompanyProfileCommand command = new GetCompanyProfileCommand(args);
        response = command.execute();
        System.out.println((Company) response.get("results"));

        assertEquals("Log in should succeed as sign in command was done by a verified user", ((Company) response.get("results")).getCompanyName(), "Microsoft2");

    }
//    @After
//    public void cleanAfterTest() throws IOException {
//        arangoDb.collection(
//                config.getArangoConfig("collection.companies.name")
//        ).drop();
//    }
//
//    @AfterClass
//    public static void clean() throws IOException {
//        ConfigReader.isTesting = false;
//        DatabaseConnection.getDBConnection().closeConnections();
//    }
}