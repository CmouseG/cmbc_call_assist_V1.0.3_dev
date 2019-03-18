package com.guiji.dispatch.enums;

public enum IsNotifyMsgEnum {

	HAVING("0","未通知"),

	NO("1","已通知"),
	;
	private String flag;

	private String message;

	private IsNotifyMsgEnum(String flag, String message) {
		this.flag = flag;
		this.message = message;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
