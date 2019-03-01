package com.prabandhah.prabandhah.dataclasses;

public class EventClass {
    String eventid;
    String eventname;
    String eventype;
    String noofguest;
    String budget;
    String address;
    String description;
    String eventmanager;

    public EventClass(String eventid, String eventname, String eventype, String noofguest, String budget, String address, String description, String eventmanager) {
        this.eventid = eventid;
        this.eventname = eventname;
        this.eventype = eventype;
        this.noofguest = noofguest;
        this.budget = budget;
        this.address = address;
        this.description = description;
        this.eventmanager = eventmanager;
    }

    public EventClass() {
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
}
