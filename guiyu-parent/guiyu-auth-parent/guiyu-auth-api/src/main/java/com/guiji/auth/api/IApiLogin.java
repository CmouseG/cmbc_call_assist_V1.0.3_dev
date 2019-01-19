package com.guiji.auth.api;

import com.guiji.user.dao.entity.SysUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("guiyu-auth-web")
public interface IApiLogin {
	
	@GetMapping("getUserByAccess")
	SysUser getUserByAccess(@RequestParam("accessKey")String accessKey, @RequestParam("secretKey")String secretKey);
	
}
