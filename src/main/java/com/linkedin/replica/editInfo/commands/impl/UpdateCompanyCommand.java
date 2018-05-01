package com.linkedin.replica.editInfo.commands.impl;
import com.google.gson.Gson;
import com.linkedin.replica.editInfo.cache.CacheConnection;
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
        LinkedHashMap<String, String> cacheArgs = new LinkedHashMap<>();

        Gson gson = CacheConnection.getGson();
        args.keySet().forEach(key -> {
            Object value = args.get(key);
            String jsonValue = gson.toJson(value);
            cacheArgs.put(key, jsonValue);
        });
        CacheEditInfoHandler cacheEditInfoHandler = (CacheEditInfoHandler) cacheHandler;
        cacheEditInfoHandler.editCompanyFromCache((String) args.get("companyId"), cacheArgs);
        return dbHandler.updateCompany(args);
    }
}