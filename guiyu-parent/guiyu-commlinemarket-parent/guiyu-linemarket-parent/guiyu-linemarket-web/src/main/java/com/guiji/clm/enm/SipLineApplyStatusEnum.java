package com.guiji.clm.enm;

/** 
* @Description: SIP线路申请进度类型
* @Author: weiyunbo
* @date 2019年1月22日 下午9:56:40 
* @version V1.0  
*/
public enum SipLineApplyStatusEnum{
	ING("申请中", 1), 
	APPROVE("审批通过", 2), 
	REJECT("审批拒绝", 3);
	
	private String name;  
    private int code;
	private SipLineApplyStatusEnum(String name, int code) {
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
