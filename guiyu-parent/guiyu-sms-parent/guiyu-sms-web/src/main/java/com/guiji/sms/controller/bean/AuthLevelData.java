package com.guiji.sms.controller.bean;

import lombok.Data;

@Data
public class AuthLevelData
{
	private Long userId;
	private Integer authLevel;
	private String orgCode;
	
	public AuthLevelData(Long userId, Integer authLevel, String orgCode)
	{
		this.userId = userId;
		this.authLevel = authLevel;
		this.orgCode = orgCode;
	}
	
	public AuthLevelData()
	{}
	
}
