package com.guiji.simagent.entity;

import java.io.Serializable;

public class FsSipOprVO implements Serializable {
    private String sipIp;
    private String sipPort;

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
        return "FsSipOprVO{" +
                "sipIp='" + sipIp + '\'' +
                ", sipPort='" + sipPort + '\'' +
                '}';
    }
}
