package com.prabandhah.prabandhah.dataclasses;

import android.widget.Spinner;

public class EventClass {
    public String eventid;
    public String eventname;
    public String eventype;
    public String noofguest;
    public String budget;
    public String address;
    public String description;
    public String eventmanager;
    public String startdate;
    public String enddate;
    public String starttime;
    public String endtime;
    public String eventstatus;
    public String eventcreationdate;
    public String eventcreationtime;
    public EventClass() {
    }
    public EventClass(String eventid, String eventname, String eventype, String noofguest, String budget, String address, String description, String eventmanager, String startdate, String enddate, String starttime, String endtime, String eventcreationdate, String eventcreationtime ,String eventstatus) {
        this.eventid = eventid;
        this.eventname = eventname;
        this.eventype = eventype;
        this.noofguest = noofguest;
        this.budget = budget;
        this.address = address;
        this.description = description;
        this.eventmanager = eventmanager;
        this.startdate = startdate;
        this.enddate = enddate;
        this.starttime = starttime;
        this.endtime = endtime;
        this.eventstatus = eventstatus;
        this.eventcreationdate = eventcreationdate;
        this.eventcreationtime = eventcreationtime;
    }



    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventype() {
        return eventype;
    }

    public void setEventype(String eventype) {
        this.eventype = eventype;
    }

    public String getNoofguest() {
        return noofguest;
    }

    public void setNoofguest(String noofguest) {
        this.noofguest = noofguest;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventmanager() {
        return eventmanager;
    }

    public void setEventmanager(String eventmanager) {
        this.eventmanager = eventmanager;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getEventstatus() {
        return eventstatus;
    }

    public void setEventstatus(String eventstatus) {
        this.eventstatus = eventstatus;
    }

    public String getEventcreationdate() {
        return eventcreationdate;
    }

    public void setEventcreationdate(String eventcreationdate) {
        this.eventcreationdate = eventcreationdate;
    }

    public String getEventcreationtime() {
        return eventcreationtime;
    }

    public void setEventcreationtime(String eventcreationtime) {
        this.eventcreationtime = eventcreationtime;
    }
}
