package com.guiji.dispatch.enums;

public enum BatchNotifyStatusEnum {

	WAITING(1,"等待"),

	FAIL(2,"失败"),

	SUCCESS(3, "成功"),

	STOP(4, "停止"),
	;
	private Integer status;

	private String message;

	private BatchNotifyStatusEnum(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
