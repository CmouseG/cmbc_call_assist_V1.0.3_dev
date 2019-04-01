package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.model.PrivlegeAuth;
import com.guiji.auth.util.ArrayUtils;
import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.SysPrivilegeMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysMenuExample;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysPrivilegeExample;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysPrivilegeExample.Criteria;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: PrivilegeService 
* @Description: 权限服务 
* @auth weiyunbo
* @date 2019年3月10日 下午3:26:22 
* @version V1.0  
*/
@Slf4j
@Service
public class PrivilegeService {
	@Autowired
	PrivilegeHisService privilegeHisService;
	@Autowired
	SysPrivilegeMapper sysPrivilegeMapper;
	@Autowired
	SysMenuMapper mapper;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	UserService userService;
	@Autowired
	AgentGroupChangeService agentGroupChangeService; 
	
	/**
	 * 查询授权对象的某种类型权限列表
	 * @param authId
	 * @param authType
	 * @param resourceType
	 * @return
	 */
	public List<SysPrivilege> queryPrivilegeListByAuth(String authId,Integer authType,Integer resourceType) {
		if(StrUtils.isNotEmpty(authId) && authType!=null && resourceType!=null) {
			SysPrivilegeExample example = new SysPrivilegeExample();
			example.createCriteria().andAuthIdEqualTo(authId).andAuthTypeEqualTo(authType).andResourceTypeEqualTo(resourceType);
			return sysPrivilegeMapper.selectByExample(example);
		}
		return null;
	}
	
	
	/**
	 * 查询某种类型资源给了哪些对象授权了
	 * @param resourceId
	 * @param resourceType
	 * @param authType
	 * @return
	 */
	public List<SysPrivilege> queryPrivilegeListByResource(String resourceId,Integer resourceType,Integer authType) {
		if(StrUtils.isNotEmpty(resourceId) && authType!=null && resourceType!=null) {
			SysPrivilegeExample example = new SysPrivilegeExample();
			example.createCriteria().andResourceIdEqualTo(resourceId).andAuthTypeEqualTo(authType).andResourceTypeEqualTo(resourceType);
			return sysPrivilegeMapper.selectByExample(example);
		}
		return null;
	}
	
	/**
	 * 新增权限关系信息
	 * @param userId
	 * @param orgCode
	 * @param authType
	 * @param authId
	 * @param resourceType
	 * @param resourceIdList
	 */
	@Transactional
	public void savePrivlege(Integer userId,String orgCode,Integer authType,String authId,Integer resourceType,List<String> resourceIdList) {
		if(userId!=null && StrUtils.isNotEmpty(orgCode) && authType!=null && StrUtils.isNotEmpty(authId) && resourceType!=null) {
			for(String resourceId:resourceIdList) {
				SysPrivilege sysPrivilege = new SysPrivilege();
				sysPrivilege.setAuthId(authId);
				sysPrivilege.setAuthType(authType);
				sysPrivilege.setOrgCode(orgCode);
				sysPrivilege.setResourceType(resourceType);
				sysPrivilege.setResourceId(resourceId);
				sysPrivilege.setCrtUser(userId);
				sysPrivilege.setCrtTime(DateUtil.getCurrent4Time());
				sysPrivilege.setUpdateUser(userId);
				sysPrivilege.setUpdateTime(DateUtil.getCurrent4Time());
				sysPrivilegeMapper.insert(sysPrivilege);
			}
			//校验是否满足条件，满足调用转人工服务绑定人工坐席
			agentGroupChangeService.bindAgentMembers(authType, authId, resourceType, resourceIdList);
		}
	}
	
