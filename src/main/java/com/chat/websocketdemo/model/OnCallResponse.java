package com.chat.websocketdemo.model;

import java.util.Date;

public class OnCallResponse {

    private String group;
    private String user;
    private Date startTime;
    private Date endTime;
    private String type;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OnCallResponse [group=" + group + ", user=" + user + ", startTime=" + startTime + ", endTime=" + endTime
                + ", type=" + type + "]";
    }

}
