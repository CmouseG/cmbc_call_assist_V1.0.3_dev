package com.guiji.cloud.zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.cloud.zuul.service.ZuulService;
@Component
public class PermissionResolve {
	
	@Autowired
	private ZuulService zuulService;
	
	public String parse(String key){
		int index=key.indexOf("/", 1);
		if(index==-1){
			key=key.substring(index,key.length());
			return zuulService.getPermissionsByUrl(key);
		}
		return "";
	}

}
