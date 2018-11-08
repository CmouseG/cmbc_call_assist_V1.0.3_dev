package com.guiji.calloutserver.entity;

import com.guiji.calloutserver.util.CommonUtil;
import lombok.Data;

@Data
public class BaseApi {
	protected String api;
	protected String data;

	public String toString(){
		return CommonUtil.beanToJson(this);
	}
}
