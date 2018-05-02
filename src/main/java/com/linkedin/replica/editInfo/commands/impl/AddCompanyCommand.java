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
        validateArgs(new String[]{"posts","companyName","companyId"
                , "companyProfilePicture", "adminUserId",
                "industryType", "aboutus"});
        String companyID;
        if(args.get("companyId")!=null)
        companyID = (String)args.get("companyId");
        else
       companyID = UUID.randomUUID().toString();
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;

        ArrayList<String> posts = (ArrayList<String>) args.get("posts");

        String response = dbHandler.insertCompany(args);
        
        return response;
    }


}