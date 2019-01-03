package com.guiji.ai.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsModel implements Serializable {
    private Integer id;

    private String model;

    private String ttsIp;

    private String ttsPort;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String company;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getTtsIp() {
        return ttsIp;
    }

    public void setTtsIp(String ttsIp) {
        this.ttsIp = ttsIp == null ? null : ttsIp.trim();
    }

    public String getTtsPort() {
        return ttsPort;
    }

    public void setTtsPort(String ttsPort) {
        this.ttsPort = ttsPort == null ? null : ttsPort.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", model=").append(model);
        sb.append(", ttsIp=").append(ttsIp);
        sb.append(", ttsPort=").append(ttsPort);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", company=").append(company);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}