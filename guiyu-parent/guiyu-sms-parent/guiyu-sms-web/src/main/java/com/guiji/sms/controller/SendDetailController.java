package com.guiji.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.SendDetailListRsp;
import com.guiji.sms.service.SendDetailService;

@RestController
@RequestMapping("sms")
public class SendDetailController
{
	@Autowired
	SendDetailService sendDetailService;
	
	/**
	 * 获取短信发送详情列表
	 */
	@PostMapping("querySendDetailList")
	public ReturnData<SendDetailListRsp> querySendDetailList(@RequestBody Condition condition,
														 @RequestHeader Long userId, 
														 @RequestHeader Integer authLevel, 
														 @RequestHeader String orgCode)
	{
		AuthLevelData authLevelData = new AuthLevelData(userId, authLevel, orgCode);
		return Result.ok(sendDetailService.querySendDetailList(condition,authLevelData));
	}
	
	/**
	 * 删除短信发送详情
	 */
	@Jurisdiction(value = "smsCenter_smsSendList_delete")
	@GetMapping("delSendDetail")
	public void delSendDetail(Integer id)
	{
		sendDetailService.delSendDetail(id);
	}
}
