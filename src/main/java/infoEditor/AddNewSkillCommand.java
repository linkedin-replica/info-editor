package  infoEditor;
import  models.Command;
import models.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

class AddNewSkillCommamd extends Command{

    public AddNewSkillCommamd(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        // get notifications from db
        dbHandler.addSkill("userId",args.get("skill"));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        return result;
    }
}