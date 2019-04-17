package com.guiji.dispatch.bean;

import java.io.Serializable;

public class UserBotenceRes implements Serializable {

    private static final long serialVersionUID = 3908114202634112232L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 话术模板ID
     */
    private String botstenceId;

    /**
     * 类型 1-默认普通类型 2-飞龙类型
     */
    private Integer type;

    /**
     * 给人+模板分配的机器人数(单位:人+模板)
     */
    private Integer robotDisNum;

    /**
     * 分配给人的最大机器人总数(单位:人)
     */
    private Integer robotMaxNum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBotstenceId() {
        return botstenceId;
    }

    public void setBotstenceId(String botstenceId) {
        this.botstenceId = botstenceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRobotDisNum() {
        return robotDisNum;
    }

    public void setRobotDisNum(Integer robotDisNum) {
        this.robotDisNum = robotDisNum;
    }

    public Integer getRobotMaxNum() {
        return robotMaxNum;
    }

    public void setRobotMaxNum(Integer robotMaxNum) {
        this.robotMaxNum = robotMaxNum;
    }
}
