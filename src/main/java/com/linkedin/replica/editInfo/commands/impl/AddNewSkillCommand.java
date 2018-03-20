package com.linkedin.replica.editInfo.commands.impl;

import com.linkedin.replica.editInfo.database.handlers.DatabaseHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


 public class AddNewSkillCommand extends Command{

    public AddNewSkillCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        validateArgs(new String[]{"userId"});
        // get notifications from db
        dbHandler.addSkill(args.get("userId"),args.get("Skill"));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        return result;
    }
}