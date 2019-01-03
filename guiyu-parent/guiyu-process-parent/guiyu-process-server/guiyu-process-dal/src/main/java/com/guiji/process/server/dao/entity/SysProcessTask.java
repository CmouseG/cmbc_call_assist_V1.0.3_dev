package com.guiji.process.server.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class SysProcessTask implements Serializable {
    private Integer id;

    private Integer processId;

    private String ip;

    private String port;

    private Integer cmdType;

    private String processKey;

    private String parameters;

    private Integer result;

    private String resultContent;

    private Integer execStatus;

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;

    private String reqKey;

    private String createTimeStr;

    private String updateTimeStr;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public Integer getCmdType() {
        return cmdType;
    }

    public void setCmdType(Integer cmdType) {
        this.cmdType = cmdType;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey == null ? null : processKey.trim();
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters == null ? null : parameters.trim();
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent == null ? null : resultContent.trim();
    }

    public Integer getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
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

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey == null ? null : reqKey.trim();
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", processId=").append(processId);
        sb.append(", ip=").append(ip);
        sb.append(", port=").append(port);
        sb.append(", cmdType=").append(cmdType);
        sb.append(", processKey=").append(processKey);
        sb.append(", parameters=").append(parameters);
        sb.append(", result=").append(result);
        sb.append(", resultContent=").append(resultContent);
        sb.append(", execStatus=").append(execStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", reqKey=").append(reqKey);
        sb.append(", createTimeStr=").append(createTimeStr);
        sb.append(", updateTimeStr=").append(updateTimeStr);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}