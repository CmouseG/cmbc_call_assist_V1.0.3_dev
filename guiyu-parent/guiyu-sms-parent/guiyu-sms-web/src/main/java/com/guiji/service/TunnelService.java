package com.guiji.service;

import java.util.List;

import com.guiji.sms.vo.TunnelListReqVO;
import com.guiji.sms.vo.TunnelListRspVO;
import com.guiji.sms.vo.TunnelReqVO;

public interface TunnelService
{
	/**
	 * 获取短信通道列表
	 */
	TunnelListRspVO getTunnelList(TunnelListReqVO tunnelListReq);

	/**
	 * 新增短信通道
	 * @param userId 
	 */
	void addTunnel(TunnelReqVO tunnelReq, Long userId);

	/**
	 * 删除短信通道
	 */
	void delTunnel(Integer id);
	
	/**
	 * 获取短信通道名称列表
	 */
	List<String> getTunnelNameList();

}
