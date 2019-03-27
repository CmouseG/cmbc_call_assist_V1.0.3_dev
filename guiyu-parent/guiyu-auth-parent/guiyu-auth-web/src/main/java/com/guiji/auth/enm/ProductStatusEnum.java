package com.guiji.auth.enm;

/** 
* @Description: 产品状态
* @Author: weiyunbo
* @date 2019年3月10日 下午9:56:40 
* @version V1.0  
*/
public enum ProductStatusEnum{
	OK("正常", 1), 
	INVALID("删除/失效", 0); 
	
	private String name;  
    private int code;
	private ProductStatusEnum(String name, int code) {
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
