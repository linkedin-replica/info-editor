package commands;
import database.DatabaseHandler;
import  models.Command;
import models.Company;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GetCompanyProfileCommand extends Command{

    public GetCompanyProfileCommand(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() throws IOException {
        // validate that all required arguments are passed
        DatabaseHandler dbHandler = (DatabaseHandler) this.dbHandler;
        validateArgs(new String[]{"companyId"});
        // get notifications from db
        Company companies = dbHandler.getCompany(args.get("companyId"));

        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        System.out.println(companies);
        result.put("results", companies);
        return result;

    }
}