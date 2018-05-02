package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.models.Company;
import com.linkedin.replica.editInfo.models.CompanyReturn;
import com.linkedin.replica.editInfo.models.User;
import com.linkedin.replica.editInfo.models.UserReturn;
import org.json.simple.parser.ParseException;
import org.junit.*;
import com.linkedin.replica.editInfo.services.*;

import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        infoEditorService = new InfoEditorService();
        arangoDb = DatabaseConnection.getInstance().getArangoDriver().db(
                Configuration.getInstance().getArangoConfigProp("db.name")
        );
        databaseSeed = new DatabaseSeed();


    }

    @Before
    public void initBeforeTest() throws IOException, ParseException {
       // databaseSeed.insertUsers();
    }

    @Test
    public void testinfoEditorServiceCompany() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        HashMap<String, Object> args = new HashMap<String, Object>();
        HashMap<String, Object> args2 = new HashMap<String,Object>();

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

     Object resultAddCompany = infoEditorService.serve("company.add", args);
        args.clear();
        args.put("companyId", "12");


        Object resultGetCompany = infoEditorService.serve("company.get", args);
        CompanyReturn company = (CompanyReturn) resultGetCompany;

        assertEquals("The Two companies' UserNames should match", company.companyName, "microsoft3");

        args2.clear();

        args2.put("companyId", "12");
        args2.put("companyName", "microsoft3");
        args2.put("profilePictureUrl", "http://www.adsf221");

       Object resultUpdateCompany = infoEditorService.serve("company.update", args2);
        args2.clear();
        args2.put("companyId", "12");


        Object resultGetCompany2 = infoEditorService.serve("company.get", args2);
        CompanyReturn companyUpdate = (CompanyReturn) resultGetCompany2;


        assertEquals("The Two companies' UserNames should match", companyUpdate.companyName, "microsoft3");
        assertEquals("The Two companies' UserProfilePicture should match", companyUpdate.profilePictureUrl, "http://www.adsf221");


    }

    @Test
    public void testinfoEditorServiceUser() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        HashMap<String, Object> args = new HashMap<String,Object>();
        args.put("userId", "6");
       Object resultProfile = infoEditorService.serve("user.get", args);
       UserReturn user=(UserReturn) resultProfile;
        assertEquals("The user first names match" ,user.firstName, "baher");
        assertEquals("The user last names match" ,user.lastName, "Abdou");
        args.clear();
        args.put("userId", "6");
        args.put("Skill", "C++");
        resultProfile = infoEditorService.serve("user.add.skill", args);
        resultProfile = infoEditorService.serve("user.get", args);
        user = (UserReturn) resultProfile;
        assertEquals("The added skill matched" ,user.skills.get(0), "C++");
        args.clear();
        args.put("userId", "6");
        args.put("headline", "ACMER");
        resultProfile = infoEditorService.serve("user.update", args);
        resultProfile = infoEditorService.serve("user.get", args);
        user=(UserReturn) resultProfile;
        assertEquals("The headline is updated" , user.headline, "ACMER");





    }
    @After
    public void cleanAfterTest() throws IOException {
        //databaseSeed.deleteAllUsers();


    }

    @AfterClass
    public static void clean() throws IOException {
        DatabaseConnection.getInstance().closeConnections();




    }
}
