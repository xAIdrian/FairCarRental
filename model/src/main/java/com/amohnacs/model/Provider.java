package com.amohnacs.model;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Provider {

    private String companyCode;
    private String companyName;

    public Provider() {
    }

    public Provider(String companyCode, String companyName) {
        this.companyCode = companyCode;
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
