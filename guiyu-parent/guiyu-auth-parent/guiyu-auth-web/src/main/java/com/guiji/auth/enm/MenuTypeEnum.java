package com.guiji.auth.enm;

/** 
* @Description: 菜单类型
* @Author: weiyunbo
* @date 2019年3月10日 下午9:56:40 
* @version V1.0  
*/
public enum MenuTypeEnum{
	MENU("菜单", 1), 
	BUTTON("按钮", 2); 
	
	private String name;  
    private int code;
	private MenuTypeEnum(String name, int code) {
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
