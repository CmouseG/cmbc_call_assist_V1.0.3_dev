package com.guiji.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.constants.AuthConstants;
import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.MenuTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.service.PrivilegeService;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.SysOrganizationMapper;
import com.guiji.user.dao.SysRoleMapper;
import com.guiji.user.dao.SysRoleUserMapper;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysMenuExample;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysOrganizationExample;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysRoleExample;
import com.guiji.user.dao.entity.SysRoleUser;
import com.guiji.user.dao.entity.SysRoleUserExample;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.dao.entity.SysUserExample;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;

/** 
* @ClassName: OldDataDealController 
* @Description: 存量数据批量处理服务
* @auth weiyunbo
* @date 2019年3月16日 下午4:23:11 
* @version V1.0  
*/
@RestController
@RequestMapping("batch")
public class OldDataDealController {
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
	/**
	 * START
	 * @return
	 */
	@RequestMapping("initData")
	@Transactional
	public ReturnData initData() {
		//基础数据初始化
		init();
		//底层菜单增加查询按钮
		step1();
		//更新用户组织代码
		step2();
		//角色现在是公用的，从这里按企业使用情况分开，各自一套
		step3();
		//为新角色添加一套菜单-按钮权限
		step4();
		//系统级角色-菜单权限
		step5();
		//系统顶层组织-菜单权限添加
		setp6();
		//系统其它组织-菜单权限添加（默认添加管理员菜单）
		step7();
		//清理redis缓存
		step8();
		//数据清理
		destroy();
		return Result.ok();
	}
	
