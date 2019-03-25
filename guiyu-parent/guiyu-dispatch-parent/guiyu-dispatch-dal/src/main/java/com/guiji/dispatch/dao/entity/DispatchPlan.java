package com.guiji.dispatch.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DispatchPlan implements Serializable {
	// 是否显示
	private Integer statusShow;

	private boolean isSuccess = true;

	private Integer limitStart;

	private Integer limitEnd;

    private long planUuid;

    private Integer userId;

    private Integer batchId;

    private String phone;

    /**
     * 附加参数，备注
     */
    private String attach;

    private String params;

    private Integer statusPlan;

    private Integer statusSync;

    private Integer recall;

    private String recallParams;

    private String robot;

    private Integer line;

    private String result;

    private String callAgent;

    private Integer clean;

    private Integer callData;

    private String callHour;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer isTts;

    private String username;

    private Integer replayType;

    private Integer isDel;

    private String lineName;

    private String robotName;

    private String batchName;

    private String flag;
    
    private static final long serialVersionUID = 1L;

	private List<String> robotIds;
	
	private String userName ;
	
    private String orgCode;

    /**
     * 组织ID
     */
    private Integer orgId;

    private Integer fileRecordId;

    /**
     * 线路
     */
    List<DispatchBatchLine> lines;

    /**
     * 线路类型 1-SIP 2-路由网关
     * PlanLineTypeEnum
     */
    private Integer lineType;
    
    private String cityName;

    private String cityCode;
    
    
    
    public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public List<DispatchBatchLine> getLines() {
		return lines;
	}

	public void setLines(List<DispatchBatchLine> lines) {
		this.lines = lines;
	}

	public Integer getFileRecordId() {
		return fileRecordId;
	}

	public void setFileRecordId(Integer fileRecordId) {
		this.fileRecordId = fileRecordId;
	}

	public Integer getStatusShow() {
		return statusShow;
	}

	public void setStatusShow(Integer statusShow) {
		this.statusShow = statusShow;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	} 

	public Integer getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	public Integer getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	public List<String> getRobotIds() {
		return robotIds;
	}

	public void setRobotIds(List<String> robotIds) {
		this.robotIds = robotIds;
	}

	public String getUserName() {
		return userName;
	}

    public void setUserName(String userName) {
		this.userName = userName;
	}

    public long getPlanUuidLong() {
        return planUuid;
    }

    public String getPlanUuid() {
        return planUuid + "";
    }

    public void setPlanUuid(long planUuid) {
        this.planUuid = planUuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
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

    public Integer getStatusPlan() {
        return statusPlan;
    }

    public void setStatusPlan(Integer statusPlan) {
        this.statusPlan = statusPlan;
    }

    public Integer getStatusSync() {
        return statusSync;
    }

    public void setStatusSync(Integer statusSync) {
        this.statusSync = statusSync;
    }

    public Integer getRecall() {
        return recall;
    }

    public void setRecall(Integer recall) {
        this.recall = recall;
    }

    public String getRecallParams() {
        return recallParams;
    }

    public void setRecallParams(String recallParams) {
        this.recallParams = recallParams == null ? null : recallParams.trim();
    }

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot == null ? null : robot.trim();
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getCallAgent() {
        return callAgent;
    }

    public void setCallAgent(String callAgent) {
        this.callAgent = callAgent == null ? null : callAgent.trim();
    }

    public Integer getClean() {
        return clean;
    }

    public void setClean(Integer clean) {
        this.clean = clean;
    }

    public Integer getCallData() {
        return callData;
    }

    public void setCallData(Integer callData) {
        this.callData = callData;
    }

    public String getCallHour() {
        return callHour;
    }

    public void setCallHour(String callHour) {
        this.callHour = callHour == null ? null : callHour.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsTts() {
        return isTts;
    }

    public void setIsTts(Integer isTts) {
        this.isTts = isTts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getReplayType() {
        return replayType;
    }

    public void setReplayType(Integer replayType) {
        this.replayType = replayType;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName == null ? null : batchName.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", planUuid=").append(planUuid);
        sb.append(", userId=").append(userId);
        sb.append(", batchId=").append(batchId);
        sb.append(", phone=").append(phone);
        sb.append(", attach=").append(attach);
        sb.append(", params=").append(params);
        sb.append(", statusPlan=").append(statusPlan);
        sb.append(", statusSync=").append(statusSync);
        sb.append(", recall=").append(recall);
        sb.append(", recallParams=").append(recallParams);
        sb.append(", robot=").append(robot);
        sb.append(", line=").append(line);
        sb.append(", result=").append(result);
        sb.append(", callAgent=").append(callAgent);
        sb.append(", clean=").append(clean);
        sb.append(", callData=").append(callData);
        sb.append(", callHour=").append(callHour);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", isTts=").append(isTts);
        sb.append(", username=").append(username);
        sb.append(", replayType=").append(replayType);
        sb.append(", isDel=").append(isDel);
        sb.append(", lineName=").append(lineName);
        sb.append(", robotName=").append(robotName);
        sb.append(", batchName=").append(batchName);
        sb.append(", flag=").append(flag);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", lineType=").append(lineType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}