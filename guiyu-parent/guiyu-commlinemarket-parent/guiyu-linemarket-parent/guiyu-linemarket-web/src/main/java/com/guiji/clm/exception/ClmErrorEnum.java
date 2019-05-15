/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.clm.exception;

/** 
 *@Description: 异常码定义枚举
 *@Author:weiyunbo
 *@history:
 *@Version:v1.0 
 */
public enum ClmErrorEnum {
	C00060001("00060001","必输字段校验失败!"),
	CLM1809303("CLM1809303","超过了SIP线路最大并发量!"),
	CLM1809304("CLM1809304","网关名称已经存在!"),
	CLM1809305("CLM1809305","查询用户机器人配置信息异常!"),
	CLM1809306("CLM1809306","申请线路并发数不能大于机器人数量!"),
	CLM1809307("CLM1809307","请先给企业配置机器人!"),
	CLM1809308("CLM1809308","呼叫中心变更线路异常!"),
	CLM1809309("CLM1809309","查询话术市场异常!"),
	CLM1809310("CLM1809310","线路仍在调度使用,不能删除!"),
	CLM1809311("CLM1809311","用户路由规则保存异常!"),
	CLM1809312("CLM1809312","用户路由规则未能获取到资源锁!"),
	CLM1809313("CLM1809313","共享线路已经生效不能修改/删除!"),
	CLM1809314("CLM1809314","初始化VOIP网关设备异常!"),
	CLM1809315("CLM1809315","初始化VOIP网关设备未能获取到资源锁!"),
	CLM1809316("CLM1809316","网关不存在!,不能删除!"),
	CLM1809317("CLM1809317","企业不存在!"),
	CLM1809318("CLM1809318","线路名称重复!"),
	CLM1809319("CLM1809319","sim线路不存在!"),
	CLM1809320("CLM1809320","sim线路查询状态异常!"),
    ERROR("ERROR","ERROR");
	
	//返回码
    private String errorCode;  
    //返回信息
    private String errorMsg;  
    private ClmErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;  
        this.errorMsg = errorMsg;  
    }  
    //根据枚举的code获取msg的方法  
    public static String getMsgByCode(String errorCode){  
        for(ClmErrorEnum responseEnum : ClmErrorEnum.values()) {  
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
  
