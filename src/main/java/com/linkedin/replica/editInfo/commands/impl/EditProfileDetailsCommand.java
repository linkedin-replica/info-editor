package com.linkedin.replica.editInfo.commands.impl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
            EditInfoHandler dbHandler = (EditInfoHandler) this.dbHandler;
            JsonObject request = (JsonObject)args.get("request");
            dbHandler.updateProfile(request);
            return null;
    }
}