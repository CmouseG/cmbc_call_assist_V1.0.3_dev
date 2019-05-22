package com.guiji.clm.enm;

/** 
* @Description: SIP线路类型
* @Author: weiyunbo
* @date 2019年1月22日 下午9:56:40 
* @version V1.0  
*/
public enum SipLineTypeEnum{
	SELF("自营线路", 1), 
	AGENT("代理线路", 2);
	
	private String name;  
    private int code;
	private SipLineTypeEnum(String name, int code) {
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
