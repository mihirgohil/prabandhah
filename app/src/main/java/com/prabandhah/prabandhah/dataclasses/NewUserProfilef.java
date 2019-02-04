package com.prabandhah.prabandhah.dataclasses;

/**
 * Created by mihir on 1/30/2019.
 */

public class NewUserProfilef {
    public String user_mail_id;
    public String user_name;
    public int role;
    public NewUserProfilef(String user_mail_id, String user_name) {
        this.user_mail_id = user_mail_id;
        this.user_name = user_name;
    }

    public NewUserProfilef(int role) {
        this.role = role;
    }

    public NewUserProfilef(String user_mail_id, String user_name, int role) {
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
