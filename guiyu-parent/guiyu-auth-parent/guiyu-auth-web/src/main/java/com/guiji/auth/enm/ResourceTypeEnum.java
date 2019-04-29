package com.guiji.auth.enm;

/** 
* @Description: 资源类型
* @Author: weiyunbo
* @date 2019年3月10日 下午9:56:40 
* @version V1.0  
*/
public enum ResourceTypeEnum{
	MENU("菜单/按钮", 1), 
	TRADE("行业", 2);
	
	private String name;  
    private int code;
	private ResourceTypeEnum(String name, int code) {
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
