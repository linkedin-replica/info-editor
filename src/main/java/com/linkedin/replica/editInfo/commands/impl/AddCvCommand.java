package com.linkedin.replica.editInfo.commands.impl;


import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class AddCvCommand extends Command{

    public AddCvCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() {
        validateArgs(new String[]{"userId", "cvUrl"});
        JsonObject request = (JsonObject)args.get("request");
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        dbHandler.addCV(request.get("userId").getAsString(),request.get("cvUrl").getAsString());
        return null;
    }


}
