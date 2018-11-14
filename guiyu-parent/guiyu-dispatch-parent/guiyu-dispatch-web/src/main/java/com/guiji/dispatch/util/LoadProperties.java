package com.guiji.dispatch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class LoadProperties {
	private static final Logger logger = LoggerFactory.getLogger(LoadProperties.class);

	private static Properties prop = new Properties();

	static {
		try {
			String os = System.getProperty("os.name");
			logger.info("os:" + os);
			if (os.toLowerCase().startsWith("win")) {
				File file = ResourceUtils.getFile("classpath:jdbc_dev.properties");
				InputStream in = new FileInputStream(file);
				prop.load(in);
			} else {
				InputStream resourceAsStream = LoadProperties.class.getClassLoader()
						.getResourceAsStream("classpath:jdbc_dev.properties");
				prop.load(resourceAsStream);
			}
		} catch (Exception e) {
			logger.error("error",e);
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	/**
	 * 鏇存柊璧勬簮鏂囦欢
	 * 
	 * @param keyname
	 * @param keyvalue
	 */
//	public static void updateProperties(String keyname, String keyvalue) {
//		OutputStream fos = null;
//		try {
//			File file = ResourceUtils.getFile("classpath:authorization.properties");
//			fos = new FileOutputStream(file);
//			prop.setProperty(keyname, keyvalue);
//			prop.store(fos, "andieguo modify" + new Date().toString());
//		} catch (IOException e) {
//			System.err.println("LoadProperties error");
//		}finally{
//			fos.close();
//		}
//	}

	public static Map<String, String> json2map(String str_json) {
		Map<String, String> res = null;
		try {
			Gson gson = new Gson();
			res = gson.fromJson(str_json, new TypeToken<Map<String, String>>() {
			}.getType());
		} catch (JsonSyntaxException e) {
		}
		return res;
	}

	public static void main(String[] args) {
		Map<String, String> json2map = json2map(LoadProperties.getProperty("1"));
//		LoadProperties.updateProperties("1", "");
		System.out.println(json2map);
		// Gson gson = new Gson();
		// Map<String, Object> map = new HashMap<String, Object>();
		// map = gson.fromJson(LoadProperties.getProperty("1"), map.getClass());
		// System.out.println(map.get(key));
	}
}
