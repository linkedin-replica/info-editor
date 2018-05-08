package com.linkedin.replica.editInfo.commands.impl;
import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class DeleteCvCommand extends Command{

    public DeleteCvCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() {
        validateArgs(new String[]{"userId"});
        JsonObject request = (JsonObject)args.get("request");
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        dbHandler.deleteCV(request.get("userId").getAsString());
        return null;
    }

}