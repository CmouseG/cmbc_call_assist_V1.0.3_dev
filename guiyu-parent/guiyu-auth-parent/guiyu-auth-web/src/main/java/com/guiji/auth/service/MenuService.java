package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.common.model.Page;
import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysMenuExample;
import com.guiji.user.vo.MenuParamVo;

@Service
public class MenuService {

	@Autowired
	private SysMenuMapper mapper;

	public void insert(SysMenu menu){
		
		mapper.insertSelective(menu);
	}

	public void delete(Long id){
		mapper.deleteByPrimaryKey(id);
	}

	public void update(SysMenu menu){
		mapper.updateByPrimaryKeySelective(menu);
	}

	public SysMenu getMenuById(Long id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Map<String,Object> getAllMenus(Long roleId){
		Map<String,Object> map=new HashMap<String,Object>();
		List<SysMenu> allMenu=mapper.getAllMenus();
		map.put("menus", parseTree(allMenu));
		
		List<Long> selected=mapper.getSelectedMenuId(roleId);
		map.put("selected", selected);
		return map;
	}
	
	public List<SysMenu> getMenus(Long userId){
		List<SysMenu> allMenu=mapper.getMenuByUserId(userId);
		return parseTree(allMenu);
	}
	
	public Map<String,String> getAllPermissions(){
		List<Map<String,String>> permList=mapper.getAllPermissions();
		Map<String,String> result=new HashMap<>();
		permList.forEach((item)->{
			result.put(item.get("url"), item.get("permission"));
		});
		
		return result;
	}
	
	private List<SysMenu> parseTree(List<SysMenu> allMenu){
		Map<Long,SysMenu> map=new HashMap<>();
		List<SysMenu> list=new ArrayList<>();
		allMenu.stream().forEach((item)->{
			Long pid=item.getPid();
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
		});
		return list;
	}
	
	public Page<Object> getRoleByPage(MenuParamVo param){
		Page<Object> page=new Page<>();
		int count=mapper.countByParamVo(param);
		List<Object> list=mapper.selectByParamVo(param);
		page.setTotal(count);
		page.setRecords(list);
		return page;
	}

	public List<SysMenu> getMenuByName(String name){
		SysMenuExample example = new SysMenuExample();
		example.createCriteria().andNameEqualTo(name);
		return mapper.selectByExample(example);
	} 
}
