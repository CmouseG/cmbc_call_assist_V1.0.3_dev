package com.guiji.sms.platform;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface ISendMessage
{
	/*
	 * 发送短信接口（所有平台实现此接口）
	 */
	void sendMessage(JSONObject params, List<String> phoneList);
}
