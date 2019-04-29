package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.MenuTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.model.MenuVO;
import com.guiji.auth.model.PrivlegeAuth;
import com.guiji.auth.util.DataLocalCacheUtil;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysMenuExample;
import com.guiji.user.dao.entity.SysMenuExample.Criteria;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.vo.MenuParamVo;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;

@Service
public class MenuService {
	@Autowired
	private SysMenuMapper mapper;
	@Autowired
	PrivilegeService privilegeService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	UserService userService;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;

	public void insert(SysMenu menu){
		
		mapper.insertSelective(menu);
	}

	public void delete(Integer id){
		mapper.deleteByPrimaryKey(id);
	}

	public void update(SysMenu menu){
		mapper.updateByPrimaryKeySelective(menu);
	}

	public SysMenu getMenuById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有菜单-只查菜单
	 * @return
	 */
	public List<SysMenu> getAllMenus(){
		SysMenuExample example = new SysMenuExample();
		example.createCriteria().andDelFlagEqualTo(0).andPidIsNotNull().andTypeEqualTo(MenuTypeEnum.MENU.getCode());
		example.setOrderByClause(" pid");
		List<SysMenu> list = mapper.selectByExample(example);
		return parseTree(list,false);
	}
	
	/**
	 * 权限管理中菜单选择
	 * 获取当前用户可以赋权的所有菜单，以及已经选择的菜单列表
	 * @param roleId 角色ID
	 * @param userId 当前操作用户
	 * @param orgCode 当前操作用户所属企业
	 * @param targetOrgCode 目标企业
	 * @return
	 */
	public Map<String,Object> getOrgRoleAuthMenus(Long roleId,Integer userId,String orgCode,String targetOrgCode){
		Map<String,Object> map=new HashMap<String,Object>();
		//获取当前用户的权限范围
		PrivlegeAuth privlegeAuth = privilegeService.getUserAuthLevel(userId, orgCode, targetOrgCode);
		if(privlegeAuth!=null) {
			//查询当前用户菜单范围列表
			List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(privlegeAuth.getAuthType(), privlegeAuth.getAuthId());
			if(!targetOrgCode.equals("1")){  // 非系统下角色
				Iterator<SysMenu> iterator = allMenus.iterator();
				while(iterator.hasNext()){
					SysMenu menu = iterator.next();
					String url = menu.getUrl();
					if(url.equals("/botsentence/botsentence_approve") ||  // 话术审核
					   url.equals("/botsentence/botsentence_mytemplate")||  // 行业模板
					   url.equals("/botsentence/botsentence_history") ||  // 发布历史
					   url.equals("/botsentence/botsentence_keywords") ||   // 关键词库
					   url.equals("/botsentence/botsentence_approveKeywords") || // 关键词审核
					   url.equals("/system/menu") || // 菜单维护
					   url.equals("/system/dataDictionaries") || // 数据字典
					   url.equals("/system/processManage") ||  // 资源进程
					   url.equals("/system/processTask") ||  // 进程任务
					   url.equals("/smsCenter/platformManage") ||  // 平台管理列表
					   url.equals("/financeCenter/rechargeManage") || // 充值管理
					   url.equals("/robotCenter/simList") || // 机器人实时配置
					   url.equals("system_account_delay")  // 系统管理-开户管理-延期
					   ){iterator.remove();}
				}
			}
			//拼装树形结构
			if(allMenus!=null) {
				map.put("menus", parseTree(allMenus,false));
			}
		}
		List<Long> selected= new ArrayList<Long>();
		if(roleId!=null) {
			//查询该角色的菜单权限
			List<SysPrivilege> menuList = privilegeService.queryPrivilegeListByAuth(roleId.toString(), AuthObjTypeEnum.ROLE.getCode(), ResourceTypeEnum.MENU.getCode());
			if(menuList!=null&&!menuList.isEmpty()) {
				for(SysPrivilege privilege:menuList) {
					selected.add(Long.valueOf(privilege.getResourceId()));
				}
			}
		}
		map.put("selected", selected);
		return map;
	}
	
	/**
	 * 产品管理中选择菜单选中状态
	 * @param productId
	 * @return
	 */
	public Map<String,Object> getProductAuthMenus(Long productId){
		Map<String,Object> map=new HashMap<String,Object>();
		List<SysMenu> allMenu=mapper.getAllMenus();
		//获取当前用户的权限范围
		if(allMenu!=null) {
			//拼装树形结构
			map.put("menus", parseTree(allMenu,false));
		}
		List<Long> selected= new ArrayList<Long>();
		if(productId!=null) {
			//查询该产品的菜单权限
			List<SysPrivilege> menuList = privilegeService.queryPrivilegeListByAuth(productId.toString(), AuthObjTypeEnum.PRODUCT.getCode(), ResourceTypeEnum.MENU.getCode());
			if(menuList!=null&&!menuList.isEmpty()) {
				for(SysPrivilege privilege:menuList) {
					selected.add(Long.valueOf(privilege.getResourceId()));
				}
			}
		}
		map.put("selected", selected);
		return map;
	}
	
	
	/**
	 * 组织管理中选择菜单选中状态
	 * @param productId
	 * @return
	 */
	public Map<String,Object> getOrgAuthMenus(Long productId,String parentOrgCode,String targetOrgCode){
		Map<String,Object> map=new HashMap<String,Object>();
		//获取当前用户的权限范围
		if(productId!=null) {
			//查询产品范围列表
			List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(AuthObjTypeEnum.PRODUCT.getCode(), productId.toString());
			//拼装树形结构
			if(allMenus!=null) {
				map.put("menus", parseTree(allMenus,true));
			}
		}else if(StrUtils.isNotEmpty(parentOrgCode)) {
			//查询上级企业菜单范围列表
			SysOrganization parentOrganization = organizationService.getOrgByCode(parentOrgCode);
			List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(AuthObjTypeEnum.ORG.getCode(), parentOrganization.getId().toString());
			//拼装树形结构
			if(allMenus!=null) {
				map.put("menus", parseTree(allMenus,true));
			}
		}else {
			throw new GuiyuException("产品编号和上级企业编号不能都为空!");
		}
		List<Long> selected= new ArrayList<Long>();
		if(StrUtils.isNotEmpty(targetOrgCode)) {
			//查询本组织的菜单权限
			SysOrganization organization = organizationService.getOrgByCode(targetOrgCode);
			List<SysPrivilege> menuList = privilegeService.queryPrivilegeListByAuth(organization.getId().toString(), AuthObjTypeEnum.ORG.getCode(), ResourceTypeEnum.MENU.getCode());
			if(menuList!=null&&!menuList.isEmpty()) {
				for(SysPrivilege privilege:menuList) {
					selected.add(Long.valueOf(privilege.getResourceId()));
				}
			}
		}
		map.put("selected", selected);
		return map;
	}
	
