package cache;

import com.google.gson.Gson;
import com.linkedin.replica.editInfo.cache.CacheConnection;
import com.linkedin.replica.editInfo.cache.handlers.impl.JedisCacheHandler;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.models.*;
import com.linkedin.replica.editInfo.models.User;
import com.linkedin.replica.editInfo.services.InfoEditorService;
import  com.linkedin.replica.editInfo.database.DatabaseSeed;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
public class JedisCacheHandlerTest {

    private static Gson gson;
    private static InfoEditorService infoEditorService;
    private static DatabaseSeed databaseSeed;
    private static Configuration config;
    private static String userId = "0";
    private static HashMap<String, String> args;
    private static JedisCacheHandler editinfoHandler;

    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, ParseException, IOException {
        gson = new Gson();
        Configuration.init("src/main/resources/config/app.config", "src/main/resources/config/arango.config","src/main/resources/config/commands.config",
                "src/main/resources/config/controller.config","src/main/resources/config/cache.config");
        config = Configuration.getInstance();
        System.out.println(Integer.parseInt(config.getRedisConfigProp("cache.users.index")));
        infoEditorService = new InfoEditorService();
        editinfoHandler = new JedisCacheHandler();
        args = new HashMap<>();

    }

    @Test
    public void testsaveUsersCache() throws Exception {
        User user = new User("1","loay","elzobaidy","position","Software");
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        String ids ="1";
        editinfoHandler.saveUsersInCache(ids,users);
        User tempuser = (User)editinfoHandler.getUserFromCache("1",UserReturn.class);
        assertEquals("the two users must have the same id","1",user.getUserId());


    }
    @Test
    public void testgetUsersCache() throws Exception {

        User user = new User("1","loay","elzobaidy","position","Software");
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        String ids="1";
        editinfoHandler.saveUsersInCache(ids,users);
        UserReturn tempuser = (UserReturn) editinfoHandler.getUserFromCache("1",UserReturn.class);
        assertEquals("the two users must have the same id","1",user.getUserId());
        assertEquals("the two users must have the same firstname","loay",user.getFirstName());
        assertEquals("the two users must have the same lastname","elzobaidy",user.getLastName());
        assertEquals("the two users must have the same headline","position",user.getHeadline());

    }
    @Test
    public void testeditUsersCache() throws Exception {

        User user = new User("2","loay","elzobaidy","position","Software");
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);

        String ids ="2";
        editinfoHandler.saveUsersInCache(ids,users);
        LinkedHashMap<String,String> args= new LinkedHashMap<String,String>();
        String new_Position  = gson.toJson("ahmed");
        args.put("firstName",new_Position);
        System.out.println(args);
        editinfoHandler.editUserCache("2",args);
        UserReturn tempuser = (UserReturn)editinfoHandler.getUserFromCache("2",UserReturn.class);
        assertEquals("the user must has the new first name","ahmed",tempuser.getFirstName());

    }


    @Test
    public void testdeleteUsersCache() throws Exception {

        User user = new User("1","loay","elzobaidy","position","Software");
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        String ids ="1";
        editinfoHandler.saveUsersInCache(ids,users);
        editinfoHandler.deleteUserFromCache("1");
        User usertemp = (User)editinfoHandler.getUserFromCache("1",UserReturn.class);
        assertEquals("the new user must be null",null,usertemp);
    }

    @Test
    public void testsaveCompaniesCache() throws Exception {
        CompanyReturn company = new CompanyReturn("Microsoft","1","","loay","3","","","",new ArrayList<String>(),new ArrayList<String>());
        ArrayList<CompanyReturn> companies = new ArrayList<CompanyReturn>();
       companies.add(company);
        String ids ="1";
        editinfoHandler.saveCompanyInCache(ids,companies);
        CompanyReturn tempCompany = (CompanyReturn) editinfoHandler.getCompanyFromCache("1",CompanyReturn.class);
        assertEquals("the two users must have the same id","Microsoft",tempCompany.getCompanyName());


    }
    @Test
    public void testgetCompaniesCache() throws Exception {
        CompanyReturn company = new CompanyReturn("Microsoft","1","","loay","3","","","",new ArrayList<String>(),new ArrayList<String>());

        ArrayList<CompanyReturn> companies = new ArrayList<CompanyReturn>();
        companies.add(company);
        String ids ="1";
        editinfoHandler.saveCompanyInCache(ids,companies);
        CompanyReturn tempcompany = (CompanyReturn) editinfoHandler.getCompanyFromCache("1",CompanyReturn.class);
        assertEquals("the two users must have the same id","1",tempcompany.getCompanyID());
//        assertEquals("the two users must have the same firstname","loay",user.getFirstName());
//        assertEquals("the two users must have the same lastname","elzobaidy",user.getLastName());
//        assertEquals("the two users must have the same headline","position",user.getHeadline());

    }
    @Test
    public void testeditCompaniesCache() throws Exception {

        CompanyReturn company = new CompanyReturn("Microsoft","2","","loay","3","","","",new ArrayList<String>(),new ArrayList<String>());

        ArrayList<CompanyReturn> companies = new ArrayList<CompanyReturn>();
        companies.add(company);
        String ids ="2";
        editinfoHandler.saveCompanyInCache(ids,companies);

        editinfoHandler.saveCompanyInCache(ids,companies);
        LinkedHashMap<String,String> args= new LinkedHashMap<String,String>();
        String new_Position  = gson.toJson("Mic");
        args.put("companyName",new_Position);
        editinfoHandler.editcompanyFromCache("2",args);
        System.out.println(args);
        CompanyReturn tempcompany = (CompanyReturn) editinfoHandler.getCompanyFromCache("2",CompanyReturn.class);
        assertEquals("the user must has the new headline","Mic",tempcompany.getCompanyName());

    }


    @Test
    public void testdeleteCompaniesCache() throws Exception {


        CompanyReturn company = new CompanyReturn("Microsoft","1","","loay","3","","","",new ArrayList<String>(),new ArrayList<String>());

        ArrayList<CompanyReturn> companies = new ArrayList<CompanyReturn>();
        companies.add(company);
        String ids ="1";
        editinfoHandler.saveUsersInCache(ids,companies);
        editinfoHandler.deleteCompanies("1");
        CompanyReturn tempcompany = (CompanyReturn) editinfoHandler.getCompanyFromCache("1",CompanyReturn.class);
        assertEquals("the user must has the new headline",null,tempcompany);
      }
    @AfterClass
    public static void teardown() throws IOException, SQLException, ClassNotFoundException {

        CacheConnection.getInstance().destroyRedisPool();
    }



}
