package com.prabandhah.prabandhah.dataclasses;

public class SubTaskemp {
    public String userid;
    public String subtaskid;
    public String status;

    public SubTaskemp() {
    }

    public SubTaskemp(String userid, String subtaskid, String status) {
        this.userid = userid;
        this.subtaskid = subtaskid;
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSubtaskid() {
        return subtaskid;
    }

    public void setSubtaskid(String subtaskid) {
        this.subtaskid = subtaskid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
