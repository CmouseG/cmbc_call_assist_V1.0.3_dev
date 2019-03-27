package com.guiji.auth.enm;

/** 
* @Description: 授权对象类型
* @Author: weiyunbo
* @date 2019年3月10日 下午9:56:40 
* @version V1.0  
*/
public enum AuthObjTypeEnum{
	PRODUCT("产品", 1), 
	ORG("企业", 2), 
	ROLE("角色", 3), 
	USER("用户", 4), 
	INVALID("删除/失效", 0); 
	
	private String name;  
    private int code;
	private AuthObjTypeEnum(String name, int code) {
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
