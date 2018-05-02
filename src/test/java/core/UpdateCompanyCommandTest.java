package core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.editInfo.cache.handlers.impl.JedisCacheHandler;
import com.linkedin.replica.editInfo.commands.impl.GetCompanyProfileCommand;
import com.linkedin.replica.editInfo.commands.impl.UpdateCompanyCommand;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.models.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
public class UpdateCompanyCommandTest {
    private static Command command;
    private static ArangoEditInfoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static Configuration config;
    private static JedisCacheHandler jedisCacheHandler;
    private static DatabaseSeed databaseSeed;



    @BeforeClass
    public static void init() throws IOException, org.json.simple.parser.ParseException, SQLException, ClassNotFoundException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        arangoHandler = new ArangoEditInfoHandler();
        jedisCacheHandler = new JedisCacheHandler();
        databaseSeed = new DatabaseSeed();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
               config.getArangoConfigProp("db.name")
        );
       // databaseSeed.insertCompanies();
    }


    @Test
    public void execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HashMap<String, Object> args = new HashMap();
        HashMap<String, Object> argstemp = new HashMap();

        Object response;
        args.put("companyId", "13");
        argstemp.put("companyId", "13");
        argstemp.put("companyName", "Microsoft28");
        ArrayList<String>  joblistings =  new ArrayList<String>();
        joblistings.add("2");

        argstemp.put("posts",joblistings);

        command = new GetCompanyProfileCommand(args);
        Command temp = new UpdateCompanyCommand(argstemp);
        temp.setCacheHandler(jedisCacheHandler);
        command.setCacheHandler(jedisCacheHandler);
        command.setDbHandler(arangoHandler);
        temp.setDbHandler(arangoHandler);
        temp.execute();
        response = command.execute();
        CompanyReturn company = (CompanyReturn) response;
        assertEquals("Expected matching new company name", "Microsoft28" ,company.getCompanyName() );

    }
    @AfterClass
    public static void teardown() throws IOException {
        String dbName = config.getArangoConfigProp("db.name");
      //  databaseSeed.deleteAllCompanies();
    }
}
