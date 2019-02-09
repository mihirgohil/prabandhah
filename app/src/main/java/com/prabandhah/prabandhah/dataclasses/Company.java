package com.prabandhah.prabandhah.dataclasses;

/**
 * Created by mihir on 2/7/2019.
 */

public class Company {
    String company_name;
    String company_mail;
    String company_address;
    String user_id;
    String company_id;

    public Company() {
    }

    public Company(String company_name, String company_mail, String company_address, String user_id, String company_id) {
        this.company_name = company_name;
        this.company_mail = company_mail;
        this.company_address = company_address;
        this.user_id = user_id;
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_mail() {
        return company_mail;
    }

    public void setCompany_mail(String company_mail) {
        this.company_mail = company_mail;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
