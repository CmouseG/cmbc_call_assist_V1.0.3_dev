package com.guiji.entity;

import com.guiji.common.exception.ExceptionEnum;

/**
 * 自定义异常枚举
 * @author Sun
 *
 */
public enum SmsExceptionEnum implements ExceptionEnum
{

	UNKNOW_ERROR("1000000","系统错误"),
	Incorrect_Format("1000001","上传文件格式不正确"),
	ParseFile_Error("1000002","解析文件失败"),
	PhoneNum_Error("1000003","手机号不匹配"),
	;
	
	private String errorCode;
	private String msg;
	
	//构造
	SmsExceptionEnum(String errorCode, String msg)
	{
		this.errorCode = errorCode;
		this.msg = msg;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	@Override
	public String getName()
	{
		return this.name();
	}
}
