package com.guiji.sms.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密/解密 工具类
 * @author Sun
 */
public class CryptographyUtil
{
	/**
	 * base64加密
	 */
	public static String encodeBase64(String str)
	{
		return new String(Base64.encodeBase64(str.getBytes()));
	}
	
	/**
	 * base64解密
	 */
	public static String decodeBase64(String str)
	{
		return new String(Base64.decodeBase64(str.getBytes()));
	}
	
	/**
	 * MD5加密（32位  小写）
	 */
	public static String encodeMD5_32bit_LowerCase(String str)
	{
		return DigestUtils.md5Hex(str);
	}
	
	/**
	 * MD5加密（32位  大写）
	 */
	public static String encodeMD5_32bit_UpperCase(String str)
	{
		return DigestUtils.md5Hex(str).toUpperCase();
	}
	
}
