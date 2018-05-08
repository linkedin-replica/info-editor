package com.linkedin.replica.editInfo.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.cache.handlers.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UpdateCompanyCommand extends Command{

    public UpdateCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute()  throws IOException {
        validateArgs(new String[]{"companyId"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        LinkedHashMap<String, String> cacheargs = new LinkedHashMap<>();
        JsonObject request = (JsonObject)args.get("request");
        dbHandler.updateCompany(request);
        return null;
    }
}