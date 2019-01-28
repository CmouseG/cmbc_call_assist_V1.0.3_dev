package com.guiji.entity;

import com.guiji.util.CommonUtil;
import lombok.Data;

@Data
public class AsrResponse extends BaseApi{
	private Boolean result;
	private Integer errorCode;

	private String id;
	private String content;
	private Integer asrDuration;

	private String cid;
	private String recordFilePath;
	private Long startStamp;
	private Long endStamp;
	private Long totalDuration;
	
	{
		this.api = "asr_response";
	}
	
	@Override
	public String toString() {
		return CommonUtil.beanToJson(this);
	}
}
