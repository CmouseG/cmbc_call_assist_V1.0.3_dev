package com.guiji.process.core.vo;

import java.io.Serializable;
import java.util.Map;

public class ProcessInstanceVO implements Serializable {

    private String ip;

    private  Integer port;

    private DeviceTypeEnum type;

    private DeviceStatusEnum status;

    private String whoUsed;

    private String processKey;

    private Map<String, Object> paramter;

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

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }


    public Map<String, Object> getParamter() {
        return paramter;
    }

    public void setParamter(Map<String, Object> paramter) {
        this.paramter = paramter;
    }

    @Override
    public String toString() {
        return "ProcessInstanceVO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", type=" + type +
                ", status=" + status +
                ", whoUsed='" + whoUsed + '\'' +
                ", processKey='" + processKey + '\'' +
                ", paramter=" + paramter +
                '}';
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
