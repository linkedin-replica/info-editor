package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.IOException;

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


//        for (String key : args.keySet()) {
//            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
//            objectOutputStream.writeObject(cacheargs.get(key));
//            objectOutputStream.flush();
//            Base64 base64 = new Base64();
//            String stringvalue = new String(base64.encode(arrayOutputStream.toByteArray()));
//            System.out.println(stringvalue+"heree");
//            cacheargs.put(key, stringvalue);
//        }

        String response = dbHandler.updateCompany(args);
//        CacheEditInfoHandler cacheEditInfoHandler = (CacheEditInfoHandler) cacheHandler;
//        cacheEditInfoHandler.editcompanyFromCache((String) args.get("companyId"), cacheargs);
        return response;
    }
}