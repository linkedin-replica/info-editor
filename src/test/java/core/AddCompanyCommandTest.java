package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.commands.impl.AddCompanyCommand;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.Company;
import com.linkedin.replica.editInfo.models.CompanyReturn;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class AddCompanyCommandTest {

    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static Configuration config;



    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException, SQLException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        DatabaseConnection.init();
        config = Configuration.getInstance();
        arangoHandler = new ArangoEditInfoHandler();



    }
    @Before
    public void initBeforeTest() throws IOException {
//        arangoDb.createCollection(
//                config.getArangoConfigProp("collection.companies.name")
//        );
    }


    @Test
    public void execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        HashMap<String, Object> args = new HashMap();
        LinkedHashMap<String, Object> response;

        args.put("companyId",   "f8312bc1-a02f-4d39-8373-5e3d03fcdba4");
        args.put("companyName", "MicrosoftUnique");
        args.put("companyProfilePicture", "http://www.");
        args.put("adminUserName", "baher");
        args.put("adminUserId", "11025");
        args.put("industryType", "Software");
        args.put("companyLocation", "Beirut");
        args.put("companyType", "Startup");
        args.put("posts", new ArrayList<String>());
        args.put("specialities", "");
        args.put("jobListings", "");
        args.put("aboutus", " ");

        command = new AddCompanyCommand(args);
        command.setDbHandler(arangoHandler);
        command.execute();


        CompanyReturn company = arangoHandler.getCompany("f8312bc1-a02f-4d39-8373-5e3d03fcdba4");
        assertEquals("Expected company Name", "MicrosoftUnique",company.getCompanyName());
        assertEquals("Expected Profile picture", "http://www." , company.getCompanyProfilePicture());
        assertEquals("Expected Admin user name", "baher",company.getAdminUserName() );
        assertEquals("Expected Industry type", "Software" , company.getIndustryType());
    }

    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");
    }
}