	/**
	 * 全量保存关系
	 * 全量处理，多-新增，少-删除
	 * @param userId 操作人
	 * @param orgCode 操作人员orgcode
	 * @param authType	授权对象类型
	 * @param authId	授权对象ID
	 * @param resourceType	资源类型
	 * @param resourceIdList 资源分配列表
	 */
	@Transactional
	public void savePrivlegeTree(Integer userId,String orgCode,Integer authType,String authId,Integer resourceType,List<String> resourceIdList) {
		if(userId!=null && StrUtils.isNotEmpty(orgCode) && authType!=null && StrUtils.isNotEmpty(authId) && resourceType!=null) {
			if(resourceIdList==null||resourceIdList.isEmpty()) {
				//如果最新绑定关系为空，那么清空该类资源权限
				log.info("清空权限，授权对象类型:{}，授权id:{}，资源类型：{}",authType,authId,resourceType);
				this.delPrivilegeTree(orgCode,userId, authType, authId, resourceIdList, resourceType);
			}else {
				//查询该授权对象已经绑定的资源列表
				List<String> addResourceIdList = new ArrayList<String>();	//新增的资源列表
				List<String> delResourceIdList = new ArrayList<String>();	//需要删除资源列表
				List<SysPrivilege> existPrivilegeList = this.queryPrivilegeListByAuth(authId, authType, resourceType);
				if(existPrivilegeList==null || existPrivilegeList.isEmpty()) {
					log.info("授权对象类型:{}，授权id:{}，资源类型：{},没有保存数据，本次全部新增：{}",authType, authId, resourceType,resourceIdList);
					addResourceIdList = resourceIdList;
				}else {
					List<String> existResourceIdList = this.getAllResourceIds(existPrivilegeList);
					//本次变更后比之前多出来的ID
					addResourceIdList = ArrayUtils.getDiffIds(existResourceIdList.toArray(new String[existResourceIdList.size()]),resourceIdList.toArray(new String[resourceIdList.size()]));
					//本次变更后需要减少的ID
					delResourceIdList = ArrayUtils.getDiffIds(resourceIdList.toArray(new String[resourceIdList.size()]),existResourceIdList.toArray(new String[existResourceIdList.size()]));
				}
				if(addResourceIdList!=null && !addResourceIdList.isEmpty()) {
					log.info("授权对象类型:{}，授权id:{}，资源类型：{},本次新增资源：{}",authType,authId,resourceType,addResourceIdList);
					this.savePrivlege(userId, orgCode, authType, authId, resourceType, addResourceIdList);
				}
				if(delResourceIdList!=null && !delResourceIdList.isEmpty()) {
					log.info("授权对象类型:{}，授权id:{}，资源类型：{},本次删除资源：{}",authType,authId,resourceType,delResourceIdList);
					this.delPrivilegeTree(orgCode,userId, authType, authId, delResourceIdList, resourceType);
				}
			}
		}else {
			log.error("保存权限，必输字段不能为空!");
		}
	}
	
	/**
	 * 删除关系数据
	 * 如删除某个产品
	 * @param userId
	 * @param authId
	 * @param authType
	 * @param resourceType
	 */
	@Transactional
	public void delProivilegeTree(Integer userId,String authId,Integer authType,Integer resourceType) {
		if(userId!=null && authType!=null && StrUtils.isNotEmpty(authId) && resourceType!=null) {
			SysPrivilegeExample example = new SysPrivilegeExample();
			Criteria criteria = example.createCriteria();
			criteria.andResourceTypeEqualTo(resourceType);
			if(AuthObjTypeEnum.ORG.getCode()==authType) {
				//删除企业数据
				criteria.andOrgCodeLike(authId+"%");
			}else {
				criteria.andAuthIdEqualTo(authId);
			}
			if(StrUtils.isNotEmpty(authId)) {
				criteria.andAuthIdEqualTo(authId);
			}
			List<SysPrivilege> list = sysPrivilegeMapper.selectByExample(example);
			int delCount = sysPrivilegeMapper.deleteByExample(example);
			log.info("删除权限,删除人:{},授权对象类型:{},授权对象编号：{},资源类型：{},共计删除数据：{}条",userId,authType,authId,resourceType,delCount);
			if(list!=null && !list.isEmpty()) {
				List<String> resourceIdList = new ArrayList<String>();
				for(SysPrivilege sysPrivilege:list) {
					//记录删除历史
					privilegeHisService.save(userId, sysPrivilege);
					resourceIdList.add(sysPrivilege.getResourceId());
				}
				//校验并删除坐席
				agentGroupChangeService.unBindAgentMembers(authType, authId, resourceType, resourceIdList);
			}
		}
	}
	
	/**
	 * 根据组织编号删除
	 * 场景：上级组织删除某个菜单，那么该组织下所有下级组织以及人，都删除该菜单
	 * @param userId 删除人
	 * @param orgCode 删除的关系组织
	 * @param authId 授权对象id
	 * @param resourceIdList
	 * @param resourceType
	 */
	@Transactional
	public void delPrivilegeTree(String orgCode,Integer userId,Integer authType,String authId,List<String> resourceIdList,Integer resourceType) {
		if(userId!=null && authType!=null && StrUtils.isNotEmpty(authId) && !resourceIdList.isEmpty() && resourceType!=null) {
			SysPrivilegeExample example = new SysPrivilegeExample();
			Criteria criteria = example.createCriteria();
			criteria.andResourceTypeEqualTo(resourceType);
			if(resourceIdList.size()==1) {
				criteria.andResourceIdEqualTo(resourceIdList.get(0));
			}else {
				criteria.andResourceIdIn(resourceIdList);
			}
			if(AuthObjTypeEnum.ORG.getCode()==authType) {
				//删除企业数据
				criteria.andOrgCodeLike(orgCode+"%");
			}else {
				criteria.andAuthIdEqualTo(authId);
			}
			if(StrUtils.isNotEmpty(authId)) {
				criteria.andAuthIdEqualTo(authId);
			}
			List<SysPrivilege> list = sysPrivilegeMapper.selectByExample(example);
			int delCount = sysPrivilegeMapper.deleteByExample(example);
			log.info("删除权限,删除人:{},授权对象类型:{},授权对象编号：{},资源编号：{},资源类型：{},共计删除数据：{}条",userId,authType,authId,resourceIdList,resourceType,delCount);
			if(list!=null && !list.isEmpty()) {
				for(SysPrivilege sysPrivilege:list) {
					//删除关系
					sysPrivilegeMapper.deleteByPrimaryKey(sysPrivilege.getId());
					//记录删除历史
					privilegeHisService.save(userId, sysPrivilege);
				}
			}
			//校验并删除坐席
			agentGroupChangeService.unBindAgentMembers(authType, authId, resourceType, resourceIdList);
		} 
	}
	
	
	/**
	 * 获取资源id
	 * @param privilegeList
	 * @return
	 */
	private List<String> getAllResourceIds(List<SysPrivilege> privilegeList){
		if(privilegeList!=null &&!privilegeList.isEmpty()) {
			List<String> resourceIds = new ArrayList<String>();
			for(SysPrivilege sysPrivilege : privilegeList) {
				resourceIds.add(sysPrivilege.getResourceId());
			}
			return resourceIds;
		}
		return null;
	}
	
