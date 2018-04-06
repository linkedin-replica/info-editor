package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.cache.handlers.impl.CacheEditInfoHandler;
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


        for (String key : cacheargs.keySet()) {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(cacheargs.get(key));
            objectOutputStream.flush();
            Base64 base64 = new Base64();
            String stringvalue = new String(base64.encode(arrayOutputStream.toByteArray()));

            cacheargs.put(key, stringvalue);
        }
        ArrayList<String >posts = new ArrayList<String>();
        ArrayList<String >jobListings = new ArrayList<String>();
        ArrayList<String> specialities = new ArrayList<String>();
        specialities.add((String)args.get("specialities"));
        jobListings.add((String)args.get("jobListings"));
        posts.add((String)args.get("posts"));
        dbHandler.updateCompany((String)args.get("companyName"),(String)args.get("companyId"),(String)args.get("companyProfilePicture"),(String)args.get("adminUserName"),(String)args.get("adminUserID"),(String)args.get("industryType"),(String)args.get("companyLocation") ,(String)args.get("companytype"),specialities,posts,jobListings);
        cacheeditInfoHandler = (CacheEditInfoHandler) cacheHandler;
        cacheeditInfoHandler.editcompanyFromCache((String) args.get("companyId"), cacheargs);
        return "Company updated successfully";
    }
}