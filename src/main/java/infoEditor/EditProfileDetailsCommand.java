package  infoEditor;
import  models.Command;
import models.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

class EditProfileDetailsCommamd extends Command{

    public EditProfileDetailsCommamd(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        // get notifications from db
        User user = dbHandler.getUserProfile("userId");
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("results", user);
        return result;
    }
}