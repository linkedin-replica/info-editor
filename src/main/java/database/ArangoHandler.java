package database;
import java.util.Date;
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
import models.lightJobListing;
import models.lightPost;
import models.lightUser;

public class ArangoHandler implements DatabaseHandler{
ArangoDB arangoDB;

    public void connect() {
        // TODO
        arangoDB = new ArangoDB.Builder().build();
    }

  
    public void disconnect() {
        // TODO
    }
    public Company getCompany(String companyID){
        Company company = arangoDB.db("Linked-in").collection("Companies").getDocument(companyID, Company.class);

        return company;
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
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(userID);
        myObject.addAttribute("cv",cv);
        try {
            arangoDB.db("Linked-in").collection("Users").updateDocument(userID,myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to Insert cv. " + e.getMessage());
        }

    }

    public void deleteCV(String userID){
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(userID);
        myObject.addAttribute("cv",null);
        try {
            arangoDB.db("Linked-in").collection("Users").updateDocument(userID,myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to delete cv. " + e.getMessage());
        }

    }
    public static void main(String [] srgs){

    }
}
