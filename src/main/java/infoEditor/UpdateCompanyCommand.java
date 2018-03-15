package  infoEditor;
import com.sun.corba.se.impl.logging.InterceptorsSystemException;
import database.ArangoHandler;
import database.DatabaseHandler;
import  models.Command;
import models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UpdateCompanyCommand extends Command{

    public UpdateCompanyCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute()  throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"companyId"});
        DatabaseHandler dbHandler = (DatabaseHandler) this.dbHandler;
        // get notifications from db
        ArrayList<String >posts = new ArrayList<String>();
        ArrayList<String >jobListings = new ArrayList<String>();
        ArrayList<String> specialities = new ArrayList<String>();
        specialities.add(args.get("specialities"));
        jobListings.add(args.get("jobListings"));
        posts.add(args.get("posts"));
        dbHandler.updateCompany(args.get("companyName"),args.get("companyId"),args.get("companyProfilePicture"),args.get("adminUserName"),args.get("adminUserID"),args.get("industryType"),args.get("companyLocation") ,args.get("companytype"),specialities,posts,jobListings);
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("results",true);
        return result;
    }
}