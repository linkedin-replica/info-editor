package commands;

import database.DatabaseHandler;
import models.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AddCompanyCommand extends Command {

    public AddCompanyCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
        validateArgs(new String[]{"companyId"});
        // get notifications from db
        DatabaseHandler dbHandler = (DatabaseHandler) this.dbHandler;
        ArrayList<String>specialities = new ArrayList<String>();
specialities.add(args.get("specilities"));

        ArrayList<String>posts = new ArrayList<String>();
        posts.add(args.get("posts"));
        ArrayList<String>jobLisitings = new ArrayList<String>();
        jobLisitings.add(args.get("jobListings"));
        dbHandler.insertCompany(args.get("companyName"),args.get("companyId"),args.get("companyProfilePicture"),args.get("adminUserName"),args.get("adminUserID")
        ,args.get("industryType"),args.get("companyLocation"),args.get("companyType"),specialities,posts,jobLisitings);
        LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
        resutls.put("response",true);
        return resutls;
    }



}