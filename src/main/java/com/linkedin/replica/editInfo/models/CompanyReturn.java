package com.linkedin.replica.editInfo.models;


public class CompanyReturn {

    public String companyName;
    public String profilePictureUrl;
    public String companyId;
    public String userId;
    public String industryType;
    public String aboutUs;


    public CompanyReturn(String companyName, String profilePictureUrl, String companyId, String userId,
                         String industryType, String aboutUs) {
        this.companyName = companyName;
        this.profilePictureUrl = profilePictureUrl;
        this.companyId = companyId;
        this.userId = userId;
        this.industryType = industryType;
        this.aboutUs = aboutUs;
    }

    public CompanyReturn(){ }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyId() {
        return companyId;
    }


    @Override
    public boolean equals(Object obj) {
        return ((CompanyReturn) (obj)).companyId.equals(companyId);
    }

    @Override
    public String toString() {
        return "CompanyReturn{" +
                "companyName='" + companyName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", companyId='" + companyId + '\'' +
                ", userId='" + userId + '\'' +
                ", industryType='" + industryType + '\'' +
                ", aboutUs='" + aboutUs + '\'' +
                '}';
    }
}