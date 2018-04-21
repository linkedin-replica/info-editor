package com.linkedin.replica.editInfo.commands.impl;

import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import java.util.UUID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AddCompanyCommand extends Command {

    public AddCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException {
        validateArgs(new String[]{"specialities", "posts", "jobListings","companyName", "companyProfilePicture", "adminUserName","adminUserName" , "adminUserName",
       "industryType", "companyLocation", "companyType" });
        String companyID = UUID.randomUUID().toString();
        System.out.println(companyID);
        // get notifications from db
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        ArrayList<String>specialities = new ArrayList<String>();
specialities.add((String) args.get("specilities"));

        ArrayList<String>posts = new ArrayList<String>();
        posts.add((String)args.get("posts"));
        ArrayList<String>jobLisitings = new ArrayList<String>();
        jobLisitings.add((String)args.get("jobListings"));
        dbHandler.insertCompany((String)args.get("companyName"),companyID,(String)args.get("companyProfilePicture"),(String)args.get("adminUserName"),(String)args.get("adminUserID")
        ,(String)args.get("industryType"),(String)args.get("companyLocation"),(String)args.get("companyType"),specialities,posts,jobLisitings);
        LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
        resutls.put("response",true);
        return "Company added successfully";
    }



}