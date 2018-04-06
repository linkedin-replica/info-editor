package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.cache.handlers.impl.CacheEditInfoHandler;
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
    private CacheEditInfoHandler cacheeditInfoHandler;
    public GetCompanyProfileCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute() throws IOException {
        // validate that all required arguments are passed
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        validateArgs(new String[]{"companyId"});
        // get notifications from db
        String [] ids  = new String[1];
        ids[0]=(String) args.get("companyId");
        cacheeditInfoHandler = (CacheEditInfoHandler)this.cacheHandler;
        Company company = (Company) cacheeditInfoHandler.getCompanyFromCache(ids[0],Company.class);
        if(company!=null) {
            ArrayList<Company>companies = new ArrayList<Company>();
            companies.add(company);
            return company;

        }
        company = dbHandler.getCompany((String)args.get("companyId"));

         ids = new String[1];
        ids[0]=(String)args.get("companyId");
        ArrayList<Company>companies = new ArrayList<Company>();
//        System.out.println(company.companyName);
        companies.add(company);
        cacheeditInfoHandler.saveCompanyInCache(ids,companies);
        return company;

    }
}