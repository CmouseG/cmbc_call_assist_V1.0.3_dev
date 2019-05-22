package com.guiji.clm.enm;

/** 
* @Description: SIP线路状态
* @Author: weiyunbo
* @date 2019年1月22日 下午9:56:40 
* @version V1.0  
*/
public enum SipLineStatusEnum {
	INIT("新增初始化", 0), 
	OK("正常", 1), 
	EXPIRE("到期", 2), 
	INVALID("失效/删除", 3);
	
	private String name;  
    private int code;
	private SipLineStatusEnum(String name, int code) {
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
