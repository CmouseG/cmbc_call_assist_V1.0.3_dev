package com.guiji.auth.model;

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
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the children
	 */
	public List<OrgRoleInfo> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<OrgRoleInfo> children) {
		this.children = children;
	}
	/**
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}
	/**
	 * @param createName the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateId
	 */
	public Long getUpdateId() {
		return updateId;
	}
	/**
	 * @param updateId the updateId to set
	 */
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the dataAuthLevel
	 */
	public Integer getDataAuthLevel() {
		return dataAuthLevel;
	}
	/**
	 * @param dataAuthLevel the dataAuthLevel to set
	 */
	public void setDataAuthLevel(Integer dataAuthLevel) {
		this.dataAuthLevel = dataAuthLevel;
	}
	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}
	/**
	 * @param createId the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	/**
	 * @return the updateName
	 */
	public String getUpdateName() {
		return updateName;
	}
	/**
	 * @param updateName the updateName to set
	 */
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
