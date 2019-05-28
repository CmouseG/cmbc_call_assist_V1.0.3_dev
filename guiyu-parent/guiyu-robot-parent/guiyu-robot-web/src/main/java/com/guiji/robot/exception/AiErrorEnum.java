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
	AI00060002("00060002","没有空闲机器人"),
	AI00060003("00060003","用户没有配置机器人拆分数据"),
	AI00060004("00060004","用户资源变更中，不能分配机器人"),
	AI00060005("00060005","机器人资源分配数量分配异常"),
	AI00060006("00060006","机器人不存在"),
	AI00060007("00060007","机器人资源动态申请异常"),
	AI00060008("00060008","调用进程管理申请机器人资源异常，无可用机器人"),
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
	AI00060020("00060020","调用SELLBOT接口异常..请联系管理员"),
	AI00060021("00060021","机器人配置数量超过了用户可用机器人数量"),
	AI00060022("00060022","机器人线路拆分配置保存系统异常"),
	AI00060023("00060023","机器人线路删除配置系统异常"),
	AI00060024("00060024","用户已经配置了线路拆分，变更机器人总数，请先手工删除配置好的线路拆分信息"),
	AI00060025("00060025","机器人分配校验异常"),
	AI00060026("00060026","话术模板文件不存在"),
	AI00060027("00060027","机器分配并发锁,未获取到锁,稍微重试"),
	AI00060028("00060028","机器分配未知异常,请联系管理员"),
	AI00060029("00060029","调用TTS接口发生异常"),
	AI00060030("00060030","无需TTS合成"),
	AI00060031("00060031","用户企业信息查询失败"),
	AI00060032("00060032","用户本次配置的机器人数量超过了企业下可用机器人数量"),
	AI00060033("00060033","机器人资源池初始化异常"),
	AI00060034("AI00060034","机器人资源池初始化并发锁,未获取到锁,稍微重试"),
	AI00060035("AI00060035","用户无该模板机器人配置或者超过了该模板配置的机器人并发上限"),
	AI00060036("AI00060036","调用飞龙接口异常..请联系管理员"),
	AI00060037("AI00060037","调用sellbot接口异常..请联系管理员"),
	AI00060038("AI00060038","挂断会话与当前机器人会话id不一致，挂断失败"),
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
	 * @return the errorMsg 
	 */
	public String getErrorMsg() {
	
		return errorMsg;
	}

}
  
