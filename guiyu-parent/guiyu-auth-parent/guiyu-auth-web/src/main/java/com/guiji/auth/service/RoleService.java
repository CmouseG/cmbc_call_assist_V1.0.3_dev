package com.guiji.auth.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.user.dao.SysRoleMapper;
import com.guiji.user.dao.SysRoleUserMapper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysRoleExample;
import com.guiji.user.dao.entity.SysRoleUser;
import com.guiji.user.dao.entity.SysRoleUserExample;
import com.guiji.user.dao.ext.SysRoleMapperExt;
import com.guiji.user.vo.RoleParamVo;
import com.guiji.utils.StrUtils;

@Service
public class RoleService {
	@Autowired
	PrivilegeService privilegeService;
	@Autowired
	private SysRoleMapper mapper;
	@Autowired
	private SysRoleMapperExt mapperExt;
	@Autowired
	private SysRoleUserMapper roleUserMapper;
	
	@Transactional
	public void insert(SysRole role,String orgCode,String[] menuIds){
		SysRoleExample example = new SysRoleExample();
		example.createCriteria().andNameEqualTo(role.getName()).andOrgCodeEqualTo(role.getOrgCode());
		int num = mapper.countByExample(example);
		if(num > 0){
			throw new GuiyuException("改角色名称已存在，请更换角色名称！");
		}
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		role.setInitRole(1);//接口增加的角色都是非初始化数据，只有初始化才是
		role.setSuperAdmin(1);//接口增加的角色都是非超级管理员，只有初始化才是
		mapper.insert(role);
//		mapperExt.addMenus(role.getId(),menuIds);
		//角色绑定菜单
		if(menuIds!=null && menuIds.length>0) {
			List<String> menuIdList = Arrays.asList(menuIds);
			privilegeService.savePrivlegeTree(null,role.getCreateId().intValue(), role.getOrgCode(), AuthObjTypeEnum.ROLE.getCode(), role.getId().toString(), ResourceTypeEnum.MENU.getCode(), menuIdList);
		}
	}
	
	@Transactional
	public void delete(Long id,Long userId){
		mapper.deleteByPrimaryKey(id.intValue());
		privilegeService.delProivilegeTree(userId.intValue(), id.toString(), AuthObjTypeEnum.ROLE.getCode(), ResourceTypeEnum.MENU.getCode());
	}
	
	@Transactional
	public void update(Long userId, SysRole role,String orgCode,String[] menuIds){
		Integer updateFlag = null;
		int role_id = getRoleByUserId(userId).getId();
		if(role_id == role.getId()){updateFlag=1;}
		role.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(role);
//		mapperExt.addMenus(role.getId(),menuIds);
		//角色绑定菜单
		List<String> menuIdList = menuIds==null?null:Arrays.asList(menuIds);
		privilegeService.savePrivlegeTree(updateFlag,role.getUpdateId().intValue(), role.getOrgCode(), AuthObjTypeEnum.ROLE.getCode(), role.getId().toString(), ResourceTypeEnum.MENU.getCode(), menuIdList);
	}
	
	public SysRole getRoleId(Long id){
		return mapper.selectByPrimaryKey(id.intValue());
	}
	
	public SysRole getRoleByUserId(Long userId)
	{
		SysRoleUserExample example = new SysRoleUserExample();
		example.createCriteria().andUserIdEqualTo(userId);
		SysRoleUser roleUser = roleUserMapper.selectByExample(example).get(0);
		return mapper.selectByPrimaryKey(roleUser.getRoleId());
	}
	
	public List<SysRole> getRoles(){
		return mapperExt.getRoles();
	}
	
	public Page<Object> getRoleByPage(RoleParamVo param){
		Page<Object> page=new Page<Object>();
		int count=mapperExt.countByParamVo(param);
		List<Object> list=mapperExt.selectByParamVo(param);
		page.setTotal(count);
		page.setRecords(list);
		return page;
	}
	
	public List<SysRole> getRoleByName(String name){
		SysRoleExample example = new SysRoleExample();
		example.createCriteria().andNameEqualTo(name);
		return mapper.selectByExample(example);
	} 
	
	
	/**
	 * 查询某个组织的所有角色
	 * @param orgCode
	 * @return
	 */
	public List<SysRole> getRolesByOrg(String orgCode){
		if(StrUtils.isNotEmpty(orgCode)) {
			SysRoleExample example = new SysRoleExample();
			example.createCriteria().andOrgCodeEqualTo(orgCode).andDelFlagEqualTo(0);
			return mapper.selectByExample(example);
		}
		return null;
	}
	
	public boolean existRoleName(SysRole role){
		return mapperExt.existRoleName(role);
	}
}
