package com.prabandhah.prabandhah.dataclasses;

public class SubTask {
    public String subtaskid;

    public SubTask() {
    }

    public String subtask;
    public String subtaskstatus;
    public String teamid;
    public String maintaskid;

    public SubTask(String subtaskid, String subtask, String subtaskstatus, String teamid, String maintaskid) {
        this.subtaskid = subtaskid;
        this.subtask = subtask;
        this.subtaskstatus = subtaskstatus;
        this.teamid = teamid;
        this.maintaskid = maintaskid;
    }

    public String getSubtaskid() {
        return subtaskid;
    }

    public void setSubtaskid(String subtaskid) {
        this.subtaskid = subtaskid;
    }

    public String getSubtask() {
        return subtask;
    }

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public String getSubtaskstatus() {
        return subtaskstatus;
    }

    public void setSubtaskstatus(String subtaskstatus) {
        this.subtaskstatus = subtaskstatus;
    }

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }

    public String getMaintaskid() {
        return maintaskid;
    }

    public void setMaintaskid(String maintaskid) {
        this.maintaskid = maintaskid;
    }
}
