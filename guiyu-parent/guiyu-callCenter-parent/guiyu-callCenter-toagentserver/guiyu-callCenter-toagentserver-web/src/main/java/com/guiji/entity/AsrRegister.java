package com.guiji.entity;

import com.guiji.util.CommonUtil;

public class AsrRegister extends BaseApi {
	private String id;
	
	{
		this.api = "asr_register";
	}
	
	public AsrRegister(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String toString(){
		return CommonUtil.beanToJson(this);
	}
}
