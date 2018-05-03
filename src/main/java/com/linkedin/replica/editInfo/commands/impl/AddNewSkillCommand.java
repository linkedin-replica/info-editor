package com.linkedin.replica.editInfo.commands.impl;

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
        String response = dbHandler.addSkill((String) args.get("userId"), (String) args.get("skill"));
        return response;
    }
}