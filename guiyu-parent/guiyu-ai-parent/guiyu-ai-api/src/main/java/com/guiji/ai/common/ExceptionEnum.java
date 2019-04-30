package com.guiji.ai.common;

/**
 * 自定义异常枚举
 * @author Sun
 *
 */
public enum ExceptionEnum
{
	UNKNOW_ERROR("-99","未知错误"),
	ERROR_REQUEST_TTS("-1","请求TTS-2.0失败"),
	;
	
	private String code;
	private String msg;
	
	//构造
	ExceptionEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
