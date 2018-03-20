import com.arangodb.ArangoDatabase;
import config.Configuration;
import database.DatabaseConnection;
import models.Company;
import org.junit.*;
import services.*;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
        infoEditorService = new InfoEditorService();
        arangoDb = DatabaseConnection.getInstance().getArangoDriver().db(
                Configuration.getInstance().getArangoConfig("db.name")
        );
    }

    @Before
    public void initBeforeTest() throws IOException {
        arangoDb.createCollection(
                config.getArangoConfig("collection.companies.name")
        );
    }

    @Test
    public void testinfoEditorService() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        HashMap<String, String> args = new HashMap<String,String>();
        args.put("companyId", "1234");

        args.clear();


        args.put("companyId", "110265");
        args.put("companyName", "MicrosoftUnique");
        args.put("companyProfilePicture", "http://www.");
        args.put("adminUserName", "baher");
        args.put("adminUserID", "11025");
        args.put("industryType", "Software");
        args.put("companyLocation", "Beirut");
        args.put("companyType", "Startup");
        args.put("posts", "");
        args.put("specialities", "");
        args.put("jobListings", "");

        LinkedHashMap<String, Object> resultAddCompany = infoEditorService.serve("company.add", args);

        args.put("companyId", "110265");


        LinkedHashMap<String, Object> resultGetCompany = infoEditorService.serve("company.get", args);
      Company company=(Company)resultGetCompany.get("results");

        assertEquals("The Two companies' UserNames should match" ,company.getCompanyName(), "MicrosoftUnique");


        args.clear();


        args.put("companyId", "110265");
        args.put("companyName", "MicrosoftUnique2");
        args.put("companyProfilePicture", "http://www.adsf221");

        LinkedHashMap<String, Object> resultUpdateCompany = infoEditorService.serve("company.update", args);

        args.put("companyId", "110265");


        LinkedHashMap<String, Object> resultGetCompany2 = infoEditorService.serve("company.get", args);
        Company companyUpdate=(Company)resultGetCompany2.get("results");

        assertEquals("The Two companies' UserNames should match" ,companyUpdate.getCompanyName(), "MicrosoftUnique2");
        assertEquals("The Two companies' UserProfilePicture should match" ,companyUpdate.getCompanyProfilePicture(), "http://www.adsf221");




//
//
//        assertEquals("Expected 2 notifications" , 2, all.size());
//        assertEquals("Expected 1 unread notification", 1, unread.size());
    }

    @After
    public void cleanAfterTest() throws IOException {
        arangoDb.collection(
                config.getArangoConfig("collection.companies.name")
        ).drop();
    }

    @AfterClass
    public static void clean() throws IOException {
        DatabaseConnection.getInstance().closeConnections();
    }
}
