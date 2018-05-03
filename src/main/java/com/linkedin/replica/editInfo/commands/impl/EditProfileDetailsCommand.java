package com.linkedin.replica.editInfo.commands.impl;
import com.google.gson.JsonArray;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.cache.handlers.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.GZIPOutputStream;
public class EditProfileDetailsCommand extends Command{

    private CacheEditInfoHandler cacheEditInfoHandler;

    public EditProfileDetailsCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute()  throws IOException {
        validateArgs(new String[]{"userId"});
        LinkedHashMap<String, String> cacheargs = new LinkedHashMap<>();
//
//        for (String key : cacheargs.keySet()) {
//            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(arrayOutputStream);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);
//            objectOutputStream.writeObject(cacheargs.get(key));
//            objectOutputStream.flush();
//            Base64 base64 = new Base64();
//            String stringvalue = new String(base64.encode(arrayOutputStream.toByteArray()));
//
//            cacheargs.put(key, stringvalue);
//        }
            EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;

            if(args.keySet().contains("skills"))
            {
                JsonArray temp = ((JsonArray)args.get("skills")).getAsJsonArray();
                ArrayList<String> newArray = new ArrayList<String>();
                for(int i =0 ; i<temp.size();i++)
                    newArray.add(temp.get(i).toString());
                args.put("skills",newArray);
            }
            String response = dbHandler.updateProfile(args);
//            cacheEditInfoHandler = (CacheEditInfoHandler) cacheHandler;
//            cacheEditInfoHandler.editUserCache((String) args.get("userId"), cacheargs);
            return null;
    }
}