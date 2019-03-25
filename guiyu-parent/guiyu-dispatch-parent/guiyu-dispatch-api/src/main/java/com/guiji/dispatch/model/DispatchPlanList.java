package com.guiji.dispatch.model;

import java.io.Serializable;
import java.util.List;


public class DispatchPlanList implements Serializable{
	private String batchName;
	private String robot;
	private String line;
	private String isClean;
	private String callHour;
	private String callDate;
	private String userId;
	private List<DispatchPlan> mobile;
	private List<DispatchBatchLine> lines;
	private Integer lineType;
	private Integer orgId;
    private static final long serialVersionUID = 1L;
	
    
    
	public List<DispatchBatchLine> getLines() {
		return lines;
	}

	public void setLines(List<DispatchBatchLine> lines) {
		this.lines = lines;
	}

	public List<DispatchPlan> getMobile() {
		return mobile;
	}

	public void setMobile(List<DispatchPlan> mobile) {
		this.mobile = mobile;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getRobot() {
		return robot;
	}

	public void setRobot(String robot) {
		this.robot = robot;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getIsClean() {
		return isClean;
	}

	public void setIsClean(String isClean) {
		this.isClean = isClean;
	}

	public String getCallHour() {
		return callHour;
	}

	public void setCallHour(String callHour) {
		this.callHour = callHour;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

//	public List<DispatchPlan> getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(List<DispatchPlan> mobile) {
//		this.mobile = mobile;
//	}

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
}
