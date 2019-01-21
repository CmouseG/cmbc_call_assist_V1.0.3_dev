package com.guiji.service;

import com.alibaba.fastjson.JSONObject;

public interface RecordService
{
	/*
	 * 保存云讯记录
	 */
	void saveYtxRecord(JSONObject returnData, String platformName, String phone);
}