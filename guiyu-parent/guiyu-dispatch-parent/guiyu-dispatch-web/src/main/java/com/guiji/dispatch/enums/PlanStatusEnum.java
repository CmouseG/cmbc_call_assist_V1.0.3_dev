package com.guiji.dispatch.enums;

public enum PlanStatusEnum {

	DOING(1,"进行中"),

	FINISH(2,"完成"),

	SUSPEND(3,"暂停"),

	STOP(4,"停止"),
	;
	private Integer status;

	private String message;

	private PlanStatusEnum(Integer status, String message) {
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
