package com.guiji.ai.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtil
{
	// 对象转JSON字符串
	public static String bean2Json(Object obj)
	{
		return JSON.toJSONString(obj);
	}

	// JSON字符串转对象
	public static <T> T json2Bean(String json, Class<T> clazz)
	{
		return JSON.parseObject(json, clazz);
	}
}
