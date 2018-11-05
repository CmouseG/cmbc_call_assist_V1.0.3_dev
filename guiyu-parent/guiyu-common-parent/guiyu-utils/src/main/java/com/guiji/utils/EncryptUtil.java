/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** 
 *@Description: 加密工具（密码使用）
 *@Author:weiyunbo
 *@date:2018年7月2日 下午4:12:30
 *@history:
 *@Version:v1.0 
 */
public class EncryptUtil {
	
	/**
	 * 密码加密
	 * @date:2018年7月2日 下午4:13:54 
	 * @param rawPassword
	 * @return String
	 */
	public static String encode(CharSequence rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}

	
	/**
	 * 密码匹配
	 * @date:2018年7月2日 下午4:14:04 
	 * @param rawPassword
	 * @param encodedPassword
	 * @return boolean
	 */
	public static boolean matches(CharSequence rawPassword, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}
}
  
