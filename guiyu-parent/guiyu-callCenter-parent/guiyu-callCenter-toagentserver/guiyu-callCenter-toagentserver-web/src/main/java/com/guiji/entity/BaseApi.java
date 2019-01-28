package com.guiji.entity;

import com.guiji.util.CommonUtil;
import lombok.Data;

@Data
public class BaseApi {
	protected String api;
	protected String data;

	public String toString(){
		return CommonUtil.beanToJson(this);
	}
}
