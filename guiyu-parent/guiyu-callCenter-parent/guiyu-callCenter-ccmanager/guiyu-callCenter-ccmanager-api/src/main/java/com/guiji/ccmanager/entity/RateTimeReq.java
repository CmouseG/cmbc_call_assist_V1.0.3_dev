package com.guiji.ccmanager.entity;

import java.io.Serializable;
import java.util.Date;

public class RateTimeReq implements Serializable {

    private static final long serialVersionUID = 4079789767639908117L;

    private Date startTime;

    private Date endTime;

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
}
