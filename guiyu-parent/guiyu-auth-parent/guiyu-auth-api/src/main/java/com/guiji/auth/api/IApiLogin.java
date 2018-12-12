package com.guiji.auth.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result.ReturnData;

@FeignClient("guiyu-cloud-zuul")
public interface IApiLogin {
	
	@RequestMapping("apiLogin")
	public ReturnData<String> apiLogin(@RequestParam("accessKey")String accessKey,@RequestParam("secretKey")String secretKey);
	
}
