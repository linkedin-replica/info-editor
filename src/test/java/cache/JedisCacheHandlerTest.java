package cache;

import com.google.gson.Gson;
import com.linkedin.replica.editInfo.cache.CacheConnection;
import com.linkedin.replica.editInfo.cache.handlers.CacheHandler;
import com.linkedin.replica.editInfo.cache.handlers.impl.JedisCacheHandler;
import com.linkedin.replica.editInfo.cache.handlers.impl.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.models.*;
import com.linkedin.replica.editInfo.models.User;
import com.linkedin.replica.editInfo.services.InfoEditorService;
import  com.linkedin.replica.editInfo.database.DatabaseSeed;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
        User user = new User("1","loay","elzobaidy","position","Software",new PersonalInfo(),"","","",new ArrayList<Position>(),new ArrayList<Education>(),"",
                "",new ArrayList<String>(),new ArrayList<FriendsList>(),new ArrayList<String>());
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        String [] ids = new String[1];
        ids[0]="1";
        editinfoHandler.saveUsersInCache(ids,users);
        User tempuser = (User)editinfoHandler.getUserFromCache("1",User.class);
        assertEquals("the two users must have the same id","1",user.getUserId());


    }
    @Test
    public void testgetUsersCache() throws Exception {

        User user = new User("1","loay","elzobaidy","position","Software",new PersonalInfo(),"","","",new ArrayList<Position>(),new ArrayList<Education>(),"",
                "",new ArrayList<String>(),new ArrayList<FriendsList>(),new ArrayList<String>());
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        String [] ids = new String[1];
        ids[0]="1";
        editinfoHandler.saveUsersInCache(ids,users);
        User tempuser = (User)editinfoHandler.getUserFromCache("1",User.class);
        assertEquals("the two users must have the same id","1",user.getUserId());
        assertEquals("the two users must have the same firstname","loay",user.getFirstName());
        assertEquals("the two users must have the same lastname","elzobaidy",user.getLastName());
        assertEquals("the two users must have the same headline","position",user.getHeadline());

    }
    @Test
    public void testeditUsersCache() throws Exception {

        User user = new User("1","loay","elzobaidy","position","Software",new PersonalInfo(),"","","",new ArrayList<Position>(),new ArrayList<Education>(),"",
                "",new ArrayList<String>(),new ArrayList<FriendsList>(),new ArrayList<String>());
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        String [] ids = new String[1];
        ids[0]="1";
//        editinfoHandler.saveUsersInCache(ids,users);
        LinkedHashMap<String,String> args= new LinkedHashMap<String,String>();
        String new_Position  = gson.toJson("");
        args.put("headline",new_Position);
        editinfoHandler.editUserIncache("1",args);
        System.out.println(args);
        User tempuser = (User)editinfoHandler.getUserFromCache("1",User.class);
        assertEquals("the user must has the new headline","new Position",user.getHeadline());

    }

//
//    @Test
//    public void testdeleteUsersCache() throws Exception {
//
//        User user = new User("1","loay","elzobaidy","position","Software",new PersonalInfo(),"","","",new ArrayList<Position>(),new ArrayList<Education>(),"",
//                "",new ArrayList<String>(),new ArrayList<FriendsList>(),new ArrayList<String>());
//        ArrayList<User> users = new ArrayList<User>();
//        users.add(user);
//        String [] ids = new String[1];
//        ids[0]="1";
//        editinfoHandler.saveUsersInCache(ids,users);
//        editinfoHandler.deleteUserFromCache("1");
//        User usertemp = (User)editinfoHandler.getUserFromCache("1",User.class);
//        assertEquals("the new user must be null",null,usertemp);
  //  }

    @AfterClass
    public static void teardown() throws IOException, SQLException, ClassNotFoundException {

        CacheConnection.getInstance().destroyRedisPool();
    }



}
