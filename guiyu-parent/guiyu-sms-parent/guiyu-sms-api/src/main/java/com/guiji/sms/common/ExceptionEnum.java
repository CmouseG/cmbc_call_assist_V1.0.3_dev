package com.guiji.sms.common;

/**
 * 自定义异常枚举
 * @author Sun
 *
 */
public enum ExceptionEnum
{
	UNKNOW_ERROR("-99","未知异常"),
	ERROR_REQUEST_SMS("-1","请求短信平台商失败"),
	ERROR_FILE_TYPE("-2","上传文件类型错误"),
	ERROR_PARSE_EXCEL("-3","解析文件失败"),
	ERROR_PARSE_DATE("-4","解析日期失败"),
	ERROR_UNSUPPORTED_URLENCODE("-5","不支持URLEncode编码"),
	ERROR_XUANWU_GET_CONNECTION("-6","获取连接失败（玄武）"),
	ERROR_XUANWU_WRITE_RESPONSE("-7","获取响应失败（玄武）"),
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

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
