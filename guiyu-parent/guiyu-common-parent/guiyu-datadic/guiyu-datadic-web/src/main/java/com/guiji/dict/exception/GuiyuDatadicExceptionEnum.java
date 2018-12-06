/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.dict.exception;


import com.guiji.common.exception.ExceptionEnum;

/**
 * 影像系统异常码码定义枚举
 */
public enum GuiyuDatadicExceptionEnum implements ExceptionEnum {
	DATADIC00020001("00020001","字典名称长度不能超过20个字符"),
	DATADIC00020002("00020002","字典KEY长度不能超过20个字符"),
	DATADIC00020003("00020003","字典值长度不能超过255个字符"),
	DATADIC00020004("00020004","字典描述长度不能超过255个字符");

	//返回码
	private String errorCode;
	//返回信息
	private String msg;

	public String getName(){
		return this.name();
	}

	//根据枚举的code获取msg的方法
	public static GuiyuDatadicExceptionEnum getMsgByErrorCode(String errorCode){
		for(GuiyuDatadicExceptionEnum guiyuDatadicExceptionEnum : GuiyuDatadicExceptionEnum.values()) {
			if(guiyuDatadicExceptionEnum.getErrorCode().equals(errorCode)){
				return guiyuDatadicExceptionEnum;
			}
		}
		return null;
	}

	GuiyuDatadicExceptionEnum(String errorCode, String msg) {
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
  