	/**
	 * 获取用户查询权限
	 * 1、如果操作人是目标企业的人，那么权限为操作人角色的权限
	 * 2、如果操作人为下级企业赋全，那么权限为下级企业的权限
	 * @param userId
	 * @param myOrgCode
	 * @param targetOrgId
	 * @return
	 */
	public PrivlegeAuth getUserAuthLevel(Integer userId,String myOrgCode,String targetOrgCode){
		if(userId!=null && StrUtils.isNotEmpty(myOrgCode)) {
			PrivlegeAuth privlegeAuth = new PrivlegeAuth();
			if(!myOrgCode.equals(targetOrgCode)) {
				//如果操作人不是目标企业的，那么权限最大就是目标企业的权限
				SysOrganization sysOrganization = organizationService.getOrgByCode(targetOrgCode);
				privlegeAuth.setAuthId(sysOrganization.getId().toString()); //目标企业ID
				privlegeAuth.setAuthType(AuthObjTypeEnum.ORG.getCode());	//企业
			}else {
				List<SysRole> roleList = userService.getRoleByUserId(Long.valueOf(userId));
				if(roleList!=null && !roleList.isEmpty()) {
					//因为现在1个用户只有1个角色，所以只用这个角色判断
					privlegeAuth.setAuthId(roleList.get(0).getId().toString()); //目标角色ID
					privlegeAuth.setAuthType(AuthObjTypeEnum.ROLE.getCode());	//角色
				}
			}
			return privlegeAuth;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 根据授权对象、授权类型，查询绑定的菜单/按钮权限的菜单树
	 * @param authType
	 * @param authId
	 * @return
	 */
	public List<SysMenu> queryMenuTreeByLowId(Integer authType,String authId){
		//查询关联的底层菜单
		List<SysPrivilege> menuPrivilegeList = this.queryPrivilegeListByAuth(authId.toString(), authType, ResourceTypeEnum.MENU.getCode());
		if(menuPrivilegeList!=null&&!menuPrivilegeList.isEmpty()) {
			//不为空的话，查询底层菜单的上级菜单
			Set<SysMenu> setMenu = new HashSet<SysMenu>();
			SysMenuExample example = new SysMenuExample();
			example.createCriteria().andDelFlagEqualTo(0);	//查询正常数据	
			List<SysMenu> allMenuList = mapper.selectByExample(example);
			if(allMenuList!=null && !allMenuList.isEmpty()) {
				Map<Integer,SysMenu> menuMap = this.changeMenu2Map(allMenuList);
				for(SysPrivilege privilege : menuPrivilegeList) {
					//设置上级
					this.fillParentMenu(menuMap, setMenu, Integer.valueOf(privilege.getResourceId()));
				}
			}
			if(setMenu!=null) {
				//set转list
				List<SysMenu> menuList = new ArrayList<SysMenu>(setMenu);
				//排序
				menuList.sort(Comparator.comparing(SysMenu::getLevel).thenComparing(SysMenu::getId));
				return menuList;
			}
		}
		return null;
	}
	
	/**
	 * 根据ID查询菜单
	 * @param menuMap
	 * @param id
	 * @return
	 */
	private void fillParentMenu(Map<Integer,SysMenu> menuMap,Set<SysMenu> setMenu,Integer id) {
		if(menuMap!=null) {
			SysMenu sysMenu = menuMap.get(id);
			if(sysMenu!=null) {
				setMenu.add(sysMenu);
				if(sysMenu.getPid()!=null && sysMenu.getPid()!=0) {
					//不是顶层继续查下去
					this.fillParentMenu(menuMap, setMenu, sysMenu.getPid());
				}
			}
		}
	}
	
	/**
	 * 将菜单转map
	 * @param allMenuList
	 * @return
	 */
	private Map<Integer,SysMenu> changeMenu2Map(List<SysMenu> allMenuList){
		if(allMenuList!=null && !allMenuList.isEmpty()) {
			Map<Integer,SysMenu> map = new HashMap<Integer,SysMenu>();
			for(SysMenu sysMenu : allMenuList) {
				map.put(sysMenu.getId(), sysMenu);
			}
			return map;
		}
		return null;
	}
}
