package  infoEditor;
import database.ArangoHandler;
import database.DatabaseHandler;
import  models.Command;
import models.Company;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class GetCompanyProfileCommand extends Command{

    public GetCompanyProfileCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() throws IOException {
        // validate that all required arguments are passed
        validateArgs(new String[]{"companyId"});
        // get notifications from db
        DatabaseHandler noSqlHandler = (DatabaseHandler) new ArangoHandler();

        this.setDbHandler(noSqlHandler);
        Company companies = dbHandler.getCompany(args.get("companyId"));

        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        System.out.println(companies);
        result.put("results", companies);
        return result;

}
}