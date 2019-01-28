package com.guiji.toagentserver.entity;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/22 10:47
 * @Project：guiyu-parent
 * @Description:
 */
public class FsInfoVO {
    private String fsIp;
    private String fsInPort;
    private String fsOutPort;

    public String getFsIp() {
        return fsIp;
    }

    public void setFsIp(String fsIp) {
        this.fsIp = fsIp;
    }

    public String getFsInPort() {
        return fsInPort;
    }

    public void setFsInPort(String fsInPort) {
        this.fsInPort = fsInPort;
    }

    public String getFsOutPort() {
        return fsOutPort;
    }

    public void setFsOutPort(String fsOutPort) {
        this.fsOutPort = fsOutPort;
    }
}
