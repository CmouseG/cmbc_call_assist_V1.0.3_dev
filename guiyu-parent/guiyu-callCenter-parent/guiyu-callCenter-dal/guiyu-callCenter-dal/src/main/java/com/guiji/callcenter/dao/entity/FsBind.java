package com.guiji.callcenter.dao.entity;

import java.io.Serializable;

public class FsBind implements Serializable {
    private Integer id;

    private String serviceId;

    private String serviceName;

    private String fsAgentId;

    private String fsAgentAddr;

    private String fsEslPort;

    private String fsEslPwd;

    private String fsInPort;

    private String fsOutPort;

    private String createDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public String getFsAgentId() {
        return fsAgentId;
    }

    public void setFsAgentId(String fsAgentId) {
        this.fsAgentId = fsAgentId == null ? null : fsAgentId.trim();
    }

    public String getFsAgentAddr() {
        return fsAgentAddr;
    }

    public void setFsAgentAddr(String fsAgentAddr) {
        this.fsAgentAddr = fsAgentAddr == null ? null : fsAgentAddr.trim();
    }

    public String getFsEslPort() {
        return fsEslPort;
    }

    public void setFsEslPort(String fsEslPort) {
        this.fsEslPort = fsEslPort == null ? null : fsEslPort.trim();
    }

    public String getFsEslPwd() {
        return fsEslPwd;
    }

    public void setFsEslPwd(String fsEslPwd) {
        this.fsEslPwd = fsEslPwd == null ? null : fsEslPwd.trim();
    }

    public String getFsInPort() {
        return fsInPort;
    }

    public void setFsInPort(String fsInPort) {
        this.fsInPort = fsInPort == null ? null : fsInPort.trim();
    }

    public String getFsOutPort() {
        return fsOutPort;
    }

    public void setFsOutPort(String fsOutPort) {
        this.fsOutPort = fsOutPort == null ? null : fsOutPort.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", serviceId=").append(serviceId);
        sb.append(", serviceName=").append(serviceName);
        sb.append(", fsAgentId=").append(fsAgentId);
        sb.append(", fsAgentAddr=").append(fsAgentAddr);
        sb.append(", fsEslPort=").append(fsEslPort);
        sb.append(", fsEslPwd=").append(fsEslPwd);
        sb.append(", fsInPort=").append(fsInPort);
        sb.append(", fsOutPort=").append(fsOutPort);
        sb.append(", createDate=").append(createDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}