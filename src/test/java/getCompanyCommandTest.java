import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import database.DatabaseSeed;
import commands.GetCompanyProfileCommand;
import models.Command;
import models.Company;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class getCompanyCommandTest {
    private static Command command;
    private static ArangoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;
    private static DatabaseSeed databaseSeed;



    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException, SQLException, ClassNotFoundException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoHandler();
        databaseSeed = new DatabaseSeed();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
        databaseSeed.insertCompanies();
    }


    @Test
    public void execute() throws IOException {
        HashMap<String, String> args = new HashMap();
        LinkedHashMap<String, Object> response;
        args.put("companyId", "1");
        command = new GetCompanyProfileCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        Company company = (Company) response.get("results");
        assertEquals("Expected matching company ID", "1" ,company.getCompanyID() );

    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllCompanies();
    }
}
