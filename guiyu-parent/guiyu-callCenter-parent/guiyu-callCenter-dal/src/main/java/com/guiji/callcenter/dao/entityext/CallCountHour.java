package com.guiji.callcenter.dao.entityext;

import java.io.Serializable;
import java.util.Date;

public class CallCountHour implements Serializable {

    private String callHour;

    private Integer outCount;

    private Integer connectCount;

    private Long duration;


    private static final long serialVersionUID = 1L;

    public String getCallHour() {
        return callHour;
    }

    public void setCallHour(String callHour) {
        this.callHour = callHour;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public Integer getConnectCount() {
        return connectCount;
    }

    public void setConnectCount(Integer connectCount) {
        this.connectCount = connectCount;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}