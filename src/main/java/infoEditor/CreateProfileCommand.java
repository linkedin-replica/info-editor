package infoEditor;

import database.ArangoHandler;
import database.DatabaseHandler;
import models.Command;
import models.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CreateProfileCommand extends Command {

    public CreateProfileCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute()  throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        DatabaseHandler noSqlHandler = (DatabaseHandler) new ArangoHandler();
        this.setDbHandler(noSqlHandler);
         dbHandler.createProfile(args,args.get("userId"));
        LinkedHashMap<String, Object> resutls = new LinkedHashMap<String, Object>();
        resutls.put("response",true);
        return resutls;
    }
}
