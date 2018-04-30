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
        validateArgs(new String[]{"posts","companyName"
                , "profilePictureUrl", "adminUserId",
                "industryType", "aboutus"});

        String companyID = UUID.randomUUID().toString();
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;

        ArrayList<String> posts = (ArrayList<String>) args.get("posts");

        String response = dbHandler.insertCompany((String)args.get("companyName"), companyID, (String)args.get("companyProfilePicture"),
                (String)args.get("adminUserId"), (String)args.get("industryType"),
                posts);
        
        return response;
    }


}