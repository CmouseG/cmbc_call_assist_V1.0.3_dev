package com.guiji.auth.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.common.model.Page;
import com.guiji.user.dao.SysRoleMapper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysRoleExample;
import com.guiji.user.vo.RoleParamVo;

@Service
public class RoleService {
	
	@Autowired
	private SysRoleMapper mapper;
	
	public void insert(SysRole role,String[] menuIds){
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		role.setInitRole(1);//接口增加的角色都是非初始化数据，只有初始化才是
		role.setSuperAdmin(1);//接口增加的角色都是非超级管理员，只有初始化才是
		mapper.insert(role);
		mapper.addMenus(role.getId(),menuIds);
	}
	
	public void delete(Long id){
		mapper.deleteByPrimaryKey(id);
	}
	
	public void update(SysRole role,String[] menuIds){
		role.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(role);
		mapper.addMenus(role.getId(),menuIds);
	}
	
	public SysRole getRoleId(Long id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<SysRole> getRoles(){
		return mapper.getRoles();
	}
	
	public Page<Object> getRoleByPage(RoleParamVo param){
		Page<Object> page=new Page<Object>();
		int count=mapper.countByParamVo(param);
		List<Object> list=mapper.selectByParamVo(param);
		page.setTotal(count);
		page.setRecords(list);
		return page;
	}
	
	public List<SysRole> getRoleByName(String name){
		SysRoleExample example = new SysRoleExample();
		example.createCriteria().andNameEqualTo(name);
		return mapper.selectByExample(example);
	} 
}
