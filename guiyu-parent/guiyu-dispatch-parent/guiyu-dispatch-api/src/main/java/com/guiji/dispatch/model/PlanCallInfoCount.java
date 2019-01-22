package com.guiji.dispatch.model;

import com.guiji.common.model.Page;

public class PlanCallInfoCount {
	private int succCount;
	private int planCount;
	private Page<FileErrorRecords> errorRecordsList;
	
	
	
	public Page<FileErrorRecords> getErrorRecordsList() {
		return errorRecordsList;
	}
	public void setErrorRecordsList(Page<FileErrorRecords> errorRecordsList) {
		this.errorRecordsList = errorRecordsList;
	}
	public int getSuccCount() {
		return succCount;
	}
	public void setSuccCount(int succCount) {
		this.succCount = succCount;
	}
	public int getPlanCount() {
		return planCount;
	}
	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}
	
	
}
