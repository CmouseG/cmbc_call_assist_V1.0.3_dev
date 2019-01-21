package com.guiji.service;

import java.util.List;

import com.guiji.sms.vo.PlatformListReqVO;
import com.guiji.sms.vo.PlatformListRspVO;
import com.guiji.sms.vo.PlatformReqVO;
import com.guiji.sms.vo.PlatformRspVO;

public interface PlatformService
{

	/**
	 * 获取短信平台列表
	 */
	PlatformListRspVO getPlatformList(PlatformListReqVO platformListReq);

	/**
	 * 新增短信平台
	 */
	void addPlatform(PlatformReqVO platformReq, Long userId);

	/**
	 * 获取短信平台名称列表及参数
	 */
	List<PlatformRspVO> getPlatformNameListWithParams();
	

}
