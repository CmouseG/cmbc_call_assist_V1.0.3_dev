package com.guiji.ai.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsModel implements Serializable {
    private Integer id;

    private String model;

    private String ttsIp;

    private String ttsPort;

    private String status;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
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
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", company=").append(company);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}