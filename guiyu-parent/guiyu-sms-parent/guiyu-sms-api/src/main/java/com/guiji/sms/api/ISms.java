package com.guiji.sms.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.guiji.sms.vo.SendMReqVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@FeignClient("guiyu-sms-web")
public interface ISms
{	
	/**
	 * 发短信
	 */
	@ApiOperation(value = "发短信")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "sendMReq", value = "发短信请求对象", required = true) 
	})
	@PostMapping(value = "sendMessage")
    public void sendMessage(@RequestBody SendMReqVO sendMReq);
}
