package com.guiji.auth.model;

import java.util.Date;

import lombok.Data;

@Data
public class UpdateOrgReq
{
	private Integer orgId;
	private Integer superAgentId;
	private Integer type;
	private String code;
	private Integer oldMachineNum;
	private String oldStartDate;
	private String oldEndDate;
	private Integer newMachineNum;
	private String newStartDate;
	private String newEndDate;
	private Long userId;
	private Date saveTime;
	
	public UpdateOrgReq(Integer orgId, Integer superAgentId, Integer type, String code, Integer oldMachineNum,
			String oldStartDate, String oldEndDate, Integer newMachineNum, String newStartDate, String newEndDate,
			Long userId, Date saveTime)
	{
		this.orgId = orgId;
		this.superAgentId = superAgentId;
		this.type = type;
		this.code = code;
		this.oldMachineNum = oldMachineNum;
		this.oldStartDate = oldStartDate;
		this.oldEndDate = oldEndDate;
		this.newMachineNum = newMachineNum;
		this.newStartDate = newStartDate;
		this.newEndDate = newEndDate;
		this.userId = userId;
		this.saveTime = saveTime;
	}

	public UpdateOrgReq()
	{}
}
