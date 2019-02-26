package com.guiji.dispatch.enums;

public enum SysDelEnum {
    
	NORMAL(0,"正常"),
	
	DEL(1,"删除"),
	;
	private Integer state;
	
	private String message;

	private SysDelEnum(Integer state, String message) {
		this.state = state;
		this.message = message;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
