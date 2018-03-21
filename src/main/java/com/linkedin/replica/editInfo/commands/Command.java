package com.linkedin.replica.editInfo.commands;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.linkedin.replica.editInfo.database.handlers.EditInfoHandler;

public abstract class Command {
    protected HashMap<String, String> args;
    protected EditInfoHandler dbHandler;
    public Command(HashMap<String, String> args) {
        this.args = args;
    }

    /**
     * Execute the command
     * @return The output (if any) of the command
     */

    public abstract LinkedHashMap<String, Object> execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
    public void setDbHandler(EditInfoHandler dbHandler) {
        this.dbHandler = dbHandler;
    }
    protected void validateArgs(String[] requiredArgs) {
        for(String arg: requiredArgs)
            if(!args.containsKey(arg)) {
                String exceptionMsg = String.format("Cannot execute command. %s argument is missing", arg);
                throw new IllegalArgumentException(exceptionMsg);
            }
    }
}
