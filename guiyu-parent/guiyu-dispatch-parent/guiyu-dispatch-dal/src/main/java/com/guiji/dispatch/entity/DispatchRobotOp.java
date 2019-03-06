package com.guiji.dispatch.entity;

public class DispatchRobotOp extends BaseBean {

    private static final long serialVersionUID = 877183588602142315L;

    /**
     * 用户ID
     */
    private String userId;

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

    /**
     * 当前分配机器人总数
     */
    private Integer currentNum;

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

    public Integer getSupplType() {
        return supplType;
    }

    public void setSupplType(Integer supplType) {
        this.supplType = supplType;
    }

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }
}
