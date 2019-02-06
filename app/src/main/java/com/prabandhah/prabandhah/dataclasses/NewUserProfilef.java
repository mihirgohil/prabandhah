package com.prabandhah.prabandhah.dataclasses;

import android.support.v4.view.PagerAdapter;

/**
 * Created by mihir on 1/30/2019.
 */

public class NewUserProfilef {
    public String user_mail_id;
    public String user_name;
    public String role;
    public String company_id;
    public String team_id;

    public String getTeam_id() {

        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getCompany_id() {

        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public NewUserProfilef(String user_mail_id, String user_name, String role, String company_id, String team_id) {

        this.user_mail_id = user_mail_id;
        this.user_name = user_name;
        this.role = role;
        this.company_id = company_id;
        this.team_id = team_id;
    }

    public NewUserProfilef(String user_mail_id, String user_name) {
        this.user_mail_id = user_mail_id;
        this.user_name = user_name;
    }

    public NewUserProfilef() {
    }

    public NewUserProfilef(String role) {
        this.role = role;
    }

    public NewUserProfilef(String user_mail_id, String user_name, String role) {
        this.user_mail_id = user_mail_id;
        this.user_name = user_name;
        this.role = role;
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
}
