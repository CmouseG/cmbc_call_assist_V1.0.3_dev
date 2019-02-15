package com.guiji.sms.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.sms.vo.MsgResultVO;
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
	
	/**
	 * 获取短信发送结果
	 */
	@ApiOperation(value = "获取短信发送结果")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "planuuid", value = "挂机短信唯一标识", required = true) 
	})
	@GetMapping(value = "getMsgResult")
	public ReturnData<MsgResultVO> getMsgResult(String planuuid);
}
