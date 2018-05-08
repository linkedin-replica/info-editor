
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
        dbHandler.createProfile(request);
        return null;
    }
}