package com.linkedin.replica.editInfo.models;

public class LightCompany {
    private String companyName;
    private String companyId;
    private String profilePictureUrl;

public LightCompany(){
    super();
}
    public LightCompany(String companyName, String companyID) {
        this.companyName = companyName;
        this.companyId = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyID() {
        return companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyID(String companyID) {
        this.companyId = companyID;
    }
}
