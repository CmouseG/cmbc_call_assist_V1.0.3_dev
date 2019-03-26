package com.guiji.auth.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.service.RoleService;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.vo.RoleParamVo;
import com.guiji.utils.StrUtils;

@RestController
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService service;
	
	@Jurisdiction("system_role_add")
	@RequestMapping("insert")
	public void insert(SysRole role,String[] menuIds,@RequestHeader Long userId,@RequestHeader String orgCode) throws CheckConditionException{
		role.setCreateId(userId);
		role.setUpdateId(userId);
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		role.setDelFlag(0);
		service.insert(role,orgCode,menuIds);
	}
	
	@Jurisdiction("system_role_delete")
	@RequestMapping("delete")
	public void delete(Long id,@RequestHeader Long userId){
		service.delete(id,userId);
	}
	
	@Jurisdiction("system_role_update")
	@RequestMapping("update")
	public void update(SysRole role,String[] menuIds,@RequestHeader Long userId,@RequestHeader String orgCode) throws CheckConditionException{
		role.setUpdateId(userId);
		role.setUpdateTime(new Date());
		service.update(role,orgCode,menuIds);
	}
	
	@RequestMapping("getRoleById")
	public SysRole getRoleId(Long id){
		return service.getRoleId(id);
	}
	
	@RequestMapping("getRoles")
	public List<SysRole> getRoles(){
		return service.getRoles();
	}
	
	@RequestMapping("/getRoleByPage")
	public Page<Object> getRoleByPage(RoleParamVo param){
		return service.getRoleByPage(param);
	}
	
	@RequestMapping("getRoleByName")
	public List<SysRole> getRoleByName(String name){
		return service.getRoleByName(name);
	}
	
	
	/**
	 * 根据组织代码查询组织下角色
	 * @param orgCode
	 * @return
	 */
	@RequestMapping("queryRoleByOrgCode")
	public List<SysRole> queryRoleByOrgCode(String orgCode){
		if(StrUtils.isNotEmpty(orgCode)) {
			return service.getRolesByOrg(orgCode);
		}
		return null;
	}
}
