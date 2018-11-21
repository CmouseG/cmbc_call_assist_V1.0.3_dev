/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.robot.exception;  
  
  
/** 
 *@Description: 异常码定义枚举
 *@Author:weiyunbo
 *@history:
 *@Version:v1.0 
 */
public enum AiErrorEnum {
	AI00060001("00060001","必输字段校验失败"),
	AI00060002("00060002","用户没有空闲机器人"),
	AI00060003("00060003","用户无机器人资源"),
	AI00060004("00060004","用户资源变更中，不能分配机器人"),
	AI00060005("00060005","机器人资源分配数量分配异常"),
	AI00060006("00060006","机器人不存在"),
	AI00060007("00060007","机器人资源动态申请异常"),
	AI00060008("00060008","机器人资源申请异常，无可用机器人"),
	AI00060009("00060009","TTS合成校验失败，参数缺失"),
	AI00060010("00060010","TTS合成参数替换失败"),
	AI00060011("00060011","TTS合成语音缺失"),
	AI00060012("00060012","TTS合成语音落地失败"),
	AI00060013("00060013","本地WAV文件拼接失败"),
	AI00060014("00060014","上传NAS文件服务器失败"),
	AI00060015("00060015","合成WAV并上传NAS服务器发生未知异常"),
	AI00060016("00060016","读取本地话术模板文件异常"),
	AI00060017("00060017","TTS查无数据,请调用参数检查准备服务"),
	AI00060018("00060018","TTS合成中,稍后再试"),
	AI00060019("00060019","TTS合成失败,请重新调用参数检查准备服务或联系管理员"),
    ERROR("ERROR","ERROR");
	
	//返回码
    private String errorCode;  
    //返回信息
    private String errorMsg;  
    private AiErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;  
        this.errorMsg = errorMsg;  
    }  
    //根据枚举的code获取msg的方法  
    public static String getMsgByCode(String errorCode){  
        for(AiErrorEnum responseEnum : AiErrorEnum.values()) {  
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
  
