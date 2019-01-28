package com.guiji.dispatch.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class FileRecords implements Serializable {
    private Long id;

    private Integer batchid;

    private String batchName;

    private String fileName;

    private Integer successCount;

    private Integer failureCount;

    private Date createTime;

    private Integer userId;

    private String orgCode;

    private String robot;

    private String lineId;

    private String callData;

    private String status;

    private String callHour;

    private Integer isClean;

    private String url;

    private String filePath;

    private String lineName;

    private String robotName;

    private String userName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBatchid() {
        return batchid;
    }

    public void setBatchid(Integer batchid) {
        this.batchid = batchid;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName == null ? null : batchName.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot == null ? null : robot.trim();
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public String getCallData() {
        return callData;
    }

    public void setCallData(String callData) {
        this.callData = callData == null ? null : callData.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCallHour() {
        return callHour;
    }

    public void setCallHour(String callHour) {
        this.callHour = callHour == null ? null : callHour.trim();
    }

    public Integer getIsClean() {
        return isClean;
    }

    public void setIsClean(Integer isClean) {
        this.isClean = isClean;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName == null ? null : robotName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", batchid=").append(batchid);
        sb.append(", batchName=").append(batchName);
        sb.append(", fileName=").append(fileName);
        sb.append(", successCount=").append(successCount);
        sb.append(", failureCount=").append(failureCount);
        sb.append(", createTime=").append(createTime);
        sb.append(", userId=").append(userId);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", robot=").append(robot);
        sb.append(", lineId=").append(lineId);
        sb.append(", callData=").append(callData);
        sb.append(", status=").append(status);
        sb.append(", callHour=").append(callHour);
        sb.append(", isClean=").append(isClean);
        sb.append(", url=").append(url);
        sb.append(", filePath=").append(filePath);
        sb.append(", lineName=").append(lineName);
        sb.append(", robotName=").append(robotName);
        sb.append(", userName=").append(userName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}