package com.guiji.cloud.zuul.config;

import java.util.Map;

import com.guiji.cloud.zuul.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
