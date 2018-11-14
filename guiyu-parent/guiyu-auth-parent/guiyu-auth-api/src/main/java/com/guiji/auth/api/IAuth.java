package com.guiji.auth.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.user.dao.entity.SysUser;

@FeignClient("guiyu-cloud-zuul")
public interface IAuth {
	
	@PostMapping(value = "/user/getUserById")
	public SysUser getUserById(Long userId);
	
}
