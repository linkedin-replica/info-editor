package database;
import java.io.IOException;
import java.util.*;

import utils.ConfigReader;
import com.arangodb.ArangoDatabase;
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
import models.lightJobListing;
import models.lightPost;
import models.lightUser;

public class ArangoHandler implements DatabaseHandler{
ArangoDB arangoDB;
    private ConfigReader config;
    private ArangoDatabase dbInstance;
    private ArangoCollection collection;
    private String collectionName;

    public void connect() {
        // TODO
        arangoDB = new ArangoDB.Builder().build();
    }

  
    public void disconnect() {
        // TODO
    }
    public Company getCompany(String companyID){
        String query = "For t in " + collectionName + " FILTER t.companyId == @companyId RETURN t";
        Map<String, Object> bindVars = new HashMap<String, Object>();
        bindVars.put("companyId", companyID);

        ArangoCursor<Company> cursor = dbInstance.query(query, bindVars, null, Company.class);



        return cursor.next();
    }
    public void updateCompany(String companyName,int companyID,String companyProfilePicture,String adminUserName,int adminUserID,
            int adminUserIDMongo, String industryType,String companyLocation,lightUser[] relatedConnections,String aboutUs, String website,Date yearFounded
            ,String[] headquarters,String companytype,int companySize,String[] specialities,lightPost[] posts,lightJobListing[] jobListings){
        BaseDocument myObject = new BaseDocument();
        if(companyName!=null)
        myObject.addAttribute("companyName", companyName);
        if(companyProfilePicture!=null)
            myObject.addAttribute("companyProfilePicture", companyProfilePicture);
        if(companyLocation!=null)
            myObject.addAttribute("companyLocation", companyLocation);
        if(companySize==-1)
            myObject.addAttribute("companySize", companySize);
        if(companytype==null)
            myObject.addAttribute("companyType", companytype);
        if(headquarters==null)
            myObject.addAttribute("headquarters",headquarters);
        if(website!=null)
            myObject.addAttribute("website", website);
        if(aboutUs!=null)
            myObject.addAttribute("aboutUS", aboutUs);
        if(industryType!=null)
            myObject.addAttribute("industryType", industryType);
        if(yearFounded!=null)
            myObject.addAttribute("yeadFounded",yearFounded);
        try {
            arangoDB.db("Linked-in").collection("Companies").updateDocument(companyID+"", myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }
    public void addCV(String userID,String cv){
        String query = "FOR u IN users\n "+"FILTER u.userId ==  @userID"+"UPDATE u WITH{ Cv:@cv} IN users";
        Map<String, Object> bindVars = new HashMap<String, Object>();
        bindVars.put("userId", userID);
        bindVars.put("cv",cv);

        dbInstance.query(query, bindVars, null, Company.class);

    }

    public void deleteCV(String userID){
        String nil= "";
            String query = "FOR u IN users\n "+"FILTER u.userId ==  @userID"+"UPDATE u WITH{ Cv:@nil} IN users";
            Map<String, Object> bindVars = new HashMap<String, Object>();
            bindVars.put("userId", userID);
            bindVars.put("nil",nil);

           dbInstance.query(query, bindVars, null, Company.class);




    }
    public ArangoHandler()throws IOException {

        config = new ConfigReader("arango_names");

        // init db
        ArangoDB arangoDriver = DatabaseConnection.getDBConnection().getArangoDriver();
        collectionName = config.getConfig("collection.notifications.name");
        dbInstance = arangoDriver.db(config.getConfig("db.name"));
        collection = dbInstance.collection(collectionName);
    }
    public static void main(String [] srgs){

    }
}

