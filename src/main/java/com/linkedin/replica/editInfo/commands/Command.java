package com.linkedin.replica.editInfo.commands;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gson.JsonObject;
import com.linkedin.replica.editInfo.cache.handlers.CacheHandler;
import com.linkedin.replica.editInfo.database.handlers.DatabaseHandler;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;
import com.linkedin.replica.editInfo.exceptions.BadRequestException;

public abstract class Command {
    protected HashMap<String, Object> args;
    protected DatabaseHandler dbHandler;
    protected CacheHandler cacheHandler;
    public Command(HashMap<String, Object> args) {
        this.args = args;
    }

    /**
     * Execute the command
     * @return The output (if any) of the command
     */

    public abstract Object execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
    public void setDbHandler(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }
    public void setCacheHandler(CacheHandler dbHandler) {
        this.cacheHandler = dbHandler;
    }

    protected void validateArgs(String[] requiredArgs) {
        JsonObject object = (JsonObject) args.get("request");
        for(String arg: requiredArgs)
            if(!object.keySet().contains(arg)) {
                String exceptionMsg = String.format("Cannot execute command. %s argument is missing", arg);
                throw new BadRequestException(exceptionMsg);
            }
    }
}
