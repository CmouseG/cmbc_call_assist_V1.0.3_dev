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
	 * ��ѯ���в˵�-ֻ��˵�
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
	 * Ȩ�޹����в˵�ѡ��
	 * ��ȡ��ǰ�û����Ը�Ȩ�����в˵����Լ��Ѿ�ѡ��Ĳ˵��б�
	 * @param roleId ��ɫID
	 * @param userId ��ǰ�����û�
	 * @param orgCode ��ǰ�����û�������ҵ
	 * @param targetOrgCode Ŀ����ҵ
	 * @return
	 */
	public Map<String,Object> getOrgRoleAuthMenus(Long roleId,Integer userId,String orgCode,String targetOrgCode){
		Map<String,Object> map=new HashMap<String,Object>();
		//��ȡ��ǰ�û���Ȩ�޷�Χ
		PrivlegeAuth privlegeAuth = privilegeService.getUserAuthLevel(userId, orgCode, targetOrgCode);
		if(privlegeAuth!=null) {
			//��ѯ��ǰ�û��˵���Χ�б�
			List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(privlegeAuth.getAuthType(), privlegeAuth.getAuthId());
			//ƴװ���νṹ
			if(allMenus!=null) {
				map.put("menus", parseTree(allMenus,true));
			}
		}
		List<Long> selected= new ArrayList<Long>();
		if(roleId!=null) {
			//��ѯ�ý�ɫ�Ĳ˵�Ȩ��
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
	 * ��Ʒ������ѡ��˵�ѡ��״̬
	 * @param productId
	 * @return
	 */
	public Map<String,Object> getProductAuthMenus(Long productId){
		Map<String,Object> map=new HashMap<String,Object>();
		List<SysMenu> allMenu=mapper.getAllMenus();
		//��ȡ��ǰ�û���Ȩ�޷�Χ
		if(allMenu!=null) {
			//ƴװ���νṹ
			map.put("menus", parseTree(allMenu,true));
		}
		List<Long> selected= new ArrayList<Long>();
		if(productId!=null) {
			//��ѯ�ò�Ʒ�Ĳ˵�Ȩ��
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
	 * ��֯������ѡ��˵�ѡ��״̬
	 * @param productId
	 * @return
	 */
	public Map<String,Object> getOrgAuthMenus(Long productId,String parentOrgCode,String targetOrgCode){
		Map<String,Object> map=new HashMap<String,Object>();
		//��ȡ��ǰ�û���Ȩ�޷�Χ
		if(productId!=null) {
			//��ѯ��Ʒ��Χ�б�
			List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(AuthObjTypeEnum.PRODUCT.getCode(), productId.toString());
			//ƴװ���νṹ
			if(allMenus!=null) {
				map.put("menus", parseTree(allMenus,true));
			}
		}else if(StrUtils.isNotEmpty(parentOrgCode)) {
			//��ѯ�ϼ���ҵ�˵���Χ�б�
			SysOrganization parentOrganization = organizationService.getOrgByCode(parentOrgCode);
			List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(AuthObjTypeEnum.ORG.getCode(), parentOrganization.getId().toString());
			//ƴװ���νṹ
			if(allMenus!=null) {
				map.put("menus", parseTree(allMenus,true));
			}
		}else {
			throw new GuiyuException("��Ʒ��ź��ϼ���ҵ��Ų��ܶ�Ϊ��!");
		}
		List<Long> selected= new ArrayList<Long>();
		if(StrUtils.isNotEmpty(targetOrgCode)) {
			//��ѯ����֯�Ĳ˵�Ȩ��
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
	 * �����û���Ȩ�Ĳ˵��б��Լ���ť�б�
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getMenus(Long userId){
		if(userId!=null) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<SysRole> roleList = userService.getRoleByUserId(Long.valueOf(userId));
			if(roleList!=null && !roleList.isEmpty()) {
				//�����û�ֻ��1����ɫ
				SysRole sysRole = roleList.get(0);
				List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(AuthObjTypeEnum.ROLE.getCode(), sysRole.getId().toString());
				Map<String,SysMenu> buttonMap = new HashMap<String,SysMenu>();
				Iterator<SysMenu> it = allMenus.iterator();
				while(it.hasNext()){
					SysMenu sysMenu = it.next();
				    if(MenuTypeEnum.BUTTON.getCode()==sysMenu.getType()){
				    	//���밴ť�б�
				    	buttonMap.put(sysMenu.getUrl(), sysMenu);
				        //�Ӳ˵��б����Ƴ�
				    	it.remove();
				    }
				}
				if(allMenus!=null) {
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
	 * ���˵�תΪ���νṹ��
	 * @param allMenu
	 * @param filterSysMenuFlag �Ƿ�Ҫ���˵�ϵͳ�˵����ܶ�ط��Ĳ˵���ʾ��Ҫ����ģ���Щ�˵����ܷ����ȥ��
	 * @return
	 */
	private List<SysMenu> parseTree(List<SysMenu> allMenu,boolean filterSysMenuFlag){
		Map<Integer,SysMenu> map=new HashMap<>();
		List<SysMenu> list=new ArrayList<>();
		for(SysMenu item : allMenu) {
			if(filterSysMenuFlag && item.getSysType()!=null && 1==item.getSysType()) {
				//���˵�ϵͳ�˵�
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
	 * ��ҳ��ѯ�˵�
	 * @param param
	 * @return
	 */
	public Page<MenuVO> getMenuByPage(MenuParamVo param){
		Page<MenuVO> page = new Page<MenuVO>();
		int totalRecord = 0;
		int pageNo = param.getPageNo();
		int pageSize = param.getPageSize();
		int limitStart = (pageNo-1)*pageSize;	//��ʼ����
		int limitEnd = pageSize;	//��ѯ����
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
		//��ѯ����
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
					//��䴴����
					if(menu.getCreateId()!=null) {
						SysUser sysUser = dataLocalCacheUtil.queryUser(menu.getCreateId().intValue());
						if(sysUser!=null) {
							vo.setCreateName(sysUser.getUsername());
						}
					}
					//��������
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
