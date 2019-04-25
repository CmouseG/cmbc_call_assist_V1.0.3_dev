package com.guiji.sms.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.guiji.sms.api.bean.SendMReqVO;

/**
 * SMS对外服务
 */
@FeignClient("guiyu-sms-web")
public interface ISms
{
	/**
	 * 发短信
	 */
	@PostMapping("sendMessage")
	public void sendMessage(SendMReqVO sendMsgReq);
}
