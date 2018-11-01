package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.vo.MenuTree;
import com.guiji.user.dao.MenuMapper;
import com.guiji.user.dao.entity.Menu;

@Service
public class MenuService {

	@Autowired
	private MenuMapper mapper;

	public void insert(Menu menu){
		mapper.insert(menu);
	}

	public void delete(String id){
		mapper.delete(id);
	}

	public void update(Menu menu){
		mapper.update(menu);
	}

	public Menu getMenuById(String id){
		return mapper.getMenuById(id);
	}
	
	public List<MenuTree> getMenus(String userId){
		List<Menu> allMenu=mapper.getMenuByUserId(userId);
		Map<String,MenuTree> map=new HashMap<>();
		List<MenuTree> list=new ArrayList<>();
		allMenu.stream().forEach((item)->{
			
			MenuTree node=new MenuTree();
			node.setParent(item);
			
			String pid=item.getPid();
			MenuTree parent=map.get(pid);
			if(parent==null&&"0".equals(pid)){
				list.add(node);
			}else{
				parent.getChild().add(node);
			}
			map.put(item.getId(), node);
		});
		return list;
	}

}
