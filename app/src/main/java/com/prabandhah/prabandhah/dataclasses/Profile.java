package com.prabandhah.prabandhah.dataclasses;

/**
 * Created by mihir on 2/9/2019.
 */

public class Profile {
    public String user_mail_id;
    public String user_name;
    public String role;
    public String company_id;
    public String user_id;

    public Profile() {
    }

    public Profile(String company_id) {
        this.company_id = company_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public Profile(String user_mail_id, String user_name, String role, String company_id , String user_id) {
        this.user_mail_id = user_mail_id;
        this.user_name = user_name;
        this.role = role;
        this.company_id = company_id;
        this.user_id = user_id;
    }

    public String getUser_mail_id() {
        return user_mail_id;
    }

    public void setUser_mail_id(String user_mail_id) {
        this.user_mail_id = user_mail_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }


}
