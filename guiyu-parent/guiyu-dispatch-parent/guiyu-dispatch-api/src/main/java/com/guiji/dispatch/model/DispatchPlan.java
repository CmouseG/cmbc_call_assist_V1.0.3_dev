package com.guiji.dispatch.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DispatchPlan implements Serializable {
	// 是否显示
	private Integer statusShow;

	private boolean isSuccess = true;

	private Integer limitStart;

	private Integer limitEnd;
	
    private Integer id;

    private String planUuid;

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
	 * 客户姓名
	 */
	private String custName;

	/**
	 * 客户所属单位
	 */
	private String custCompany;

    private Integer fileRecordId;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlanUuid() {
		return planUuid;
	}

	public void setPlanUuid(String planUuid) {
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
		this.phone = phone;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
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
		this.recallParams = recallParams;
	}

	public String getRobot() {
		return robot;
	}

	public void setRobot(String robot) {
		this.robot = robot;
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
		this.result = result;
	}

	public String getCallAgent() {
		return callAgent;
	}

	public void setCallAgent(String callAgent) {
		this.callAgent = callAgent;
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
		this.callHour = callHour;
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
		this.username = username;
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
		this.lineName = lineName;
	}

	public String getRobotName() {
		return robotName;
	}

	public void setRobotName(String robotName) {
		this.robotName = robotName;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getFileRecordId() {
		return fileRecordId;
	}

	public void setFileRecordId(Integer fileRecordId) {
		this.fileRecordId = fileRecordId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustCompany() {
		return custCompany;
	}

	public void setCustCompany(String custCompany) {
		this.custCompany = custCompany;
	}
}