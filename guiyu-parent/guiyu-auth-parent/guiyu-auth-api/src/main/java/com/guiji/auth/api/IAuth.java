package com.guiji.auth.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.component.result.Result.ReturnData;

@FeignClient("guiyu-cloud-zuul")
public interface IAuth {
	
	@PostMapping(value = "/getUserId")
	public ReturnData<Long> getUserId();
	
}
