package com.linkedin.replica.editInfo.commands.impl;

import com.google.gson.JsonObject;
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
        JsonObject request = (JsonObject)args.get("request");
        if(args.get("companyId")!=null)
        companyID = (String)args.get("companyId");
        else
       companyID = UUID.randomUUID().toString();
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        args.put("companyName",request.get("companyName").getAsString());
        args.put("industryType",request.get("industryType").getAsString());
        args.put("aboutus",request.get("aboutus").getAsString());
        args.put("companyProfilePicture",request.get("companyProfilePicture").getAsString());
        args.put("adminUserId",request.get("adminUserId").getAsString());

        ArrayList<String > postsArra = new ArrayList<String>();
        JsonObject posts = (JsonObject)request.get("posts");
        for(String key : posts.keySet())
            postsArra.add(posts.get(key).getAsString());


        ArrayList<String > skillsArra = new ArrayList<String>();
        JsonObject skills = (JsonObject)request.get("skills");
        for(String key : skills.keySet())
            skillsArra.add(skills.get(key).getAsString());



        args.put("posts",postsArra);
        args.put("skills",skillsArra);

        String response = dbHandler.insertCompany(args);
        
        return response;
    }


}