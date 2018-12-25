/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.da.exception;  
  
  
/** 
 *@Description: 异常码定义枚举
 *@Author:weiyunbo
 *@history:
 *@Version:v1.0 
 */
public enum DaErrorEnum {
	DA00060001("00060001","必输字段校验失败"),
    ERROR("ERROR","ERROR");
	
	//返回码
    private String errorCode;  
    //返回信息
    private String errorMsg;  
    private DaErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;  
        this.errorMsg = errorMsg;  
    }  
    //根据枚举的code获取msg的方法  
    public static String getMsgByCode(String errorCode){  
        for(DaErrorEnum responseEnum : DaErrorEnum.values()) {  
            if(responseEnum.getErrorCode().equals(errorCode)){  
                return responseEnum.errorMsg;  
            }  
        }  
        return null;  
    }
	/** 
	 * @return the errorCode 
	 */
	public String getErrorCode() {
	
		return errorCode;
	}
	/** 
	 @param errorCode the errorCode to set 
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/** 
	 * @return the errorMsg 
	 */
	public String getErrorMsg() {
	
		return errorMsg;
	}
	/** 
	 @param errorMsg the errorMsg to set 
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
  
