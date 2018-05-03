package com.linkedin.replica.editInfo.commands.impl;
import com.google.gson.JsonObject;
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

    public UpdateCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute()  throws IOException {
        validateArgs(new String[]{"companyId"});
        EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
        LinkedHashMap<String, String> cacheargs = new LinkedHashMap<>();
        JsonObject request = (JsonObject)args.get("request");

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

        args.put("companyName",request.get("companyName").getAsString());
        args.put("industryType",request.get("industryType").getAsString());
        args.put("aboutus",request.get("aboutus").getAsString());
        args.put("companyProfilePicture",request.get("companyProfilePicture").getAsString());
        args.put("adminUserId",request.get("adminUserId").getAsString());

        ArrayList<String > postsArra = new ArrayList<String>();
        JsonObject posts = (JsonObject)request.get("posts");
        for(String key : posts.keySet())
            postsArra.add(posts.get(key).getAsString());


        ArrayList<String > skillsArra = new ArrayList<String>();
        JsonObject skills = (JsonObject)request.get("skills");
        for(String key : skills.keySet())
            skillsArra.add(skills.get(key).getAsString());



        args.put("posts",postsArra);
        args.put("skills",skillsArra);

        String response = dbHandler.updateCompany(args);
//        CacheEditInfoHandler cacheEditInfoHandler = (CacheEditInfoHandler) cacheHandler;
//        cacheEditInfoHandler.editcompanyFromCache((String) args.get("companyId"), cacheargs);
        return response;
    }
}