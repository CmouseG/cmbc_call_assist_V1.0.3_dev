package com.guiji.sms.controller;

import java.util.List;

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
import com.guiji.sms.controller.bean.TunnelAddReq;
import com.guiji.sms.controller.bean.TunnelListRsp;
import com.guiji.sms.controller.bean.TunnelParamsRsp;
import com.guiji.sms.controller.bean.TunnelSendTestReq;
import com.guiji.sms.service.TunnerlService;

@RestController
public class TunnelController
{
	@Autowired
	TunnerlService tunnerlService;
	
	/**
	 * 获取短信通道列表
	 */
	@PostMapping("queryTunnelList")
	public ReturnData<TunnelListRsp> queryTunnelList(@RequestBody Condition condition,
												 @RequestHeader Long userId, 
												 @RequestHeader Integer authLevel, 
												 @RequestHeader String orgCode)
	{
		AuthLevelData authLevelData = new AuthLevelData(userId, authLevel, orgCode);
		return Result.ok(tunnerlService.queryTunnelList(condition,authLevelData));
	}
	
	/**
	 * 新增短信通道
	 */
	@Jurisdiction("smsCenter_smsManage_add")
	@PostMapping("addTunnel")
	public void addTunnel(@RequestBody TunnelAddReq tunnelAddReq,
						@RequestHeader Long userId,
						@RequestHeader String orgCode)
	{
		tunnerlService.addTunnel(tunnelAddReq,userId,orgCode);
	}
	
	/**
	 * 删除短信通道
	 */
	@Jurisdiction("smsCenter_smsManage_delete")
	@GetMapping("delTunnel")
	public void delTunnel(Integer id, String tunnelName)
	{
		tunnerlService.delTunnel(id,tunnelName);
	}
	
	/**
	 * 获取所有的通道名称和内容形式
	 */
	@GetMapping("queryAllTunnelNameWithContentType")
	public ReturnData<List<TunnelParamsRsp>> queryAllTunnelNameWithContentType(
													@RequestHeader Long userId, 
													@RequestHeader Integer authLevel, 
													@RequestHeader String orgCode)
	{
		AuthLevelData authLevelData = new AuthLevelData(userId, authLevel, orgCode);
		return Result.ok(tunnerlService.queryAllTunnelNameWithContentType(authLevelData));
	}
	
	/**
	 * 测试发送
	 */
	@Jurisdiction("smsCenter_smsManage_test")
	@PostMapping("testSend")
	public void testSend(@RequestBody TunnelSendTestReq tunnelSendTestReq,
					@RequestHeader Long userId, 
					@RequestHeader String orgCode)
	{
		tunnelSendTestReq.setUserId(userId.intValue());
		tunnelSendTestReq.setOrgCode(orgCode);
		tunnerlService.testSend(tunnelSendTestReq);
	}

}
