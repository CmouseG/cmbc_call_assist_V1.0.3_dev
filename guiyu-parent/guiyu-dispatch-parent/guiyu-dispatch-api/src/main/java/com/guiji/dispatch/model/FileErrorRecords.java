package com.guiji.dispatch.model;

import java.io.Serializable;
import java.util.Date;

public class FileErrorRecords implements Serializable {
    private Long id;

    private String phone;

    private String attach;

    private String params;

    private Date createTime;

    private Long fileRecordsId;

    private Integer errorType;

    private Integer errorLine;

    private Integer dataType;

    private Integer batchId;

    private String batchName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getFileRecordsId() {
        return fileRecordsId;
    }

    public void setFileRecordsId(Long fileRecordsId) {
        this.fileRecordsId = fileRecordsId;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public Integer getErrorLine() {
        return errorLine;
    }

    public void setErrorLine(Integer errorLine) {
        this.errorLine = errorLine;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName == null ? null : batchName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", phone=").append(phone);
        sb.append(", attach=").append(attach);
        sb.append(", params=").append(params);
        sb.append(", createTime=").append(createTime);
        sb.append(", fileRecordsId=").append(fileRecordsId);
        sb.append(", errorType=").append(errorType);
        sb.append(", errorLine=").append(errorLine);
        sb.append(", dataType=").append(dataType);
        sb.append(", batchId=").append(batchId);
        sb.append(", batchName=").append(batchName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}