package com.linkedin.replica.editInfo.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.cache.handlers.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.models.UserReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetUserProfileCommand extends Command {
    private CacheEditInfoHandler cacheEditInfoHandler;

    public GetUserProfileCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException {
        validateArgs(new String[]{"userId"});
       JsonObject request = (JsonObject)args.get("request");
        UserReturn user =null;
        if(user != null) {
            return user;
        }
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        if (args.containsKey("profileId"))
            user = dbHandler.getUserProfile(request.get("profileId").getAsString());
        else
            user = dbHandler.getUserProfile(request.get("userId").getAsString());
        return user;
    }
}