	/**
	 * 返回用户有权的菜单列表以及按钮列表
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getMenus(Long userId){
		if(userId!=null) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<SysRole> roleList = userService.getRoleByUserId(userId);
			if(roleList!=null && !roleList.isEmpty()) {
				//现在用户只有1个角色
				SysRole sysRole = roleList.get(0);
				List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(AuthObjTypeEnum.ROLE.getCode(), sysRole.getId().toString());
				Map<String,SysMenu> buttonMap = new HashMap<String,SysMenu>();
				if(allMenus!=null) {
					Iterator<SysMenu> it = allMenus.iterator();
					while(it.hasNext()){
						SysMenu sysMenu = it.next();
						if(MenuTypeEnum.BUTTON.getCode()==sysMenu.getType()){
							//加入按钮列表
							buttonMap.put(sysMenu.getUrl(), sysMenu);
							//从菜单列表中移除
							it.remove();
						}
					}
					map.put("menus", parseTree(allMenus,false));
				}
				map.put("buttons", buttonMap);
				return map;
			}
		}
		return null;
	}
	
	public Map<String,String> getAllPermissions(){
		List<Map<String,String>> permList=mapper.getAllPermissions();
		Map<String,String> result=new HashMap<>();
		permList.forEach((item)->{
			result.put(item.get("url"), item.get("permission"));
		});
		
		return result;
	}
	
	/**
	 * 将菜单转为树形结构，
	 * @param allMenu
	 * @param filterSysMenuFlag 是否要过滤掉系统菜单（很多地方的菜单显示是要分配的，有些菜单不能分配出去）
	 * @return
	 */
	private List<SysMenu> parseTree(List<SysMenu> allMenu,boolean filterSysMenuFlag){
		Map<Integer,SysMenu> map=new HashMap<>();
		List<SysMenu> list=new ArrayList<>();
		for(SysMenu item : allMenu) {
			if(filterSysMenuFlag && item.getSysType()!=null && 1==item.getSysType()) {
				//过滤掉系统菜单
				continue;
			}
			Integer pid=item.getPid();
			if(0==pid){
				list.add(item);
				map.put(item.getId(), item);
			}else{
				SysMenu parent=map.get(pid);
				if(parent!=null){
					parent.getChild().add(item);
					map.put(item.getId(), item);
				}
			}
		}
		return list;
	}
	
	/**
	 * 分页查询菜单
	 * @param param
	 * @return
	 */
	public Page<MenuVO> getMenuByPage(MenuParamVo param){
		Page<MenuVO> page = new Page<MenuVO>();
		int totalRecord = 0;
		int pageNo = param.getPageNo();
		int pageSize = param.getPageSize();
		int limitStart = (pageNo-1)*pageSize;	//起始条数
		int limitEnd = pageSize;	//查询条数
		SysMenuExample example = new SysMenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andDelFlagEqualTo(0);
		if(param.getPid()!=null) {
			criteria.andPidEqualTo(param.getPid());
		}
		if(param.getType()!=null) {
			criteria.andTypeEqualTo(param.getType());
		}
		if(StrUtils.isNotEmpty(param.getMenuName())) {
			criteria.andNameLike("%"+param.getMenuName()+"%");
		}
		//查询总数
		totalRecord = mapper.countByExample(example);
		if(totalRecord > 0) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
			List<SysMenu> list = mapper.selectByExample(example);
			List<MenuVO> voList = new ArrayList<MenuVO>();
			if(list!=null && !list.isEmpty()) {
				for(SysMenu menu : list) {
					MenuVO vo = new MenuVO();
					BeanUtil.copyProperties(menu, vo);
					//填充创建人
					if(menu.getCreateId()!=null) {
						SysUser sysUser = dataLocalCacheUtil.queryUser(menu.getCreateId().intValue());
						if(sysUser!=null) {
							vo.setCreateName(sysUser.getUsername());
						}
					}
					//填充更新人
					if(menu.getUpdateId()!=null) {
						SysUser sysUser = dataLocalCacheUtil.queryUser(menu.getUpdateId().intValue());
						if(sysUser!=null) {
							vo.setUpdateName(sysUser.getUsername());
						}
					}
					voList.add(vo);
				}
			}
			page.setRecords(voList);
		}
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotal(totalRecord);
		return page;
	}

	public List<SysMenu> getMenuByName(String name){
		SysMenuExample example = new SysMenuExample();
		example.createCriteria().andNameEqualTo(name);
		return mapper.selectByExample(example);
	} 
}
