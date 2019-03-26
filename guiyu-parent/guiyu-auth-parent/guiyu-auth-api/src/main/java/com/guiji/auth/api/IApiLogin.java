package com.guiji.auth.api;

import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("guiyu-auth-web")
public interface IApiLogin {
	
	@GetMapping("getUserByAccess")
	Result.ReturnData<SysUser> getUserByAccess(@RequestParam("accessKey")String accessKey, @RequestParam("secretKey")String secretKey);
	
	@GetMapping("getUserByAccess")
	Result.ReturnData<Boolean> WxLogin(@RequestParam("userName")String userName, @RequestParam("password")String password);
	
}
