package com.linkedin.replica.editInfo.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import java.util.UUID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AddCompanyCommand extends Command {

    public AddCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException {
        validateArgs(new String[]{"userId"});
        JsonObject request = (JsonObject)args.get("request");
        ((EditInfoHandler)dbHandler).insertCompany(request);
        return null;
    }


}