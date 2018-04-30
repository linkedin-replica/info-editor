package com.linkedin.replica.editInfo.commands.impl;

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
        String userId = (String) args.get("userId");
        cacheEditInfoHandler = (CacheEditInfoHandler) this.cacheHandler;
        UserReturn user = (UserReturn) cacheEditInfoHandler.getUserFromCache(userId, UserReturn.class);
        if(user != null) {
            return user;
        }
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;

        user = dbHandler.getUserProfile((String) args.get("userId"));

        cacheEditInfoHandler.saveUsersInCache(userId, user);
        return user;
    }
}