package com.guiji.calloutserver.entity;

import com.guiji.calloutserver.util.CommonUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class AsrRequest extends BaseApi implements Serializable{
	private String cid;
	private String recordFilePath;
	private Long startStamp;
	
	{
		this.api = "asr_request";
	}
	
	@Override
	public String toString() {
		return CommonUtil.beanToJson(this);
	}
}
