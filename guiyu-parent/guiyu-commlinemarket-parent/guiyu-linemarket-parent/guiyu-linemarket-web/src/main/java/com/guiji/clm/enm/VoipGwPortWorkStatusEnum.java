package com.guiji.clm.enm;

/** 
* @Description: 语音网关端口基站连接状态
* @Author: weiyunbo
* @date 2019年1月22日 下午9:56:40 
* @version V1.0  
*/
public enum VoipGwPortWorkStatusEnum{
	WORKING("工作中", 1), 
	DISABLED("不可用", 2);
	
	private String name;  
    private int code;
	private VoipGwPortWorkStatusEnum(String name, int code) {
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
