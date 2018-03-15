import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import database.DatabaseSeed;
import infoEditor.GetCompanyProfileCommand;
import infoEditor.GetUserProfileCommand;
import models.Command;
import models.Company;
import models.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigReader;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import infoEditor.UpdateCompanyCommand;
public class UpdateCompanyCommandTest {
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
        HashMap<String, String> argstemp = new HashMap();

        LinkedHashMap<String, Object> response;
        args.put("companyId", "1");
        argstemp.put("companyId", "1");
        argstemp.put("companyName", "Microsoft2");

        command = new GetCompanyProfileCommand(args);
        Command temp = new UpdateCompanyCommand(argstemp);
        command.setDbHandler(arangoHandler);
        temp.setDbHandler(arangoHandler);
        temp.execute();
        response = command.execute();
        Company company = (Company) response.get("results");
        assertEquals("Expected matching company ID", "Microsoft2" ,company.getCompanyName() );

    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllCompanies();
    }
}
