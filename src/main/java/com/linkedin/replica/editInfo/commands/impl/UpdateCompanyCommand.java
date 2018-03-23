package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class UpdateCompanyCommand extends Command{

    public UpdateCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute()  throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"companyId"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        // get notifications from db
        ArrayList<String >posts = new ArrayList<String>();
        ArrayList<String >jobListings = new ArrayList<String>();
        ArrayList<String> specialities = new ArrayList<String>();
        specialities.add((String)args.get("specialities"));
        jobListings.add((String)args.get("jobListings"));
        posts.add((String)args.get("posts"));
        dbHandler.updateCompany((String)args.get("companyName"),(String)args.get("companyId"),(String)args.get("companyProfilePicture"),(String)args.get("adminUserName"),(String)args.get("adminUserID"),(String)args.get("industryType"),(String)args.get("companyLocation") ,(String)args.get("companytype"),specialities,posts,jobListings);

        return "Company updated successfully";
    }
}