package com.guiji.dispatch.dto;

import com.guiji.dispatch.sys.PageDto;

public class DispatchRobotOpDto extends PageDto {

    /**
     * 话术用户ID
     */
    private String botstenceUserId;

    /**
     * 话术模板ID
     */
    private String botstenceId;

    /**
     * 分配模板机器人数
     */
    private Integer robotNum;

    /**
     * 补充机器人数量
     */
    private Integer supplNum;

    /**
     * 补充标识，1-补充，2-删除
     */
    private Integer supplType;

    private String userName;

    public String getBotstenceUserId() {
        return botstenceUserId;
    }

    public void setBotstenceUserId(String botstenceUserId) {
        this.botstenceUserId = botstenceUserId;
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

    public Integer getSupplType() {
        return supplType;
    }

    public void setSupplType(Integer supplType) {
        this.supplType = supplType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
