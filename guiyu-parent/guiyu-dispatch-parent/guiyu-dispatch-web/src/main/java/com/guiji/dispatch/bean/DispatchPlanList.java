package com.guiji.dispatch.bean;

import java.util.List;

import com.guiji.dispatch.dao.entity.DispatchPlan;


public class DispatchPlanList {
	private String batchName;
	private String robot;
	private String line;
	private String isClean;
	private String callHour;
	private String callDate;
	private List<DispatchPlan> mobile;

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
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

	public List<DispatchPlan> getMobile() {
		return mobile;
	}

	public void setMobile(List<DispatchPlan> mobile) {
		this.mobile = mobile;
	}

	
	
	
	
	
	
	
}
