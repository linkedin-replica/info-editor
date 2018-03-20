package com.linkedin.replica.editInfo.models;

public class LightCompany {
    private String companyName;
    private String companyID;

    public LightCompany(String companyName, String companyID) {
        this.companyName = companyName;
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
}
