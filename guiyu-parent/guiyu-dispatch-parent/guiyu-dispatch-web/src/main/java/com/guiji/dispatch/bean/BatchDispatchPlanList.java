package com.guiji.dispatch.bean;

import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlan;

import java.util.List;


public class BatchDispatchPlanList {
	private String batchName;
	private String robot;
	private List<DispatchBatchLine> lines;
	private String clean;
	private String callHour;
	private String callDate;
	private String robotName;
	private String lineName;
	private List<DispatchPlan> mobile;
	private Integer lineType;


	public List<DispatchBatchLine> getLines() {
		return lines;
	}

	public void setLines(List<DispatchBatchLine> lines) {
		this.lines = lines;
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

	
	public String getRobotName() {
		return robotName;
	}

	public void setRobotName(String robotName) {
		this.robotName = robotName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public void setRobot(String robot) {
		this.robot = robot;
	}


	public String getClean() {
		return clean;
	}

	public void setClean(String clean) {
		this.clean = clean;
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


	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}
}
