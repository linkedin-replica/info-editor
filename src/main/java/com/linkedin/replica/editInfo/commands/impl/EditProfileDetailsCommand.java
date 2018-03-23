package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class EditProfileDetailsCommand extends Command{

    public EditProfileDetailsCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute()  throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        validateArgs(new String[]{"userId"});
        // get notifications from db
        dbHandler.updateProfile (args, (String)args.get("userId"));

       return "Profile Edited successfully";
    }
}