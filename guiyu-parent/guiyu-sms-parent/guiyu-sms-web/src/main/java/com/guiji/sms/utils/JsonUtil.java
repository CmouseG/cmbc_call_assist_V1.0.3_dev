package com.guiji.sms.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil
{
	/**
	 * 对象转Json字符串
	 */
	public static String bean2JsonStr(Object obj)
	{
		return JSON.toJSONString(obj);
	}

	/**
	 * Json字符串转Json对象
	 */
	public static JSONObject jsonStr2JsonObj(String jsonStr)
	{
		return JSON.parseObject(jsonStr);
	}

	/**
	 * Json字符串转对象
	 */
	public static <T> T jsonStr2Bean(String jsonStr, Class<T> clazz)
	{
		return JSON.parseObject(jsonStr, clazz);
	}

	/**
	 * Json字符串转List
	 */
	public static <T> List<T> jsonStr2List(String jsonStr, Class<T> clazz)
	{
		return JSON.parseArray(jsonStr, clazz);
	}

	/**
	 * Json字符串转数组
	 */
	public static <T> Object[] jsonStr2Array(String json, Class<T> clazz)
	{
		return JSON.parseArray(json, clazz).toArray();
	}
}
