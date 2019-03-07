package com.prabandhah.prabandhah.dataclasses;

public class Task {
    public String taskid;
    public String task;
    public String taskstatus;

    public Task(String taskid, String task, String taskstatus) {
        this.taskid = taskid;
        this.task = task;
        this.taskstatus = taskstatus;
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
