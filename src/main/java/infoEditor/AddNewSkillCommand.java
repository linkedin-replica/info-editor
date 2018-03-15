package  infoEditor;
import database.ArangoHandler;
import database.DatabaseHandler;
import  models.Command;
import models.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

 public class AddNewSkillCommand extends Command{

    public AddNewSkillCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"userId"});
        DatabaseHandler noSqlHandler = (DatabaseHandler) new ArangoHandler();
        this.setDbHandler(noSqlHandler);
        // get notifications from db
        dbHandler.addSkill(args.get("userId"),args.get("Skill"));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        return result;
    }
}