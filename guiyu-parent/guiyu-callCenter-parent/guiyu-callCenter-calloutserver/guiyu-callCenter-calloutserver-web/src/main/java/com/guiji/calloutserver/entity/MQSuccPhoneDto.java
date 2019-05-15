package com.guiji.calloutserver.entity;

public class MQSuccPhoneDto {

	private String planuuid;
	private String label;
	private Integer lineId;
	private String tempId;
	private Integer userId;
	private Boolean simLineIsOk;

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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Boolean getSimLineIsOk() {
		return simLineIsOk;
	}

	public void setSimLineIsOk(Boolean simLineIsOk) {
		this.simLineIsOk = simLineIsOk;
	}
}
