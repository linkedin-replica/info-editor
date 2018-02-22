package database;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import models.Company;
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
    public Company getCompany(){
        Company company = arangoDB.db("Linked-in").collection("Companies").getDocument("", Company.class);
        return company;
    }
    public static void main(String [] srgs){

    }
}
