package com.guiji.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class MapUtil {
	
	/**
	 * String转Map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map handleStringToMap(String StrParams) throws Exception {
		
		Map map = new HashMap();
		if (StringUtils.isBlank(StrParams)) {
			throw new Exception("待转换为Map的字符串不能为空");
		}
		String[] params = StrParams.split("&");	
		for (String param : params) {
			if (StringUtils.isBlank(param)) {
				throw new Exception("待转换为Map的字符串格式不正确");
			}
			String[] values = param.split("=");
			if (values.length != 2) {
				throw new Exception("待转换为Map的字符串格式不正确");
			}
			map.put(values[0], values[1]);
		}
		return map;
	}

	/**
	 * Map转String（key=value&key=value……）
	 */
	public static <K, V> String handleMapToString(Map<K, V> map) throws Exception {
		
		Iterator<Entry<K, V>> i = map.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		for (;;) {
			Map.Entry<K, V> m = i.next();
			K key = m.getKey();
			V value = m.getValue();
			sb.append(key);
			sb.append('=');
			sb.append(value);
			if (!i.hasNext()) {
				return sb.toString();
			}
			sb.append('&');
		}
	}

	/**
	 * is_null = 0 ：不允许为空 
	 * is_null = 1 ：允许为空
	 */
	@SuppressWarnings("rawtypes")
	public static String getString(Map map, String key, int is_null) throws Exception {
		
		Object obj = map.get(key);
		if (obj == null || obj.toString().length() <= 0) {
			if (is_null == 1) {
				return null;
			} else { // 如果没指定是否允许为空，默认不允许为空
				throw new Exception(key + "不能为空");
			}
		}
		return obj.toString();
	}

	/**
	 * is_null = 0 ：不允许为空 
	 * is_null = 1 ：允许为空
	 */
	@SuppressWarnings("rawtypes")
	public static int getInt(Map map, String key, int is_null) throws Exception {
		
		Object obj = map.get(key);
		if (obj == null || obj.toString().length() <= 0) {
			if (is_null == 1) {
				return 0;
			} else { // 如果没指定是否允许为空，默认不允许为空
				throw new Exception(key + "不能为空");
			}
		}
		return Integer.parseInt(obj.toString());
	}

	/**
	 * is_null = 0 ：不允许为空 
	 * is_null = 1 ：允许为空
	 */
	@SuppressWarnings("rawtypes")
	public static long getLong(Map map, String key, int is_null) throws Exception {
		
		Object obj = map.get(key);
		if (obj == null || obj.toString().length() <= 0) {
			if (is_null == 1) {
				return -1;
			} else { // 如果没指定是否允许为空，默认不允许为空
				throw new Exception(key + "不能为空");
			}
		}
		return Long.parseLong(obj.toString());
	}
	
	/**
	 * is_null = 0 ：不允许为空 
	 * is_null = 1 ：允许为空
	 */
	@SuppressWarnings("rawtypes")
	public static float getFloat(Map map, String key, int is_null) throws Exception {
		
		Object obj = map.get(key);
		if (obj == null || obj.toString().length() <= 0) {
			if (is_null == 1) {
				return -1;
			} else { // 如果没指定是否允许为空，默认不允许为空
				throw new Exception(key + "不能为空");
			}
		}
		return Float.parseFloat(obj.toString());
	}
	
	/**
	 * is_null = 0 ：不允许为空 
	 * is_null = 1 ：允许为空
	 */
	@SuppressWarnings("rawtypes")
	public static double getDouble(Map map, String key, int is_null) throws Exception {
		
		Object obj = map.get(key);
		if (obj == null || obj.toString().length() <= 0) {
			if (is_null == 1) {
				return -1.00;
			} else { // 如果没指定是否允许为空，默认不允许为空
				throw new Exception(key + "不能为空");
			}
		}
		return Double.parseDouble(obj.toString());
	}
}
