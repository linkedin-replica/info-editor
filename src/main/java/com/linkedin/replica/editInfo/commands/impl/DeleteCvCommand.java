package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class DeleteCvCommand extends Command{

    public DeleteCvCommand(HashMap<String, Object> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
        validateArgs(new String[]{"userId"});
        // get notifications from db
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        validateArgs(new String[]{"userId"});
        dbHandler.deleteCV((String)args.get("userId"));
        LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
        resutls.put("response",true);
        return resutls;
    }

}