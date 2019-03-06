package com.guiji.dispatch.vo;

import java.io.Serializable;

public class DispatchRobotOpVo implements Serializable {

    private static final long serialVersionUID = -8943778791417845611L;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 话术模板ID
     */
    private String botstenceId;

    /**
     * 话术模板名称
     */
    private String botstenceName;

    /**
     * 最大机器人数
     */
    private Integer maxRobotNum;

    /**
     * 分配模板机器人数
     */
    private Integer robotNum;

    /**
     * 补充机器人数量
     */
    private Integer supplNum;

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

    public Integer getRobotNum() {
        return robotNum;
    }

    public void setRobotNum(Integer robotNum) {
        this.robotNum = robotNum;
    }

    public Integer getSupplNum() {
        return supplNum;
    }

    public void setSupplNum(Integer supplNum) {
        this.supplNum = supplNum;
    }

    public Integer getMaxRobotNum() {
        return maxRobotNum;
    }

    public void setMaxRobotNum(Integer maxRobotNum) {
        this.maxRobotNum = maxRobotNum;
    }

    public String getBotstenceName() {
        return botstenceName;
    }

    public void setBotstenceName(String botstenceName) {
        this.botstenceName = botstenceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
