import com.arangodb.ArangoDatabase;
import config.Configuration;
import database.DatabaseConnection;
import database.DatabaseSeed;
import models.Company;
import models.User;
import org.json.simple.parser.ParseException;
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
static DatabaseSeed databaseSeed;
    @BeforeClass
    public static void init() throws IOException, ParseException {
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
        databaseSeed = new DatabaseSeed();
        arangoDb.createCollection(
                config.getArangoConfig("collection.companies.name")
        );

    }

    @Before
    public void initBeforeTest() throws IOException, ParseException {
        databaseSeed.insertUsers();
    }

    @Test
    public void testinfoEditorServiceCompany() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        HashMap<String, String> args = new HashMap<String, String>();
        HashMap<String, String> args2 = new HashMap<String, String>();

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
        args.clear();
        args.put("companyId", "110265");


        LinkedHashMap<String, Object> resultGetCompany = infoEditorService.serve("company.get", args);
        Company company = (Company) resultGetCompany.get("results");

        assertEquals("The Two companies' UserNames should match", company.getCompanyName(), "MicrosoftUnique");
        System.out.println(company.getCompanyName()+"hereeeeee");

        args2.clear();

        args2.put("companyId", "110265");
        args2.put("companyName", "MicrosoftUnique2");
        args2.put("companyProfilePicture", "http://www.adsf221");

        LinkedHashMap<String, Object> resultUpdateCompany = infoEditorService.serve("company.update", args2);
        args2.clear();
        args2.put("companyId", "110265");


        LinkedHashMap<String, Object> resultGetCompany2 = infoEditorService.serve("company.get", args2);
        Company companyUpdate = (Company) resultGetCompany2.get("results");


        assertEquals("The Two companies' UserNames should match", companyUpdate.getCompanyName(), "MicrosoftUnique2");
        assertEquals("The Two companies' UserProfilePicture should match", companyUpdate.getCompanyProfilePicture(), "http://www.adsf221");


    }

    @Test
    public void testinfoEditorServiceUser() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        HashMap<String, String> args = new HashMap<String,String>();
        args.put("userId", "0");
        LinkedHashMap<String, Object> resultProfile = infoEditorService.serve("user.get", args);
        User user=(User)resultProfile.get("results");
        assertEquals("The user first names match" ,user.getFirstName(), "Omar");
        assertEquals("The user last names match" ,user.getLastName(), "Radwan");
        args.clear();
        args.put("userId", "0");
        args.put("Skill", "C++");
        resultProfile = infoEditorService.serve("user.add.skill", args);
        resultProfile = infoEditorService.serve("user.get", args);
        user=(User)resultProfile.get("results");
        assertEquals("The added skill matched" ,user.getSkills().get(4), "C++");
        args.clear();
        args.put("userId", "0");
        args.put("headline", "ACMER");
        resultProfile = infoEditorService.serve("user.update", args);
        resultProfile = infoEditorService.serve("user.get", args);
        user=(User)resultProfile.get("results");
        assertEquals("The headline is updated" , user.getHeadline(), "ACMER");





    }
    @After
    public void cleanAfterTest() throws IOException {
        databaseSeed.deleteAllUsers();


    }

    @AfterClass
    public static void clean() throws IOException {
        DatabaseConnection.getInstance().closeConnections();
        arangoDb.collection(
                config.getArangoConfig("collection.companies.name")
        ).drop();



    }
}
