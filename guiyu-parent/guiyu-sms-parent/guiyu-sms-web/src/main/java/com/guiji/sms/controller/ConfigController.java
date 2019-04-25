package com.guiji.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.ConfListRsp;
import com.guiji.sms.controller.bean.ConfReq;
import com.guiji.sms.service.ConfigService;

@RestController
public class ConfigController
{
	@Autowired
	ConfigService configService;
	
	/**
	 * 获取短信配置列表
	 */
	@PostMapping("queryConfigList")
	public ReturnData<ConfListRsp> queryConfigList(@RequestBody Condition condition,
													 @RequestHeader Long userId, 
													 @RequestHeader Integer authLevel, 
													 @RequestHeader String orgCode)
	{
		AuthLevelData authLevelData = new AuthLevelData(userId, authLevel, orgCode);
		return Result.ok(configService.queryConfigList(condition,authLevelData));
	}
	
	/**
	 * 新增短信配置
	 * @return 
	 */
	@Jurisdiction("smsCenter_smsConfigList_add")
	@PostMapping("addConfig")
	public void addConfig(@RequestBody ConfReq confReq,
						@RequestHeader Long userId,
						@RequestHeader String orgCode)
	{
		configService.addConfig(confReq,userId,orgCode);
	}
	
	/**
	 * 删除短信配置
	 * @return 
	 */
	@Jurisdiction(value = "smsCenter_smsConfigList_delete")
	@GetMapping("delConfig")
	public void delConfig(Integer id)
	{
		configService.delConfig(id);
	}
	
	/**
	 * 编辑短信配置
	 */
	@Jurisdiction("smsCenter_smsConfigList_edit")
	@PostMapping("updateConfig")
	public void updateConfig(@RequestBody ConfReq confReq,
						  @RequestHeader Long userId)
	{
		configService.updateConfig(confReq,userId);
	}
	
	/**
	 * 短信配置一键停止
	 */
	@Jurisdiction("smsCenter_smsConfigList_oneKeyStop")
	@GetMapping("stopConfig")
	public void stopConfig(Integer id,
						@RequestHeader Long userId)
	{
		configService.stopConfig(id,userId);
	}
	
	/**
	 * 短信配置一键启动
	 */
	@GetMapping("startConfig")
	public void startConfig(Integer id,
						@RequestHeader Long userId)
	{
		configService.startConfig(id,userId);
	}
	
	/**
	 * 短信配置审核
	 */
	@Jurisdiction(value = "smsCenter_smsConfigList_auditing")
	@GetMapping("auditingConfig")
	public void auditingConfig(Integer id,
						@RequestHeader Long userId)
	{
		configService.auditingConfig(id,userId);
	}
}
