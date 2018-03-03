package  infoEditor;
import database.ArangoHandler;
import database.DatabaseHandler;
import  models.Command;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DeleteCvCommand extends Command{

    public DeleteCvCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
        validateArgs(new String[]{"userId"});
        // get notifications from db
        DatabaseHandler noSqlHandler = (DatabaseHandler) new ArangoHandler();

        this.setDbHandler(noSqlHandler);

        dbHandler.deleteCV(args.get("userId"));
        LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
        resutls.put("response",true);
        return resutls;
    }

}