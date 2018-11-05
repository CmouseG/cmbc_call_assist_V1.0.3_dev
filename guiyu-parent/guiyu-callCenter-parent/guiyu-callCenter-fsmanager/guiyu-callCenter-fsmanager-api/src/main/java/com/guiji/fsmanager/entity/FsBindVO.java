package com.guiji.fsmanager.entity;

import java.io.Serializable;

public class FsBindVO implements Serializable {
    private String serviceId;

    private String fsAgentId;

    private String fsAgentAddr;

    private String fsEslPort;

    private String fsEslPwd;

    private String fsInPort;

    private String fsOutPort;

    private static final long serialVersionUID = 1L;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getFsAgentId() {
        return fsAgentId;
    }

    public void setFsAgentId(String fsAgentId) {
        this.fsAgentId = fsAgentId;
    }

    public String getFsAgentAddr() {
        return fsAgentAddr;
    }

    public void setFsAgentAddr(String fsAgentAddr) {
        this.fsAgentAddr = fsAgentAddr;
    }

    public String getFsEslPort() {
        return fsEslPort;
    }

    public void setFsEslPort(String fsEslPort) {
        this.fsEslPort = fsEslPort;
    }

    public String getFsEslPwd() {
        return fsEslPwd;
    }

    public void setFsEslPwd(String fsEslPwd) {
        this.fsEslPwd = fsEslPwd;
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

    @Override
    public String toString() {
        return "FsBind{" +
                "serviceId='" + serviceId + '\'' +
                ", fsAgentId='" + fsAgentId + '\'' +
                ", fsAgentAddr='" + fsAgentAddr + '\'' +
                ", fsEslPort='" + fsEslPort + '\'' +
                ", fsEslPwd='" + fsEslPwd + '\'' +
                ", fsInPort='" + fsInPort + '\'' +
                ", fsOutPort='" + fsOutPort + '\'' +
                '}';
    }

}