package  infoEditor;
import  models.Command;
import models.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class GetUserProfileCommand extends Command{

    public GetUserProfileCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        // get notifications from db
        System.out.println(args.get("userId"));
        User user = dbHandler.getUserProfile(args.get("userId"));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("results", user);
        return result;
    }
}