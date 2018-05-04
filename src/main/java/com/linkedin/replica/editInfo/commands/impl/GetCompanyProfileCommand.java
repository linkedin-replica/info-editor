package com.linkedin.replica.editInfo.commands.impl;
import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.cache.handlers.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.commands.Command;
import java.io.IOException;
import java.util.HashMap;

import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.models.*;

public class GetCompanyProfileCommand extends Command{

    public GetCompanyProfileCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException {
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        validateArgs(new String[]{"companyId"});
        JsonObject request = (JsonObject)args.get("request");
        CompanyReturn company = dbHandler.getCompany(request.get("companyId").getAsString());
        return company;
    }
}