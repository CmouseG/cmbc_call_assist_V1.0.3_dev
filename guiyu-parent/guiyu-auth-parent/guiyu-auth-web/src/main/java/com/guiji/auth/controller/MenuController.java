package com.guiji.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.service.MenuService;
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
	
	@RequestMapping("test")
	public String test(){
		return "ss33";
	}
	
	@RequestMapping("test23")
	public void test2(){
		System.out.println("sdfsd");
	}
}
