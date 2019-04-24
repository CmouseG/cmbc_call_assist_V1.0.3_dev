package com.guiji.sms.utils;

import org.json.JSONObject;
import org.json.XML;

public class XmlUtil
{
	/**
	 * xml字符串转json对象
	 */
	public static JSONObject xmlStr2Json(String xmlStr)
	{
		return XML.toJSONObject(xmlStr);
	}
	
	/**
	 * xml字符串转json字符串
	 */
	public static String xmlStr2JsonStr(String xmlStr)
	{
		return XML.toJSONObject(xmlStr).toString();
	}
	
}
