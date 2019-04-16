package com.guiji.auth.model;

import java.util.Date;

import lombok.Data;

@Data
public class SaveOrgReq
{
	private Integer orgId;
	private Integer type;
	private String name;
	private String code;
	private String subCode;
	private Long userId;
	private Date saveTime;
	
	public SaveOrgReq(Integer orgId, Integer type, String name, String code, String subCode, Long userId, Date saveTime)
	{
		this.orgId = orgId;
		this.type = type;
		this.name = name;
		this.code = code;
		this.subCode = subCode;
		this.userId = userId;
		this.saveTime = saveTime;
	}

	public SaveOrgReq()
	{}
	
}
