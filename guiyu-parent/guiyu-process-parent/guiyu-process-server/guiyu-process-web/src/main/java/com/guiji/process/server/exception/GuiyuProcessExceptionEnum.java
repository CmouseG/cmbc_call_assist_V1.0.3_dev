/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.process.server.exception;


import com.guiji.common.exception.ExceptionEnum;

/**
 * 影像系统异常码码定义枚举
 */
public enum GuiyuProcessExceptionEnum implements ExceptionEnum {
	EXCP_PROCESS_MODEL_NULL("08000001","获取指定模型TTS资源时，模型不能为空"),
	PROCESS08000002("08000002","命令正在执行中,请稍后再试");

	//返回码
	private String errorCode;
	//返回信息
	private String msg;

	public String getName(){
		return this.name();
	}

	//根据枚举的code获取msg的方法
	public static GuiyuProcessExceptionEnum getMsgByErrorCode(String errorCode){
		for(GuiyuProcessExceptionEnum guiyuProcessExceptionEnum : GuiyuProcessExceptionEnum.values()) {
			if(guiyuProcessExceptionEnum.getErrorCode().equals(errorCode)){
				return guiyuProcessExceptionEnum;
			}
		}
		return null;
	}

	GuiyuProcessExceptionEnum(String errorCode, String msg) {
		this.errorCode = errorCode;
		this.msg = msg;
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
  
