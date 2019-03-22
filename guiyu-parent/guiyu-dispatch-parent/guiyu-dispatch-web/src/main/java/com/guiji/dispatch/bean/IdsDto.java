package com.guiji.dispatch.bean;

public class IdsDto {
	private String planuuid;
	private Integer status;
	private Integer batchid;
	private Integer orgId;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getBatchid() {
		return batchid;
	}
	public void setBatchid(Integer batchid) {
		this.batchid = batchid;
	}
	public String getPlanuuid() {
		return planuuid;
	}
	public void setPlanuuid(String planuuid) {
		this.planuuid = planuuid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
