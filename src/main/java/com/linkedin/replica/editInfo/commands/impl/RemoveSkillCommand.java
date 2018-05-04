package com.linkedin.replica.editInfo.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.HashMap;

public class RemoveSkillCommand extends Command {

    public RemoveSkillCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute() throws IOException {
        validateArgs(new String[]{"userId", "skill"});
        JsonObject request = (JsonObject)args.get("request");
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        dbHandler.deleteSkill(request.get("userId").getAsString(), request.get("skill").getAsString());
        return null;
    }
}