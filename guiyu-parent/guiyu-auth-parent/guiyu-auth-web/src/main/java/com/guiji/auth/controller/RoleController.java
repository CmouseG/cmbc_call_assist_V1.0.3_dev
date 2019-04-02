package com.guiji.auth.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.service.RoleService;
import com.guiji.auth.service.UserService;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.vo.RoleParamVo;
import com.guiji.utils.StrUtils;

@RestController
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	@Jurisdiction("system_role_add")
	@RequestMapping("insert")
	public void insert(SysRole role,String[] menuIds,@RequestHeader Long userId,@RequestHeader String orgCode) throws Exception{
		if(roleService.existRoleName(role)){
			throw new GuiyuException("角色名已存在，请更换角色名！");
		}
		role.setCreateId(userId);
		role.setUpdateId(userId);
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		role.setDelFlag(0);
		roleService.insert(role,orgCode,menuIds);
	}
	
	@Jurisdiction("system_role_delete")
	@RequestMapping("delete")
	public void delete(Long id, @RequestHeader Long userId)
	{
		List<SysUser> users = userService.queryUserByRoleId(id.intValue());
		if (users != null && users.size() > 0){
			throw new GuiyuException("该角色下存在绑定用户，请先将用户解绑再删除！");
		}
		roleService.delete(id, userId);
	}
	
	@Jurisdiction("system_role_edit")
	@RequestMapping("update")
	public void update(SysRole role,String[] menuIds,@RequestHeader Long userId,@RequestHeader String orgCode) throws CheckConditionException{
		role.setUpdateId(userId);
		role.setUpdateTime(new Date());
		roleService.update(role,orgCode,menuIds);
	}
	
	@RequestMapping("getRoleById")
	public SysRole getRoleId(Long id){
		return roleService.getRoleId(id);
	}
	
	@RequestMapping("getRoles")
	public List<SysRole> getRoles(){
		return roleService.getRoles();
	}
	
	@RequestMapping("/getRoleByPage")
	public Page<Object> getRoleByPage(RoleParamVo param){
		return roleService.getRoleByPage(param);
	}
	
	@RequestMapping("getRoleByName")
	public List<SysRole> getRoleByName(String name){
		return roleService.getRoleByName(name);
	}
	
	
	/**
	 * 根据orgCode查询角色
	 * @param orgCode
	 * @return
	 */
	@RequestMapping("queryRoleByOrgCode")
	public List<SysRole> queryRoleByOrgCode(String orgCode){
		if(StrUtils.isNotEmpty(orgCode)) {
			return roleService.getRolesByOrg(orgCode);
		}
		return null;
	}
}
