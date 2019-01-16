package com.guiji.dispatch.bean;

public class MQSuccPhoneDto {
	private String planuuid;
	private String label;
	private Integer userId;
	private Integer lineId;
	private String tempId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPlanuuid() {
		return planuuid;
	}
	public void setPlanuuid(String planuuid) {
		this.planuuid = planuuid;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
}
