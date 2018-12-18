package com.guiji.user.vo;

public class SysUserVo {

	private Long id;

    private String username;

    private String password;

    private Integer status;

    private String pushType;

    private String intenLabel;

    private String orgCode;

    private String createTime;

    private String  vaildTime;
    
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

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVaildTime() {
		return vaildTime;
	}

	public void setVaildTime(String vaildTime) {
		this.vaildTime = vaildTime;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
    
}
