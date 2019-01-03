package com.guiji.user.vo;

public class SysUserVo {

	private Long id;

    private String username;

    private String password;

    private Integer status;

    private Integer pushType;

    private String intenLabel;

    private String orgCode;

    private String  vaildTime;

    private String startTime;
    
    private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public String getIntenLabel() {
		return intenLabel;
	}

	public void setIntenLabel(String intenLabel) {
		this.intenLabel = intenLabel;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getVaildTime() {
		return vaildTime;
	}

	public void setVaildTime(String vaildTime) {
		this.vaildTime = vaildTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
    
}
