import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.commands.impl.AddCompanyCommand;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.commands.*;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.Company;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class AddCompanyCommandTest {

    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;
    private static DatabaseSeed databaseSeed;



    @BeforeClass
    public static void init() throws IOException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoEditInfoHandler();
        databaseSeed = new DatabaseSeed();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );

    }
    @Before
    public void initBeforeTest() throws IOException {
        arangoDb.createCollection(
                config.getArangoConfig("collection.companies.name")
        );
    }


    @Test
    public void execute() throws IOException {
        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;
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

        command = new AddCompanyCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();


        Company company = arangoHandler.getCompany("110265");
        System.out.println("");
        assertEquals("Expected skillsNumber", company.getCompanyName() ,"MicrosoftUnique");
//        assertEquals("Expected LastSkill", "Java" , myUser.getSkills().get(5));
    }

    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllCompanies();
    }
}