/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.auth.exception;


import com.guiji.common.exception.ExceptionEnum;

/**
 * 影像系统异常码码定义枚举
 */
public enum GuiyuAuthExceptionEnum implements ExceptionEnum {
	AUTH00010011("00010011","公告标题不能为空"),
	AUTH00010012("00010012","公告标题长度不能超过150个字符"),
	AUTH00010013("00010013","公告内容不能超过200个字符"),
	AUTH00010014("00010014","公告含样式内容不能超过3000个字符"),
	AUTH00010015("00010015","公告对象不能为空"),
	AUTH00010016("00010016","用户名和密码不能为空"),
	AUTH00010017("00010017","行业id，行业名称，产品id，产品名称都不能为空");

	//返回码
	private String errorCode;
	//返回信息
	private String msg;

	public String getName(){
		return this.name();
	}

	//根据枚举的code获取msg的方法
	public static GuiyuAuthExceptionEnum getMsgByErrorCode(String errorCode){
		for(GuiyuAuthExceptionEnum guiyuAuthExceptionEnum : GuiyuAuthExceptionEnum.values()) {
			if(guiyuAuthExceptionEnum.getErrorCode().equals(errorCode)){
				return guiyuAuthExceptionEnum;
			}
		}
		return null;
	}

	GuiyuAuthExceptionEnum(String errorCode, String msg) {
		this.errorCode = errorCode;
		this.msg = msg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMsg() {
		return msg;
	}


}
  
