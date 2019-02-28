package com.guiji.fsmanager.entity;

import java.io.Serializable;

public class FsSipVO implements Serializable {
    private String sipIp;
    private String sipPort;
    private String linePort;

    public String getLinePort() {
        return linePort;
    }

    public void setLinePort(String linePort) {
        this.linePort = linePort;
    }

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

    @Override
    public String toString() {
        return "FsSipVO{" +
                "sipIp='" + sipIp + '\'' +
                ", sipPort='" + sipPort + '\'' +
                ", linePort='" + linePort + '\'' +
                '}';
    }
}
