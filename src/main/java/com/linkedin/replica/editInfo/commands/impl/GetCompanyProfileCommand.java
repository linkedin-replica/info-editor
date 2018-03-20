package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.commands.Command;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.models.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GetCompanyProfileCommand extends Command{

    public GetCompanyProfileCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() throws IOException {
        // validate that all required arguments are passed
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        validateArgs(new String[]{"companyId"});
        // get notifications from db
        Company companies = dbHandler.getCompany(args.get("companyId"));

        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        System.out.println(companies);
        result.put("results", companies);
        return result;

    }
}