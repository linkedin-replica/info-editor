package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.cache.handlers.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.GZIPOutputStream;

public class UpdateCompanyCommand extends Command{
    private CacheEditInfoHandler cacheeditInfoHandler;
    public UpdateCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute()  throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"companyId"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        // get notifications from db
        LinkedHashMap<String, String> cacheargs = new LinkedHashMap<String, String>();


        for (String key : args.keySet()) {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(cacheargs.get(key));
            objectOutputStream.flush();
            Base64 base64 = new Base64();
            String stringvalue = new String(base64.encode(arrayOutputStream.toByteArray()));
            System.out.println(stringvalue+"heree");
            cacheargs.put(key, stringvalue);
        }
        ArrayList<String >posts = new ArrayList<String>();
        ArrayList<String >jobListings = new ArrayList<String>();
        ArrayList<String> specialities = new ArrayList<String>();
        System.out.println(cacheargs+"cache");
        dbHandler.updateCompany(args);
        cacheeditInfoHandler = (CacheEditInfoHandler) cacheHandler;
        cacheeditInfoHandler.editcompanyFromCache((String) args.get("companyId"), cacheargs);
        return "Company updated successfully";
    }
}