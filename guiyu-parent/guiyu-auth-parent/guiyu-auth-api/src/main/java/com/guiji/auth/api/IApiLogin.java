package com.guiji.auth.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysRole;

@FeignClient("guiyu-cloud-zuul")
public interface IApiLogin {
	
	@RequestMapping("apiLogin")
	public ReturnData<List<SysRole>> apiLogin(@RequestParam("accessKey")String accessKey,@RequestParam("secretKey")String secretKey);
	
}
