package com.guiji.sms.controller;

import java.util.List;

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
import com.guiji.sms.controller.bean.PlatAddReq;
import com.guiji.sms.controller.bean.PlatListRsp;
import com.guiji.sms.controller.bean.PlatParamsRsp;
import com.guiji.sms.service.PlatformService;

@RestController
@RequestMapping("sms")
public class PlatformController
{
	@Autowired
	PlatformService platformService;
	
	/**
	 * 获取短信平台列表
	 */
	@PostMapping("queryPlatformList")
	public ReturnData<PlatListRsp> queryPlatformList(@RequestBody Condition condition,
													 @RequestHeader Long userId, 
													 @RequestHeader Integer authLevel, 
													 @RequestHeader String orgCode)
	{
		AuthLevelData authLevelData = new AuthLevelData(userId, authLevel, orgCode);
		return Result.ok(platformService.queryPlatformList(condition,authLevelData));
	}
	
	/**
	 * 新增短信平台
	 */
	@Jurisdiction("smsCenter_platformManage_add")
	@PostMapping("addPlatform")
	public void addPlatform(@RequestBody PlatAddReq platAddReq,
						 @RequestHeader Long userId,
						 @RequestHeader String orgCode)
	{
		platformService.addPlatform(platAddReq,userId,orgCode);
	}
	
	/**
	 * 删除短信平台
	 */
	@Jurisdiction(value = "smsCenter_platformManage_delete")
	@GetMapping("delPlatform")
	public void delPlatform(Integer id, String platformName)
	{
		platformService.delPlatform(id,platformName);
	}
	
	/**
	 * 获取所有的平台名称和对应的参数列表
	 */
	@GetMapping("queryAllPlatNameWithParams")
	public ReturnData<List<PlatParamsRsp>> queryAllPlatNameWithParams()
	{
		return Result.ok(platformService.queryAllPlatNameWithParams());
	}
}
