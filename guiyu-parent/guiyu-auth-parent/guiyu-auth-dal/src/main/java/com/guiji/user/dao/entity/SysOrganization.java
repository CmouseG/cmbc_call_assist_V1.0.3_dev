package com.guiji.user.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysOrganization implements Serializable 
{
	private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String code;
    private String subCode;
    private Integer type;
    private Integer robot;
    private String startDate;
    private String endDate;
    private Integer line;
    private Integer botstence;
	private Integer open;
    private Integer delFlag;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;    
    private String createName;
    private String updateName;
    private String parentName;
    private List<Integer> product;
    private List<String> industryIds;
    private List<String> menuIds;
    private Integer usable;   
    
    private String effectiveDate;
    private String invalidDate;
    
    private Integer bussinessId;

    public Integer getBussinessId()
	{
		return bussinessId;
	}

	public void setBussinessId(Integer bussinessId)
	{
		this.bussinessId = bussinessId;
	}

	public String getEffectiveDate()
	{
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	public String getInvalidDate()
	{
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate)
	{
		this.invalidDate = invalidDate;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public List<String> getMenuIds()
	{
		return menuIds;
	}

	public void setMenuIds(List<String> menuIds)
	{
		this.menuIds = menuIds;
	}

	public Integer getUsable()
	{
		return usable;
	}

	public void setUsable(Integer usable)
	{
		this.usable = usable;
	}
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRobot() {
        return robot;
    }

    public void setRobot(Integer robot) {
        this.robot = robot;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

    public Integer getBotstence() {
        return botstence;
    }

    public void setBotstence(Integer botstence) {
        this.botstence = botstence;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public List<Integer> getProduct() {
        return product;
    }

    public void setProduct(List<Integer> product) {
        this.product = product;
    }

    public List<String> getIndustryIds() {
        return industryIds;
    }

    public void setIndustryIds(List<String> industryIds) {
        this.industryIds = industryIds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", type=").append(type);
        sb.append(", robot=").append(robot);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", line=").append(line);
        sb.append(", botstence=").append(botstence);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", open=").append(open);
        sb.append(", createId=").append(createId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateId=").append(updateId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", subCode=").append(subCode);
        sb.append(", product=").append(product);
        sb.append(", industryIds=").append(industryIds);
        sb.append(", usable=").append(usable);
		sb.append(", menuIds=").append(menuIds);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}