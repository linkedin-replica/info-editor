package com.linkedin.replica.editInfo.commands.impl;

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
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        String response = dbHandler.deleteSkill((String) args.get("userId"), (String)args.get("skill"));
        return response;

    }
}