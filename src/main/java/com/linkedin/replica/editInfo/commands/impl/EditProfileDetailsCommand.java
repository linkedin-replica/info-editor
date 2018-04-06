package com.linkedin.replica.editInfo.commands.impl;
import com.linkedin.replica.editInfo.commands.Command;
import com.linkedin.replica.editInfo.cache.handlers.impl.CacheEditInfoHandler;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base64;
public class EditProfileDetailsCommand extends Command{
    private CacheEditInfoHandler cacheeditInfoHandler;
    public EditProfileDetailsCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute()  throws IOException {
        // validate that all required arguments are passed
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

            validateArgs(new String[]{"userId"});
            EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
            // get notifications from db
            dbHandler.updateProfile(args, (String) args.get("userId"));
            cacheeditInfoHandler = (CacheEditInfoHandler) cacheHandler;
            cacheeditInfoHandler.editUserCache((String) args.get("userId"), cacheargs);
            return "Profile Edited successfully";

    }
}