package com.guiji.auth.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.service.MenuService;
import com.guiji.common.model.Page;
import com.guiji.component.aspect.SysOperaLog;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.vo.MenuParamVo;

@RestController
@RequestMapping("menu")
public class MenuController {

	@Autowired
	private MenuService service;
	
	private static String URL_MATCH="^/(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
	
	@RequestMapping("insert")
	public void insert(SysMenu menu,@RequestHeader Long userId) throws CheckConditionException{
		if(!"/".equals(menu.getUrl())&&!Pattern.matches(URL_MATCH, menu.getUrl())){
			throw new CheckConditionException("00010008");
		}
		
		menu.setCreateId(userId);
		menu.setUpdateId(userId);
		menu.setCreateTime(new Date());
		menu.setUpdateTime(new Date());
		menu.setDelFlag(0);
		if("2".equals(menu.getType())){
			menu.setPermission(menu.getUrl().replace("/", ":").substring(1));
		}
		service.insert(menu);
		
	}
	
	@RequestMapping("delete")
	public void delete(Integer id){
		service.delete(id);
	}

	@RequestMapping("update")
	public void update(SysMenu menu,@RequestHeader Long userId) throws CheckConditionException{
		if(!Pattern.matches(URL_MATCH, menu.getUrl())){
			throw new CheckConditionException("00010008");
		}
		menu.setUpdateId(userId);
		menu.setUpdateTime(new Date());
		service.update(menu);
	}
	
	@RequestMapping("getMenuById")
	public SysMenu getMenuById(Integer id){
		return service.getMenuById(id);
	}
	
	@RequestMapping("getMenus")
	public List<SysMenu> getMenus(@RequestHeader Long userId){
		return service.getMenus(userId);
	}
	
	@RequestMapping("getAllMenus")
	public Map<String,Object> getAllMenus(Long roleId){
		return service.getAllMenus(roleId);
	} 
	
	@RequestMapping("/getMenuByPage")
	public Page<Object> getMenuByPage(MenuParamVo param){
		return service.getRoleByPage(param);
	}
	
	@RequestMapping("getMenuByName")
	public List<SysMenu> getMenuByName(String name){
		return service.getMenuByName(name);
	}
}
