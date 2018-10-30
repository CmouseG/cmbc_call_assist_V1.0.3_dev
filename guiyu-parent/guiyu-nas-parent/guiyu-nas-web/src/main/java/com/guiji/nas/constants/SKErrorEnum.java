/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.nas.constants;
  
  
/** 
 * 影像系统异常码码定义枚举
 */
public enum SKErrorEnum {
	QUERY_NULL("0700001","文件查询请求信息附件ID、业务ID不能都为空"),
	UPLOAD_ERROR("0700002","文件上传失败！"),
	DOWNLOAD_ERROR("0700003","文件下载失败！");
	
	//返回码
    private String errorCode;  
    //返回信息
    private String errorMsg;  
    private SKErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;  
        this.errorMsg = errorMsg;  
    }  
    //根据枚举的code获取msg的方法  
    public static String getMsgByCode(String errorCode){  
        for(SKErrorEnum responseEnum : SKErrorEnum.values()) {  
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
  
