package com.guiji.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.service.MenuService;
import com.guiji.auth.vo.MenuTree;
import com.guiji.user.dao.entity.Menu;

@RestController
@RequestMapping("menu")
public class MenuController {

	@Autowired
	private MenuService service;

	@RequestMapping("insert")
	public void insert(Menu menu){
		service.insert(menu);
	}

	@RequestMapping("delete")
	public void delete(String id){
		service.delete(id);
	}

	@RequestMapping("update")
	public void update(Menu menu){
		service.update(menu);
	}

	@RequestMapping("getMenuById")
	public Menu getMenuById(String id){
		return service.getMenuById(id);
	}
	
	@RequestMapping("getMenus")
	public List<MenuTree> getMenus(String userId){
		return service.getMenus(userId);
	}
}
