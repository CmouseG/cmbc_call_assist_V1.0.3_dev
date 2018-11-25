package com.guiji.common.model.process;

import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;

import java.io.Serializable;
import java.util.Map;

public class ProcessInstanceVO implements Serializable, Cloneable  {

    private String ip;

    private  Integer port;

    private ProcessTypeEnum type;

    private ProcessStatusEnum status;

    private String name;

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

    public ProcessTypeEnum getType() {
        return type;
    }

    public void setType(ProcessTypeEnum type) {
        this.type = type;
    }

    public ProcessStatusEnum getStatus() {
        return status;
    }

    public String getWhoUsed() {
        return whoUsed;
    }

    public void setWhoUsed(String whoUsed) {
        this.whoUsed = whoUsed;
    }

    public void setStatus(ProcessStatusEnum status) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProcessInstanceVO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", type=" + type +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", whoUsed='" + whoUsed + '\'' +
                ", processKey='" + processKey + '\'' +
                ", paramter=" + paramter +
                '}';
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
