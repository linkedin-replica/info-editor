package  infoEditor;
import  models.Command;

import java.util.HashMap;
import java.util.LinkedHashMap;

class DeleteCvCommand extends Command{

    public DeleteCvCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() {

        dbHandler.deleteCV(null);
        return null;
    }

}