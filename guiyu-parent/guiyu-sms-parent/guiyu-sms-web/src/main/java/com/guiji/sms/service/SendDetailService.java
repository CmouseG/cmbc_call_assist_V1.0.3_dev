package com.guiji.sms.service;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.SendDetailListRsp;

public interface SendDetailService
{
	/**
	 * 获取短信发送详情列表
	 */
	SendDetailListRsp querySendDetailList(Condition condition, AuthLevelData authLevelData);

	/**
	 * 删除短信发送详情
	 */
	void delSendDetail(Integer id);

}
