import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.commands.impl.GetCompanyProfileCommand;
import com.linkedin.replica.editInfo.models.Company;
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
import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.commands.impl.GetUserProfileCommand;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.User;
public class getCompanyCommandTest {
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
        HashMap<String, Object> args = new HashMap();
       Object response;
        args.put("companyId", "1");
        command = new GetCompanyProfileCommand(args);
        command.setDbHandler(arangoHandler);
        response = command.execute();
        Company company = (Company) response;
        assertEquals("Expected matching company ID", "1" ,company.getCompanyID() );

    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfig("db.name");
        databaseSeed.deleteAllCompanies();
    }
}
