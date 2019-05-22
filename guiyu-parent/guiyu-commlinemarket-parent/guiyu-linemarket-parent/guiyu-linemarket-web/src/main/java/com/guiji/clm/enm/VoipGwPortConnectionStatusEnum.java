package com.guiji.clm.enm;

/** 
* @Description: 语音网关端口基站连接状态
* @Author: weiyunbo
* @date 2019年1月22日 下午9:56:40 
* @version V1.0  
*/
public enum VoipGwPortConnectionStatusEnum{
	UNCONN("未连接", 0), 
	CONN("已连接", 1);
	
	private String name;  
    private int code;
	private VoipGwPortConnectionStatusEnum(String name, int code) {
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
