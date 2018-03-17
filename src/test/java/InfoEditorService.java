import Database.ArangoHandler;
import database.DatabaseConnection;
import models.Company;
import org.junit.*;
import services.InfoEditorService;
import config.Configuration;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import database.DatabaseSeed;
import static org.junit.Assert.assertEquals;

public class InfoEditorServiceTest {
    private static InfoEditorService infoEditorService;
    private static ArangoDatabase arangoDb;
    static Configuration config;

    @BeforeClass
    public static void init() throws IOException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        notificationService = new NotificationService();
        arangoDb = DatabaseConnection.getInstance().getArangoDriver().db(
                Configuration.getInstance().getArangoConfig("db.name")
        );

    }

    @Before
    public void initBeforeTest() throws IOException {
        arangoDb.createCollection(
                config.getArangoConfig("collection.companies.name")
        );
        arangoDb.createCollection(
                config.getArangoConfig("collection.users.name")
        );
        DatabaseSeed databaseSeed = new DatabaseSeed();
        databaseSeed.insertUsers();
        databaseSeed.insertCompanies();
    }

    @Test
    public void testNotificationService() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        HashMap<String, String> args = new HashMap<>();

        args.put("");

    }

    @After
    public void cleanAfterTest() throws IOException {
        arangoDb.collection(
                config.getArangoConfig("collection.companies.name")
        ).drop();
        arangoDb.collection(
                config.getArangoConfig("collection.users.name")
        ).drop();
    }

    @AfterClass
    public static void clean() throws IOException {
        DatabaseConnection.getInstance().closeConnections();
    }
}