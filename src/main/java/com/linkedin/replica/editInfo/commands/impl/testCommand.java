package com.linkedin.replica.editInfo.commands.impl;

import com.linkedin.replica.editInfo.commands.Command;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class testCommand extends Command{
    public testCommand(HashMap<String, Object> args) {
        super(args);
    }

    @Override
    public Object execute()  {
        return null;
    }
}
