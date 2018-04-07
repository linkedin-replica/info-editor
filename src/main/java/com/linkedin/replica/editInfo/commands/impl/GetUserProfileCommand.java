package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.cache.handlers.EditInfoCacheHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetUserProfileCommand extends Command{
    private EditInfoCacheHandler cacheeditInfoHandler;

    public GetUserProfileCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute()  throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        String []ids=new String[1];
       ids[0]=(String) args.get("userId");
        cacheeditInfoHandler = (EditInfoCacheHandler)this.cacheHandler;
        User user = (User) cacheeditInfoHandler.getUserFromCache(ids[0],User.class);
        if(user!=null) {
            return user;

        }
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;

        validateArgs(new String[]{"userId"});
        // get notifications from db
         user = dbHandler.getUserProfile((String)args.get("userId"));
       ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        cacheeditInfoHandler.saveUsersInCache(ids,users);
        return user;
    }
}