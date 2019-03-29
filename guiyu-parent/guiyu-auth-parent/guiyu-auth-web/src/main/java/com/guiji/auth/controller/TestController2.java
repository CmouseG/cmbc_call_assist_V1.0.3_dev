package com.guiji.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.service.PrivilegeService;
import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.SysOrganizationMapper;
import com.guiji.user.dao.SysRoleMapper;
import com.guiji.user.dao.SysRoleUserMapper;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysMenuExample;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysRoleExample;
import com.guiji.user.dao.entity.SysRoleUser;
import com.guiji.user.dao.entity.SysRoleUserExample;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.dao.entity.SysUserExample;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;

/** 
* @ClassName: TestController2 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @auth weiyunbo
* @date 2019年3月27日 下午1:07:15 
* @version V1.0  
*/
@RestController
@RequestMapping("batch")
public class TestController2 {
	@Autowired
	SysMenuMapper sysMenuMapper;
	@Autowired
	SysOrganizationMapper sysOrganizationMapper;
	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	PrivilegeService privilegeService;
	@Autowired
	SysUserMapper sysUserMapper;
	@Autowired
	SysRoleUserMapper sysRoleUserMapper;
	@Autowired
	RedisUtil redisUtil;
	//内存数据
	Date now = DateUtil.parseDate("2019-03-16 00:00:00");
	Map<Integer,SysMenu> menuButtonMap = new HashMap<Integer,SysMenu>();	//父级菜单以及对应的按钮map
	List<SysMenu> allMenu = null;
	List<SysOrganization> allOrgs = null;
	List<SysRole> allRoles = null;
	List<SysRoleUser> allUserRoles = null;
	List<SysUser> allUsers = null;
	Map<String,SysOrganization> orgCodeMap = new HashMap<String,SysOrganization>();
	Map<Integer,SysRole> userRoleMap = new HashMap<Integer,SysRole>();
	Map<Integer,SysRole> roleMap = new HashMap<Integer,SysRole>();
	Map<Integer,List<Long>> roleMenuMap = new HashMap<Integer,List<Long>>();	//角色-菜单map
	Map<Integer,SysRoleUser> userRoleMap2 = new HashMap<Integer,SysRoleUser>();
	Map<Integer,Set<Integer>> roleOrgMap = new HashMap<Integer,Set<Integer>>();	//角色ID--企业ID
	Map<String,Map<Integer,Set<Integer>>> orgRolUserMap = new HashMap<String,Map<Integer,Set<Integer>>>();
	Map<Integer,SysRole> newAndOldRoleMap = new HashMap<Integer,SysRole>();	//新老role映射关系
	Set<Integer> sysRoleSet = new HashSet<Integer>();	//系统级角色（未删除重建的）
	Integer workPlatformMenuId = null;	//个人中心或者坐席工作台菜单
	Map<Integer,List<String>> addButtonsMap = new HashMap<Integer,List<String>>();	//新增的按钮，key-父菜单id,value-新增的按钮id
	Map<Integer,List<String>> roleMenuPrivMap = new HashMap<Integer,List<String>>(); //现在角色对应的按钮菜单权限
	Map<String,SysMenu> menuMap = new HashMap<String,SysMenu>();
	
	@RequestMapping("ttttttt")
	public void test2() {
		init();
		for(SysRole role : allRoles) {
			if(1==role.getId()) {
				continue;
			}
			List<String> addButtonsList = new ArrayList<String>();
			List<SysPrivilege> buttonsIds = privilegeService.queryPrivilegeListByAuth(role.getId().toString(), AuthObjTypeEnum.ROLE.getCode(), ResourceTypeEnum.MENU.getCode());
			for(SysPrivilege pv : buttonsIds) {
				SysMenu menu = menuMap.get(pv.getResourceId());	//原来配置的按钮
				Integer pid = menu.getPid();
				List<String> otherBts = addButtonsMap.get(pid);
				if(otherBts!=null && otherBts.size()>0) {
					addButtonsList.addAll(otherBts);
					
				}
			}
			if(addButtonsList!=null && !addButtonsList.isEmpty()) {
				System.out.println("roleid:"+role.getId()+"-------新增按钮数："+addButtonsList.size());
					privilegeService.savePrivlege(1, role.getOrgCode(), AuthObjTypeEnum.ROLE.getCode(), role.getId().toString(), ResourceTypeEnum.MENU.getCode(), addButtonsList);
			}
		}
	}
	
	
	/**
	 * 基础数据初始化
	 */
	private void init() {
		//菜单
		SysMenuExample example = new SysMenuExample();
		allMenu = sysMenuMapper.selectByExample(example);
		for(SysMenu menu:allMenu) {
			menuMap.put(menu.getId().toString(), menu);
		}
		
		//新增的按钮  pid-ids
		SysMenuExample exampleb = new SysMenuExample();
		example.createCriteria().andTypeEqualTo(2).andRemarksEqualTo("add");
		List<SysMenu> buttonList = sysMenuMapper.selectByExample(exampleb);
		if(buttonList!=null && !buttonList.isEmpty()) {
			for(SysMenu button:buttonList) {
				Integer pid = button.getPid();
				if(pid!=null) {
					List<String> ids = addButtonsMap.get(pid);
					if(ids==null) {
						ids = new ArrayList<String>();
					}
					ids.add(button.getId().toString());
					addButtonsMap.put(pid, ids);
				}
			}
		}
		
		//组织
		allOrgs = sysOrganizationMapper.selectByExample(new SysOrganizationExample());
		//角色
		SysRoleExample example2 = new SysRoleExample();
		example2.createCriteria().andDelFlagEqualTo(0);
		allRoles = sysRoleMapper.selectByExample(example2);
		//用户
		allUsers = sysUserMapper.selectByExample(new SysUserExample());
		//用户-角色
		allUserRoles = sysRoleUserMapper.selectByExample(new SysRoleUserExample());
		//角色-菜单
		
		//初始化roleMap
		for(SysRole role:allRoles) {
			roleMap.put(role.getId(), role);
			List<Long> menuList = sysMenuMapper.getSelectedMenuId(Long.valueOf(role.getId()));
			roleMenuMap.put(role.getId(), menuList);
		}
		//初始化userid--role map
		for(SysRoleUser ur : allUserRoles) {
			userRoleMap.put(ur.getUserId().intValue(), roleMap.get(ur.getRoleId()));
			userRoleMap2.put(ur.getUserId().intValue(), ur);
		}
		//初始化 orgCode-orgMap
		for(SysOrganization org : allOrgs) {
			orgCodeMap.put(org.getCode(), org);
		}
	}
}
