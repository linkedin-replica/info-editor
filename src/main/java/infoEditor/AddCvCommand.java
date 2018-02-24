package  infoEditor;
import  models.Command;

import java.util.HashMap;
import java.util.LinkedHashMap;

class AddCvCommand extends Command{

    public AddCvCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() {

        dbHandler.addCV(null,null);
        return null;
    }


}
