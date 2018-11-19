package com.guiji.process.vo;

import java.io.Serializable;

public class DeviceVO implements Serializable {

    private String ip;

    private  Integer port;

    private DeviceTypeEnum type;

    private DeviceStatusEnum status;

    private String whoUsed;

    private String deviceKey;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public DeviceTypeEnum getType() {
        return type;
    }

    public void setType(DeviceTypeEnum type) {
        this.type = type;
    }

    public DeviceStatusEnum getStatus() {
        return status;
    }

    public String getWhoUsed() {
        return whoUsed;
    }

    public void setWhoUsed(String whoUsed) {
        this.whoUsed = whoUsed;
    }

    public void setStatus(DeviceStatusEnum status) {
        this.status = status;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    @Override
    public String toString() {
        return "DeviceVO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", type=" + type +
                ", status=" + status +
                ", whoUsed='" + whoUsed + '\'' +
                ", deviceKey='" + deviceKey + '\'' +
                '}';
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
