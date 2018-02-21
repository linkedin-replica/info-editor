package database;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import models.User;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
//import com.arangodb.ArangoDB;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;
public class ArangoHandler implements DatabaseHandler{
ArangoDB arangoDB;

    public void connect() {
        // TODO
        arangoDB = new ArangoDB.Builder().build();
    }

  
    public void disconnect() {
        // TODO
    }
    public User getUser(){
        User user = arangoDB.db("myDatabase").collection("myCollection").getDocument("", User.class);
        return user;
    }
    public static void main(String [] srgs){

    }
}
