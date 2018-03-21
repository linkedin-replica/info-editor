import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.commands.impl.GetCompanyProfileCommand;
import com.linkedin.replica.editInfo.commands.impl.UpdateCompanyCommand;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.Company;
import com.linkedin.replica.editInfo.models.User;
import com.arangodb.ArangoDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigReader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
public class UpdateCompanyCommandTest {
    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;
    private static DatabaseSeed databaseSeed;



    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException, SQLException, ClassNotFoundException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoEditInfoHandler();
        databaseSeed = new DatabaseSeed();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
        databaseSeed.insertCompanies();
    }


    @Test
    public void execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
