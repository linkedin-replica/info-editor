
package com.linkedin.replica.editInfo.commands.impl;
import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class CreateProfileCommand extends Command {

    public CreateProfileCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() {
        validateArgs(new String[]{"userId"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        JsonObject request = (JsonObject)args.get("request");
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
        String response = dbHandler.createProfile(args,(String)args.get("userId"));
        return response;
    }
}