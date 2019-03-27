package com.guiji.auth.model;


import com.guiji.user.dao.entity.SysOrganization;

import lombok.Data;

/** 
* @ClassName: OrganizationVO 
* @Description: 组织信息显示
* @auth weiyunbo
* @date 2019年3月20日 下午1:05:08 
* @version V1.0  
*/
@Data
public class OrganizationVO extends SysOrganization{
	//创建人
	private String createName;
	//最后更新人
	private String updateName;
	//父组织编号
	private String parentOrgCode;
}
