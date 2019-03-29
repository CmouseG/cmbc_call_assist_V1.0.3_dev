package com.guiji.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.constants.AuthConstants;
import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.MenuTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.model.MenuVO;
import com.guiji.auth.service.MenuService;
import com.guiji.auth.service.PrivilegeService;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysMenuExample;
import com.guiji.user.vo.MenuParamVo;
import com.guiji.utils.BeanUtil;

@RestController
@RequestMapping("menu")
public class MenuController {
	@Autowired
	private SysMenuMapper mapper;
	@Autowired
	private MenuService service;
	@Autowired
	PrivilegeService privilegeService;
	
	private static String URL_MATCH="^/(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
	
	@Jurisdiction("system_menu_add")
	@RequestMapping("insert")
	public void insert(SysMenu menu,@RequestHeader Long userId,@RequestHeader String orgCode) throws CheckConditionException{
		if(1==menu.getType() && !"/".equals(menu.getUrl())&&!Pattern.matches(URL_MATCH, menu.getUrl())){
			throw new CheckConditionException("00010008");
		}
		menu.setCreateId(userId);
		menu.setUpdateId(userId);
		menu.setCreateTime(new Date());
		menu.setUpdateTime(new Date());
		menu.setDelFlag(0);
		if(2==menu.getType()){
			menu.setPermission(menu.getUrl().replace("/", ":"));
		}
		//新增菜单
		service.insert(menu);
		List<String> buttonList = new ArrayList<String>();
		if(1==menu.getType()) {
			//新增初始化的查询按钮
			SysMenu queryButton = new SysMenu();
			BeanUtil.copyProperties(menu, queryButton);
			queryButton.setId(null);
			queryButton.setPid(menu.getId()); //父级id
			queryButton.setName(menu.getName()+"-查询");
			queryButton.setLevel(menu.getLevel()+1);
			queryButton.setType(MenuTypeEnum.BUTTON.getCode());
			queryButton.setPermission(queryButton.getUrl().replace("/", ":").substring(1));
			queryButton.setUrl(queryButton.getUrl().replace("/", "_").substring(1)+"_defquery");
			service.insert(queryButton);
			//给超管和系统组织 自动关联
			buttonList.add(queryButton.getId().toString());
		}else if(2==menu.getType()) {
			//如果是按钮，直接挂给超管和系统组织
			buttonList.add(menu.getId().toString());
		}
		//顶层组织增加关系
		privilegeService.savePrivlege(userId.intValue(), orgCode, AuthObjTypeEnum.ORG.getCode(), AuthConstants.ROOT_ORG_CODE, ResourceTypeEnum.MENU.getCode(),buttonList);
		//超管增加关系
		privilegeService.savePrivlege(userId.intValue(), orgCode, AuthObjTypeEnum.ROLE.getCode(), AuthConstants.ROOT_ROLE_ADMIN, ResourceTypeEnum.MENU.getCode(),buttonList);
	}
	
	@Jurisdiction("system_menu_delete")
	@RequestMapping("delete")
	public void delete(Integer id,@RequestHeader Long userId){
		SysMenu sysMenu = mapper.selectByPrimaryKey(id);
		if(sysMenu!=null) {
			service.delete(id);
			List<String> buttonList = new ArrayList<String>();
			if(MenuTypeEnum.BUTTON.getCode()==sysMenu.getType()) {
				//如果是按钮，那么删除资源
				buttonList.add(id.toString());
				//删除关系
				privilegeService.delPrivilegeTree(AuthConstants.ROOT_ORG_CODE,userId.intValue(), AuthObjTypeEnum.ORG.getCode(), AuthConstants.ROOT_ORG_CODE, buttonList, ResourceTypeEnum.MENU.getCode());
			}else if(MenuTypeEnum.MENU.getCode()==sysMenu.getType()) {
				//查询下级按钮
				SysMenuExample example = new SysMenuExample();
				example.createCriteria().andPidEqualTo(id).andTypeEqualTo(MenuTypeEnum.BUTTON.getCode());
				List<SysMenu> buttons = mapper.selectByExample(example);
				if(buttons!=null &&!buttons.isEmpty()) {
					for(SysMenu button : buttons) {
						service.delete(button.getId()); //删除按钮
						buttonList.add(button.getId().toString());
					}
					//删除关系
					privilegeService.delPrivilegeTree(AuthConstants.ROOT_ORG_CODE,userId.intValue(), AuthObjTypeEnum.ORG.getCode(), AuthConstants.ROOT_ORG_CODE, buttonList, ResourceTypeEnum.MENU.getCode());
				}
			}
		}
	}

