package com.guiji.voipgateway.synway.dao.entity;

/**
 * @Classname PortStatus
 * @Description TODO
 * @Date 2019/5/14 13:39
 * @Created by qinghua
 */
public class PortStatus {

    /**
     * 端口号
     */
    private Integer portId;

    /**
     * 运行状态
     */
    private Integer runStatus;

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
    }

    public Integer getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Integer runStatus) {
        this.runStatus = runStatus;
    }
}
