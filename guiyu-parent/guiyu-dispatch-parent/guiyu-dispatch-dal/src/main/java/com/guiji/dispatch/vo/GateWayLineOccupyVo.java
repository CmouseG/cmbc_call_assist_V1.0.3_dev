package com.guiji.dispatch.vo;

public class GateWayLineOccupyVo {

    /**
     * 网关线路ID
     */
    private String lineId;

    /**
     * 占用标识  0-闲置，1-占用
     */
    private Integer status;

    /**
     * 占用时间
     */
    private String occupyTime;

    /**
     * 释放时间
     */
    private String releaseTime;

    /**
     * 被占用户ID
     */
    private String userId;

    /**
     * 被占话术模板ID
     */
    private String botstenceId;

    /**
     * planuuid
     */
    private String plannUuid;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOccupyTime() {
        return occupyTime;
    }

    public void setOccupyTime(String occupyTime) {
        this.occupyTime = occupyTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

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

    public String getPlannUuid() {
        return plannUuid;
    }

    public void setPlannUuid(String plannUuid) {
        this.plannUuid = plannUuid;
    }
}
