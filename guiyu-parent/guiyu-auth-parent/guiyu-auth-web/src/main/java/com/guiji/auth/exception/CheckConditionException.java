package com.guiji.auth.exception;

public class CheckConditionException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public CheckConditionException(){}
	
	public CheckConditionException(String code){
		this.code=code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
