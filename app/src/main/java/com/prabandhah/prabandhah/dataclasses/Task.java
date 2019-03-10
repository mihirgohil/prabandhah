package com.prabandhah.prabandhah.dataclasses;

public class Task {
    public String taskid;
    public String task;
    public String taskstatus;
    public String teamid;
    public Task(String taskid, String task, String taskstatus,String teamid) {
        this.taskid = taskid;
        this.task = task;
        this.taskstatus = taskstatus;
        this.teamid = teamid;
    }

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }

    public Task() {
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }
}
