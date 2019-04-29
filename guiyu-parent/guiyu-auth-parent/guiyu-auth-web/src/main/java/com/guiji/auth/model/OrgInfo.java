package com.guiji.auth.model;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: OrgRoleInfo 
* @Description: 组织角色信息
* @auth weiyunbo
* @date 2019年3月15日 下午4:46:13 
* @version V1.0  
*/
public class OrgInfo {
	
	//主键ID
	private Long id;
	//名称
	private String name;
	//企业
	private String orgCode;
	//类型：1-企业  2-角色
	private Integer type;
	
	//下级
	private List<RoleInfo> roleChildren;
	//下级
	private List<OrgInfo> orgChildren;
	
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getOrgCode()
	{
		return orgCode;
	}
	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}
	public Integer getType()
	{
		return type;
	}
	public void setType(Integer type)
	{
		this.type = type;
	}
	public List<RoleInfo> getRoleChildren()
	{
		return roleChildren;
	}
	public void setRoleChildren(List<RoleInfo> roleChildren)
	{
		this.roleChildren = roleChildren;
	}
	
	public List<OrgInfo> getOrgChildren()
	{
		return orgChildren;
	}
	
	public void setOrgChildren(List<OrgInfo> orgChildren)
	{
		this.orgChildren = orgChildren;
	}
	
	public void addOrgChild(OrgInfo child)
	{
		if(orgChildren == null){
			orgChildren = new ArrayList<OrgInfo>();
		}
		orgChildren.add(child);
	}
	
	public void addRoleChild(RoleInfo child)
	{
		if(roleChildren == null){
			roleChildren = new ArrayList<RoleInfo>();
		}
		roleChildren.add(child);
	}
	
}
