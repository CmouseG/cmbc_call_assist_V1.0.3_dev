package com.guiji.clm.enm;

/** 
* @Description: SIP线路路由状态
* @Author: weiyunbo
* @date 2019年1月30日 下午9:56:40 
* @version V1.0  
*/
public enum SipRouteRuleTypeEnum{
	PRI("优先级", 1);
	
	private String name;  
    private int code;
	private SipRouteRuleTypeEnum(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public int getCode() {
		return code;
	}

}
