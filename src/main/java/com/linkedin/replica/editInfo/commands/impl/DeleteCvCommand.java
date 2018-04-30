package com.linkedin.replica.editInfo.commands.impl;
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
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        String response = dbHandler.deleteCV((String)args.get("userId"));
        return response;
    }

}