	/**
	 * 基础数据初始化
	 */
	private void init() {
		//菜单
		SysMenuExample example = new SysMenuExample();
		example.createCriteria().andTypeEqualTo(1);
		allMenu = sysMenuMapper.selectByExample(example);
		
		//新增的按钮  pid-ids
		SysMenuExample exampleb = new SysMenuExample();
		exampleb.createCriteria().andTypeEqualTo(2).andRemarksEqualTo("add");
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
	
	/**
	 * 清理内存数据
	 */
	public void destroy() {
		menuButtonMap = new HashMap<Integer,SysMenu>();	//父级菜单以及对应的按钮map
		allMenu = null;
		allOrgs = null;
		allRoles = null;
		allUserRoles = null;
		allUsers = null;
		orgCodeMap = new HashMap<String,SysOrganization>();
		userRoleMap = new HashMap<Integer,SysRole>();
		roleMap = new HashMap<Integer,SysRole>();
		roleMenuMap = new HashMap<Integer,List<Long>>();	//角色-菜单map
		userRoleMap2 = new HashMap<Integer,SysRoleUser>();
		roleOrgMap = new HashMap<Integer,Set<Integer>>();	//角色ID--企业ID
		orgRolUserMap = new HashMap<String,Map<Integer,Set<Integer>>>();
		newAndOldRoleMap = new HashMap<Integer,SysRole>();	//新老role映射关系
		sysRoleSet = new HashSet<Integer>();	//系统级角色（未删除重建的）
		addButtonsMap = new HashMap<Integer,List<String>>();
	}
	
	/**
	 * 步骤一：菜单默认新增查询按钮
	 */
	private void step1() {
		List<SysMenu> lowMenu = new ArrayList<SysMenu>();
		for(SysMenu sysMenu:allMenu) {
			boolean isLowMenu = true;	//是否底层菜单，默认是底层菜单
			for(SysMenu sysMenu2:allMenu) {
				if(sysMenu.getId()==sysMenu2.getPid()) {
					isLowMenu = false;	//有子节点，跳出
					break;
				}
			}
			if(isLowMenu) {
				lowMenu.add(sysMenu);
			}
		}
		//获取到的底层菜单，每个菜单默认添加一个查询按钮
		for(SysMenu menu : lowMenu) {
			//新增初始化的查询按钮
			SysMenu queryButton = new SysMenu();
			queryButton.setName(menu.getName()+"-查询");
			queryButton.setPid(menu.getId()); //父级id
			queryButton.setLevel(menu.getLevel()+1);
			queryButton.setType(MenuTypeEnum.BUTTON.getCode());
			if(menu.getUrl().equals("/")) {
				//首页
				queryButton.setPermission("firstPage:defquery");
				queryButton.setUrl("firstPage_defquery");
			}else {
				queryButton.setPermission(menu.getUrl().replace("/", ":").substring(1));
				queryButton.setUrl(menu.getUrl().replace("/", "_").substring(1)+"_defquery");
			}
			queryButton.setIsShow(1);
			queryButton.setAppid(0);
			queryButton.setDelFlag(0);
			queryButton.setCreateId(1L);
			queryButton.setUpdateId(1L);
			queryButton.setCreateTime(now);
			queryButton.setUpdateTime(now);
			sysMenuMapper.insert(queryButton);
			menuButtonMap.put(menu.getId(), queryButton);
			//添加坐席虚拟操作
			//新增初始化的查询按钮，默认拥有接待中心的人就是坐席
			if(menu.getUrl().contains("workPlatform")) {
				//接待中心下默认挂个坐席
				workPlatformMenuId = menu.getId();
			}
		}
	}
	
	/**
	 * 更新用户的组织代码
	 */
	public void step2() {
		for(SysUser user : allUsers) {
			SysRole role = userRoleMap.get(user.getId().intValue());
			String orgCode = user.getOrgCode();
			if(1==role.getId() || 2==role.getId() || 3==role.getId()) {
				//原管理员用户的code同企业code一致
			}else {
				//其他角色
				int last2 = orgCode.lastIndexOf(".",orgCode.lastIndexOf(".")-1);	//倒数第2个.
				orgCode = orgCode.substring(0, last2+1);
				user.setOrgCode(orgCode);
				//更新
				sysUserMapper.updateByPrimaryKeySelective(user);
			}
			//遍历时顺便把 组织-角色-用户关系获取下
			//开始拼装：组织-角色-用户 map
			Map<Integer,Set<Integer>> roleUserMap = orgRolUserMap.get(orgCode);
			if(roleUserMap==null) {
				roleUserMap = new HashMap<Integer,Set<Integer>>();
				Set<Integer> userSet = new HashSet<Integer>();
				userSet.add(user.getId().intValue());
				roleUserMap.put(role.getId(),userSet);
				orgRolUserMap.put(orgCode, roleUserMap);
			}else {
				Set<Integer> userSet = roleUserMap.get(role.getId());
				if(userSet==null) {
					userSet = new HashSet<Integer>();
				}
				userSet.add(user.getId().intValue());
				roleUserMap.put(role.getId(),userSet);
				orgRolUserMap.put(orgCode, roleUserMap);
			}
		}
	}
	
	
	/**
	 * 将角色拆开，原来公用一套角色，现在按企业拆开
	 */
	public void step3() {
		for(Map.Entry<String,Map<Integer,Set<Integer>>> orgRoleUserEntry : orgRolUserMap.entrySet()) {
			//遍历组织
			String orgCode = orgRoleUserEntry.getKey();	//组织
			if(AuthConstants.ROOT_ORG_CODE.equals(orgCode)) {
				//如果是系统组织，那么暂不处理，因为现在的角色默认系统都会保留一套，给其他组织新增
				Set<Integer> roleSet = orgRoleUserEntry.getValue().keySet();
				if(roleSet!=null && !roleSet.isEmpty()) {
					sysRoleSet.addAll(roleSet);
				}
				continue;
			}
			//普通企业下
			for(Map.Entry<Integer,Set<Integer>> roleUserEntry : orgRoleUserEntry.getValue().entrySet()) {
				//遍历角色
				Integer roleId = roleUserEntry.getKey();	//角色ID
				//新建复制一个角色
				SysRole oldRole = roleMap.get(roleId);
				SysRole newRole = new SysRole();
				BeanUtil.copyProperties(oldRole, newRole);
				newRole.setId(null);
				newRole.setCreateTime(now);
				newRole.setUpdateTime(now);
				newRole.setOrgCode(orgCode);
				oldRole.setOrgCode(orgCode);	//老的也加下orgCode，后续使用
				//新增角色
				sysRoleMapper.insert(newRole);
				//新角色对应老角色映射
				newAndOldRoleMap.put(newRole.getId(), oldRole);
				for(Integer userId:roleUserEntry.getValue()){
					//删除原user_role
					sysRoleUserMapper.deleteByPrimaryKey(userRoleMap2.get(userId).getId());
					//新建关系
					SysRoleUser sysRoleUser = new SysRoleUser();
					sysRoleUser.setUserId(Long.valueOf(userId));
					sysRoleUser.setRoleId(newRole.getId());
					sysRoleUser.setCreateId(1L);
					sysRoleUser.setCreateTime(now);
					sysRoleUser.setUpdateId(1L);
					sysRoleUser.setUpdateTime(now);
					sysRoleUser.setDelFlag(0);
					sysRoleUserMapper.insert(sysRoleUser);
				}
			}
		}
	}
	
	
	/**
	 * 权限管理：原角色挂的菜单，重新挂到新的角色上，同时挂上该菜单的查询按钮
	 */
	public void step4() {
		for(Map.Entry<Integer, SysRole> newAndOldRoleEntry : newAndOldRoleMap.entrySet()) {
			//遍历新角色
			Integer newRoleId = newAndOldRoleEntry.getKey();
			SysRole oldRole = newAndOldRoleEntry.getValue();
			Integer oldRoleId = oldRole.getId();
			List<String> buttonList = new ArrayList<String>();	//菜单下按钮的id
			List<Long> roleMenuList = roleMenuMap.get(oldRoleId);	//原来角色下挂的菜单
			if(roleMenuList!=null && !roleMenuList.isEmpty()) {
				//找到新加的菜单下挂的默认查询按钮
				for(Long menuId:roleMenuList) {
					SysMenu button = menuButtonMap.get(menuId.intValue());
					if(button!=null) {
						buttonList.add(button.getId().toString());
					}
					if(menuId.intValue()==workPlatformMenuId) {
						//如果当前是坐席工作台菜单，那么默认拥有坐席操作权限
						buttonList.add(AuthConstants.MENU_AGENT_MEMBER+"");
					}
					//添加前端新增的按钮权限
					if(addButtonsMap.get(menuId.intValue())!=null) {
						buttonList.addAll(addButtonsMap.get(menuId.intValue()));
					}
				}
				if(buttonList!=null && !buttonList.isEmpty()) {
					privilegeService.savePrivlege(1, oldRole.getOrgCode(), AuthObjTypeEnum.ROLE.getCode(), newRoleId.toString(), ResourceTypeEnum.MENU.getCode(), buttonList);
				}
			}
		}
	}
	
	/**
	 * 初始化系统级角色对应的菜单权限
	 */
	public void step5() {
		//初始系统角色菜单权限
		for(Integer roleId:sysRoleSet) {
			List<String> buttonList = new ArrayList<String>();	//菜单下按钮的id
			List<Long> menuList = roleMenuMap.get(roleId);
			if(menuList!=null && !menuList.isEmpty()) {
				//找到新加的菜单下挂的默认查询按钮
				for(Long menuId:menuList) {
					SysMenu button = menuButtonMap.get(menuId.intValue());
					if(button!=null) {
						buttonList.add(button.getId().toString());
					}
					if(menuId.intValue()==workPlatformMenuId) {
						//如果当前是坐席工作台菜单，那么默认拥有坐席操作权限
						buttonList.add(AuthConstants.MENU_AGENT_MEMBER+"");
					}
					//添加前端新增的按钮权限
					if(addButtonsMap.get(menuId.intValue())!=null) {
						buttonList.addAll(addButtonsMap.get(menuId.intValue()));
					}
				}
				if(buttonList!=null && !buttonList.isEmpty()) {
					privilegeService.savePrivlege(1, "1", AuthObjTypeEnum.ROLE.getCode(), roleId.toString(), ResourceTypeEnum.MENU.getCode(), buttonList);
				}
			}
		}
	}
	
	/**
	 * 初始系统顶层组织-菜单权限
	 */
	public void setp6() {
		List<SysMenu> buttonList = new ArrayList<>(menuButtonMap.values());
		List<String> buttonIdList = new ArrayList<String>();	//菜单下按钮的id
		for(SysMenu buttonMenu : buttonList) {
			buttonIdList.add(buttonMenu.getId().toString());
		}
		if(buttonIdList!=null && !buttonIdList.isEmpty()) {
			privilegeService.savePrivlege(1, "1", AuthObjTypeEnum.ORG.getCode(), AuthConstants.ROOT_ORG_CODE, ResourceTypeEnum.MENU.getCode(), buttonIdList);
		}
	}
	
	/**
	 * 给系统中一般组织配置菜单权限
	 * 按企业管理员配置给默认权限
	 */
	public void step7() {
		//查询企业管理员对应按钮
		List<String> buttonList = new ArrayList<String>();	//菜单下按钮的id
		List<Long> roleMenuList = roleMenuMap.get(3L);	//原来角色下挂的菜单
		if(roleMenuList!=null && !roleMenuList.isEmpty()) {
			//找到新加的菜单下挂的默认查询按钮
			for(Long menuId:roleMenuList) {
				SysMenu button = menuButtonMap.get(menuId.intValue());
				if(button!=null) {
					buttonList.add(button.getId().toString());
				}
				if(menuId.intValue()==workPlatformMenuId) {
					//如果当前是坐席工作台菜单，那么默认拥有坐席操作权限
					buttonList.add(AuthConstants.MENU_AGENT_MEMBER+"");
				}
				//添加前端新增的按钮权限
				if(addButtonsMap.get(menuId.intValue())!=null) {
					buttonList.addAll(addButtonsMap.get(menuId.intValue()));
				}
			}
		}
		for(SysOrganization org : allOrgs) {
			if(org.getId()==1L) {
				//顶层组织挂所有菜单，已经处理
				continue;
			}
			//其他组织默认挂管理员菜单
			privilegeService.savePrivlege(1, org.getCode(), AuthObjTypeEnum.ORG.getCode(), org.getId().toString(), ResourceTypeEnum.MENU.getCode(), buttonList);
		}
	}
	
	/**
	 * 请求auth相关redis缓存
	 */
	@RequestMapping("flushAuthCache")
	public void step8() {
		redisUtil.delVague("REDIS_USER_BY_USERID_");
		redisUtil.delVague("REDIS_ROLE_BY_USERID_");
		redisUtil.delVague("REDIS_ORG_BY_USERID_");
		redisUtil.delVague("REDIS_ORG_BY_USERID_");
		redisUtil.delVague("REDIS_ORG_BY_CODE_");
	}
	
	
}
