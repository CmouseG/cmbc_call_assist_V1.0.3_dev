package com.guiji.cloud.zuul.service;

import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuService {

	@Autowired
	private SysMenuMapper mapper;

	public void insert(SysMenu menu){
		menu.setCreateTime(new Date());
		menu.setUpdateTime(new Date());
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

}
