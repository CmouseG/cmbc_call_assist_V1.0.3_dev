package com.guiji.dispatch.enums;

public enum ExportFileStatusEnum {

	DOING(0,"进行中"),

	FINISH(1,"完成"),

	CANCEL(2,"取消"),

	DEL(3,"删除"),

	FAIL(4,"失败"),
	;
	private Integer status;

	private String message;

	private ExportFileStatusEnum(Integer status, String message) {
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
