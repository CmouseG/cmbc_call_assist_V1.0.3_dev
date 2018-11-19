package com.guiji.ai.tts.constants;

import com.guiji.common.exception.ExceptionEnum;

public enum GuiyuAIExceptionEnum implements ExceptionEnum{

	EXCP_AI_TRANSFER_ERROR("0500001","语音合成失败");
	
	//返回码
	private String errorCode;
	//返回信息
	private String msg;
	
	GuiyuAIExceptionEnum(String errorCode, String msg) {
		this.errorCode = errorCode;
		this.msg = msg;
	}
	
	//根据枚举的code获取msg的方法
	public static GuiyuAIExceptionEnum getMsgByErrorCode(String errorCode){
		for(GuiyuAIExceptionEnum guiyuNasExceptionEnum : GuiyuAIExceptionEnum.values()) {
			if(guiyuNasExceptionEnum.getErrorCode().equals(errorCode)){
				return guiyuNasExceptionEnum;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return this.name();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
