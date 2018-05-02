package com.linkedin.replica.editInfo.commands.impl;
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
        String companyId =(String) args.get("companyId");
        CacheEditInfoHandler cacheEditInfoHandler = (CacheEditInfoHandler) this.cacheHandler;
        CompanyReturn company = (CompanyReturn) cacheEditInfoHandler.getCompanyFromCache(companyId);
        if(company != null) {
            return company;
        }
        cacheEditInfoHandler.saveCompanyInCache(companyId, company);
        company = dbHandler.getCompany((String)args.get("companyId"));
        return company;
    }
}