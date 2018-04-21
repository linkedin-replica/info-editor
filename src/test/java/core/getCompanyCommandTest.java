package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.cache.handlers.impl.JedisCacheHandler;
import com.linkedin.replica.editInfo.commands.impl.GetCompanyProfileCommand;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.models.Company;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.commands.Command;

public class getCompanyCommandTest {
    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static Configuration config;
    private static DatabaseSeed databaseSeed;
    private static JedisCacheHandler cacheEditInfoHandler;



    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException, SQLException, ClassNotFoundException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        databaseSeed = new DatabaseSeed();
    String JobID;
    String positionName ;
    String companyName ;
    String companyID;
    String companyProfilePictureURL ;
        cacheEditInfoHandler = new JedisCacheHandler();
        arangoHandler = new ArangoEditInfoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                config.getArangoConfigProp("db.name")
        );
//        databaseSeed.insertCompanies();
  //      databaseSeed.insertJobs();
//        databaseSeed.insertUsers();

    }


    @Test
    public void execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HashMap<String, Object> args = new HashMap();
       Object response;
        args.put("companyId", "12");
        command = new GetCompanyProfileCommand(args);
        command.setDbHandler(arangoHandler);
        command.setCacheHandler(cacheEditInfoHandler);
        response = command.execute();
        Company company = (Company) response;
        assertEquals("Expected matching company ID", "1" ,company.getCompanyID() );

    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");
        //databaseSeed.deleteAllCompanies();
    }
}
