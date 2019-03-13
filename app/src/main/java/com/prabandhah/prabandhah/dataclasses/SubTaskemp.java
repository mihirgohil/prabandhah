package com.prabandhah.prabandhah.dataclasses;

public class SubTaskemp {
    public String userid;
    public String subtaskid;
    public String status;
    public String subtask;
    public String maintaskid;
    public String eventid;
    public String teamid;
    public SubTaskemp() {
    }

    public String getMaintaskid() {
        return maintaskid;
    }

    public void setMaintaskid(String maintaskid) {
        this.maintaskid = maintaskid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }

    public String getSubtask() {
        return subtask;
    }

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public SubTaskemp(String userid, String subtaskid, String status, String subtask, String maintaskid, String eventid, String teamid) {
        this.userid = userid;
        this.subtaskid = subtaskid;
        this.status = status;
        this.subtask = subtask;
        this.maintaskid = maintaskid;
        this.eventid = eventid;
        this.teamid = teamid;
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
