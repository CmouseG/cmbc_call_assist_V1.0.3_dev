package com.guiji.fsagent.entity;

public class FsInfoVO {
    private String fsAgentId;

    private String fsIp;

    private String fsInPort;

    private String fsOutPort;

    private String fsEslPort;

    private String fsEslPwd;

    public String getFsAgentId() {
        return fsAgentId;
    }

    public void setFsAgentId(String fsAgentId) {
        this.fsAgentId = fsAgentId;
    }

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

    @Override
    public String toString() {
        return "FsInfoVO{" +
                "fsAgentId='" + fsAgentId + '\'' +
                ", fsIp='" + fsIp + '\'' +
                ", fsInPort='" + fsInPort + '\'' +
                ", fsOutPort='" + fsOutPort + '\'' +
                ", fsEslPort='" + fsEslPort + '\'' +
                ", fsEslPwd='" + fsEslPwd + '\'' +
                '}';
    }
}
