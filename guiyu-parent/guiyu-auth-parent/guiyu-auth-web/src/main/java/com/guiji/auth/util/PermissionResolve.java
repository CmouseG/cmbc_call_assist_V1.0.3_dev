package com.guiji.auth.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.auth.service.MenuService;

@Component
public class PermissionResolve {
	
	@Autowired
	private MenuService menuService;
	
	private Map<String,String> map;
	
	public String parse(String key){
		if(map==null){
			map=menuService.getAllPermissions();
		}
		return map.get(key);
	}
	
	public void clean(){
		map=null;
	}

}
