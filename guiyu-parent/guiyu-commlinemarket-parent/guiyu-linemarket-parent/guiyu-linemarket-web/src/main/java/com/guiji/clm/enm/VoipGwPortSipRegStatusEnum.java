package com.guiji.clm.enm;

/** 
* @Description: SIP线路申请类型
* @Author: weiyunbo
* @date 2019年1月22日 下午9:56:40 
* @version V1.0  
*/
public enum VoipGwPortSipRegStatusEnum{
	NEW_LINE("新线路", 1), 
	BUSI_MERGE("业务数据变更", 2), 
	LINE_MERGE("线路变更", 3);
	
	private String name;  
    private int code;
	private VoipGwPortSipRegStatusEnum(String name, int code) {
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
