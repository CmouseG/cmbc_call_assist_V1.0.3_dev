package com.guiji.clm.enm;

/** 
* @Description: SIP线路计费类型
* @Author: weiyunbo
* @date 2019年2月15日 下午9:56:40 
* @version V1.0  
*/
public enum SipLineFeeTypeEnum{
	DO_FEE("计费", 1), 
	UNDO_FEE("不计费（自备线路）", 2); 
	
	private String name;  
    private int code;
	private SipLineFeeTypeEnum(String name, int code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}
