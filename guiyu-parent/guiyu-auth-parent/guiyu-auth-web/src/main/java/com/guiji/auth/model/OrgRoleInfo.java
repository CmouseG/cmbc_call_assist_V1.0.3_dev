package com.guiji.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 
* @ClassName: OrgRoleInfo 
* @Description: 组织角色信息
* @auth weiyunbo
* @date 2019年3月15日 下午4:46:13 
* @version V1.0  
*/
public class OrgRoleInfo {
	
	//主键ID
	private Long id;
	//名称
	private String name;
	//企业
	private String orgCode;
	//类型：1-企业  2-角色
	private Integer type;
	
	/**角色信息**/
	private String desc;
	//创建人
	private String createId;
	//创建人
	private String createName;
	//创建时间
    private Date createTime;
    //最后更新人
    private Long updateId;
    //最后更新人
  	private String updateName;
    //更新时间
    private Date updateTime;
    //数据查询权限
    private Integer dataAuthLevel;
    
    //下级
  	private List<OrgRoleInfo> children;
	
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
	public String getDesc()
	{
		return desc;
	}
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public String getCreateId()
	{
		return createId;
	}
	public void setCreateId(String createId)
	{
		this.createId = createId;
	}
	public String getCreateName()
	{
		return createName;
	}
	public void setCreateName(String createName)
	{
		this.createName = createName;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public Long getUpdateId()
	{
		return updateId;
	}
	public void setUpdateId(Long updateId)
	{
		this.updateId = updateId;
	}
	public String getUpdateName()
	{
		return updateName;
	}
	public void setUpdateName(String updateName)
	{
		this.updateName = updateName;
	}
	public Date getUpdateTime()
	{
		return updateTime;
	}
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	public Integer getDataAuthLevel()
	{
		return dataAuthLevel;
	}
	public void setDataAuthLevel(Integer dataAuthLevel)
	{
		this.dataAuthLevel = dataAuthLevel;
	}
	public List<OrgRoleInfo> getChildren()
	{
		return children;
	}
	public void setChildren(List<OrgRoleInfo> children)
	{
		this.children = children;
	}
	public void addChild(OrgRoleInfo child)
	{
		if(children == null){
			children = new ArrayList<OrgRoleInfo>();
		}
		children.add(child);
	}
}
