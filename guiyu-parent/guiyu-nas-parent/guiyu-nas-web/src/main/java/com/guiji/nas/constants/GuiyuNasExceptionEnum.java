/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.nas.constants;


import com.guiji.common.exception.ExceptionEnum;

/**
 * 影像系统异常码码定义枚举
 */
public enum GuiyuNasExceptionEnum implements ExceptionEnum {
	EXCP_NAS_QUERY_NULL("07000001","文件查询请求信息附件ID、业务ID不能都为空"),
	EXCP_NAS_UPLOAD_ERROR("07000002","文件上传失败！"),
	EXCP_NAS_DOWNLOAD_ERROR("07000003","文件下载失败！"),
	EXCP_NAS_UPLOAD_FILE_NULL("07000004","上传文件不能为空！"),
	EXCP_NAS_UPLOAD_FILE_SIZE_NOTNULL("07000005","上传文件大小不能为0！");

	//返回码
	private String errorCode;
	//返回信息
	private String msg;

	public String getName(){
		return this.name();
	}

	//根据枚举的code获取msg的方法
	public static GuiyuNasExceptionEnum getMsgByErrorCode(String errorCode){
		for(GuiyuNasExceptionEnum guiyuNasExceptionEnum : GuiyuNasExceptionEnum.values()) {
			if(guiyuNasExceptionEnum.getErrorCode().equals(errorCode)){
				return guiyuNasExceptionEnum;
			}
		}
		return null;
	}

	GuiyuNasExceptionEnum(String errorCode, String msg) {
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
  
