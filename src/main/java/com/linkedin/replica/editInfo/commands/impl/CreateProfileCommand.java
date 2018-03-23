
package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class CreateProfileCommand extends Command {

    public CreateProfileCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute()  throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        dbHandler.createProfile(args,(String)args.get("userId"));
        return "Profile created successfully";
    }
}