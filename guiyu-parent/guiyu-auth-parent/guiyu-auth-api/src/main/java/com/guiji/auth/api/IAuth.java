package com.guiji.auth.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.component.result.Result.ReturnData;

@FeignClient("auth")
public interface IAuth {
	
	@PostMapping(value = "/user/getUserId")
	public ReturnData<Long> getUserId();
	
}
