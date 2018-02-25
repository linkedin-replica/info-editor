package  infoEditor;
import  models.Command;
import models.Company;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

class GetCompanyProfileCommamd extends Command{

    public GetCompanyProfileCommamd(HashMap<String, String> args) {
        super(args);
    }
    public LinkedHashMap<String, Object> execute() {
        // validate that all required arguments are passed
        validateArgs(new String[]{"companyId"});
        // get notifications from db
        Company companies = dbHandler.getCompany("companyId");

        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("results", companies);
        return result;

}
}