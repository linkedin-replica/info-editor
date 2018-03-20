package commands;
import database.DatabaseHandler;
import  models.Command;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class EditProfileDetailsCommand extends Command{

    public EditProfileDetailsCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute()  throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        DatabaseHandler dbHandler = (DatabaseHandler) this.dbHandler;
        validateArgs(new String[]{"userId"});
        // get notifications from db
        dbHandler.updateProfile (args, args.get("userId"));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        //result.put("results", user);
       return result;
    }
}