	@Jurisdiction("system_menu_edit")
	@RequestMapping("update")
	public void update(SysMenu menu,@RequestHeader Long userId) throws CheckConditionException{
		if(1==menu.getType() && !"/".equals(menu.getUrl())&&!Pattern.matches(URL_MATCH, menu.getUrl())){
			throw new CheckConditionException("00010008");
		}
		SysMenu extMenu = service.getMenuById(menu.getId());	//库里菜单数据
		if(!extMenu.getUrl().equals(menu.getUrl()) || !extMenu.getName().equals(menu.getName())) {
			//菜单名称或者url变更了，更新默认查询按钮
			SysMenuExample example = new SysMenuExample();
			example.createCriteria().andPidEqualTo(menu.getId()).andTypeEqualTo(2).andDelFlagEqualTo(0).andUrlEqualTo(extMenu.getUrl()+"/defquery");
			List<SysMenu> queryButtonList = mapper.selectByExample(example);
			if(queryButtonList!=null && !queryButtonList.isEmpty()) {
				queryButtonList.get(0).setName(menu.getName()+"-查询");
				queryButtonList.get(0).setUrl(menu.getUrl().replace("/", "_")+"_defquery");
				queryButtonList.get(0).setPermission(menu.getUrl().replace("/", ":").substring(1));
				mapper.updateByPrimaryKey(queryButtonList.get(0));
			}
		}
		BeanUtil.copyProperties(menu, extMenu);
		extMenu.setUpdateId(userId);
		extMenu.setUpdateTime(new Date());
		service.update(extMenu);
	}
	
	@RequestMapping("getMenuById")
	public SysMenu getMenuById(Integer id){
		return service.getMenuById(id);
	}
	
	@RequestMapping("getMenus")
	public Map<String,Object> getMenus(@RequestHeader Long userId){
		return service.getMenus(userId);
	}
	
	
	@RequestMapping("/getMenuByPage")
	public Page<MenuVO> getMenuByPage(MenuParamVo param){
		return service.getMenuByPage(param);
	}
	
	@RequestMapping("getMenuByName")
	public List<SysMenu> getMenuByName(String name){
		return service.getMenuByName(name);
	}
	
	/**
	 * 查询所有菜单
	 * @param roleId
	 * @return
	 */
	@RequestMapping("getAllMenus")
	public List<SysMenu> getAllMenus(){
		return service.getAllMenus();
	}
	
	/**
	 * 产品管理中选择菜单选中状态
	 * @param productId
	 * @return
	 */
	@RequestMapping("getProductAuthMenus")
	public Map<String,Object> getProductAuthMenus(
			Long productId){
		return service.getProductAuthMenus(productId);
	} 
	
	
	/**
	 * 查询组织的菜单权限，总的如果传产品那么按产品查，如果给上级企业编号按上级企业编号查询范围
	 * @param productId
	 * @param parentOrgCode
	 * @param targetOrgCode
	 * @return
	 */
	@RequestMapping("getOrgAuthMenus")
	public Map<String,Object> getAuthMenus(
			Long productId,
			String parentOrgCode,
			String targetOrgCode){
		return service.getOrgAuthMenus(productId,parentOrgCode,targetOrgCode);
	} 
	
	
	/**
	 * 权限管理中菜单选择
	 * @param roleId
	 * @param targetOrgCode
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	@RequestMapping("getOrgRoleAuthMenus")
	public Map<String,Object> getOrgRoleAuthMenus(
			Long roleId,
			String targetOrgCode,
			@RequestHeader Integer userId,
			@RequestHeader String orgCode){
		return service.getOrgRoleAuthMenus(roleId,userId,orgCode,targetOrgCode);
	} 
}
