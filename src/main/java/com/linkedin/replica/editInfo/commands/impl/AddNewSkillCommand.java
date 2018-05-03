package com.linkedin.replica.editInfo.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.database.handlers.DatabaseHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


 public class AddNewSkillCommand extends Command{

    public AddNewSkillCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() {
        validateArgs(new String[]{"userId", "skill"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
     JsonObject request = (JsonObject)args.get("request");
        String response = dbHandler.addSkill(request.get("userId").getAsString(), request.get("skill").getAsString());
        return response;

    }
}