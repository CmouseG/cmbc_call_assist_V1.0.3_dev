package com.guiji.clm.enm;

/** 
* @Description: 语音网关注册状态
* @Author: weiyunbo
* @date 2019年1月22日 下午9:56:40 
* @version V1.0  
*/
public enum VoipGwRegStatusEnum{
	INIT("初始化", 0), 
	CONFIRM("确认", 1);
	
	private String name;  
    private int code;
	private VoipGwRegStatusEnum(String name, int code) {
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
