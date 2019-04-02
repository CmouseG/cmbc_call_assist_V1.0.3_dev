package com.guiji.dispatch.enums;

public enum SyncStatusEnum {

	NO_SYNC(0,"未推送redis"),

	ALREADY_SYNC(1,"已推送redis"),
	;
	private Integer status;

	private String message;

	private SyncStatusEnum(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
