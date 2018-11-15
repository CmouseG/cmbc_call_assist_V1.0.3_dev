package com.guiji.auth.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysUser;

@FeignClient("guiyu-auth-web")
public interface IAuth {
	
	@PostMapping(value = "/user/getUserById")
	public ReturnData<SysUser> getUserById(Long userId);
	
}
