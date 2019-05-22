package com.guiji.clm.enm;

/** 
* @Description: 语音网关注册类型
* @Author: weiyunbo
* @date 2019年1月22日 下午9:56:40 
* @version V1.0  
*/
public enum VoipGwRegTypeStatusEnum{
	reverse("反向注册(gw-fs)", 1), 
	forward("正向注册(fs-gw)", 2);
	
	private String name;  
    private int code;
	private VoipGwRegTypeStatusEnum(String name, int code) {
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
