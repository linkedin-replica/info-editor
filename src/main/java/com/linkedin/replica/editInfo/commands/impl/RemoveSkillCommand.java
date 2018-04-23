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
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId", "Skill"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        validateArgs(new String[]{"userId"});
        // get notifications from db
        dbHandler.deleteSkill((String) args.get("userId"),(String)args.get("Skill"));
        return "Skill removed successfully";

    }
}