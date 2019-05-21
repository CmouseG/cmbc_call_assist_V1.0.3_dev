package com.guiji.sms.service;

import java.util.List;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.TunnelAddReq;
import com.guiji.sms.controller.bean.TunnelListRsp;
import com.guiji.sms.controller.bean.TunnelParamsRsp;
import com.guiji.sms.controller.bean.TunnelSendTestReq;

public interface TunnerlService
{
	/**
	 * 获取短信通道列表
	 */
	TunnelListRsp queryTunnelList(Condition condition, AuthLevelData authLevelData);
	
	/**
	 * 新增短信通道
	 */
	void addTunnel(TunnelAddReq tunnelAddReq, Long userId, String orgCode);

	/**
	 * 删除短信通道
	 */
	void delTunnel(Integer id, String tunnelName);

	/**
	 * 获取所有的通道名称和内容形式
	 */
	List<TunnelParamsRsp> queryAllTunnelNameWithContentType(AuthLevelData authLevelData);

	/**
	 * 测试发送
	 */
	void testSend(TunnelSendTestReq tunnelSendTestReq);

}
