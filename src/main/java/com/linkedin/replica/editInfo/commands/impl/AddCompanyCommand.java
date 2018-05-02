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

        String companyId = UUID.randomUUID().toString();
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;


        String response = dbHandler.insertCompany((String)args.get("companyName"), companyId, (String)args.get("companyProfilePicture"), (String)args.get("userId"), (String)args.get("industryType"));
        
        return response;
    }


}