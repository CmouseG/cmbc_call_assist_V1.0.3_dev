package com.guiji.dispatch.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class PushRecords implements Serializable {
    private Long id;

    private String planuuid;

    private String phone;

    private Date createTime;

    private Integer callbackStatus;

    private Integer userId;

    private Integer line;

    private String robot;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanuuid() {
        return planuuid;
    }

    public void setPlanuuid(String planuuid) {
        this.planuuid = planuuid == null ? null : planuuid.trim();
    }

    public void setPlanuuid(long planuuid) {
        this.planuuid = planuuid +"";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot == null ? null : robot.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", planuuid=").append(planuuid);
        sb.append(", phone=").append(phone);
        sb.append(", createTime=").append(createTime);
        sb.append(", callbackStatus=").append(callbackStatus);
        sb.append(", userId=").append(userId);
        sb.append(", line=").append(line);
        sb.append(", robot=").append(robot);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}