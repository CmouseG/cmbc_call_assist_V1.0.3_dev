package com.guiji.process.agent.model;

import java.io.Serializable;

/**
 * Created by ty on 2018/11/19.
 */
public class ProcessStatus implements Serializable{
    private static final long serialVersionUID = 1L;
    private String ip;

    private String port;

    private String pid;

    private boolean isUp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    @Override
    public String toString() {
        return "ProcessStatus{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", pid='" + pid + '\'' +
                ", isUp=" + isUp +
                '}';
    }
}
