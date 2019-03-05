package com.guiji.simagent.entity;

import java.io.Serializable;

public class FsSipOprVO implements Serializable {
    private String sipIp;
    private String sipPort;
    private String linePort;

    public String getSipIp() {
        return sipIp;
    }

    public void setSipIp(String sipIp) {
        this.sipIp = sipIp;
    }

    public String getSipPort() {
        return sipPort;
    }

    public void setSipPort(String sipPort) {
        this.sipPort = sipPort;
    }

    public String getLinePort() {
        return linePort;
    }

    public void setLinePort(String linePort) {
        this.linePort = linePort;
    }

    @Override
    public String toString() {
        return "FsSipOprVO{" +
                "sipIp='" + sipIp + '\'' +
                ", sipPort='" + sipPort + '\'' +
                ", linePort='" + linePort + '\'' +
                '}';
    }
}
