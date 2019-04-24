package com.guiji.sms.service;

import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.PlatAddReq;

import java.util.List;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.PlatListRsp;
import com.guiji.sms.controller.bean.PlatParamsRsp;

public interface PlatformService
{

	/**
	 * 获取短信平台列表
	 */
	PlatListRsp queryPlatformList(Condition condition, AuthLevelData authLevelData);

	/**
	 * 新增短信平台
	 */
	void addPlatform(PlatAddReq platAddReq, Long userId, String orgCode);

	/**
	 * 删除短信平台
	 */
	void delPlatform(Integer id, String platformName);

	/**
	 * 获取所有的平台名称和对应的参数列表
	 */
	List<PlatParamsRsp> queryAllPlatNameWithParams();

}
