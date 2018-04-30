package com.linkedin.replica.editInfo.commands.impl;


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

        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        String response = dbHandler.addCV((String)args.get("userId"),(String)args.get("cvUrl"));
        return response;
    }


}
