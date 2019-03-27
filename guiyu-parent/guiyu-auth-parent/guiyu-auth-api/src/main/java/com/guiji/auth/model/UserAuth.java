package com.guiji.auth.model;

/** 
* @ClassName: UseAuth 
* @Description: 用户数据查询权限
* @auth weiyunbo
* @date 2019年3月15日 下午1:37:45 
* @version V1.0  
*/
public class UserAuth {
	//用户ID
	private Integer userId;
	//用户数据查询权限级别（1-本人;2-本组织;3-本组织及下级组织）
	private Integer authLevel;
	//用户所属企业编号
	private String orgCode;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAuthLevel() {
		return authLevel;
	}
	public void setAuthLevel(Integer authLevel) {
		this.authLevel = authLevel;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Override
	public String toString() {
		return "UseAuth [userId=" + userId + ", authLevel=" + authLevel + ", orgCode=" + orgCode + "]";
	}
}
