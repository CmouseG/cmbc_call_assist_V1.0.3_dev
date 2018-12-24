package com.guiji.dispatch.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64MD5Util {

	private static final Logger LOG = LoggerFactory.getLogger(Base64MD5Util.class);
	// 字符串编码
	private static final String UTF_8 = "utf-8";

	/**
	 * 加密字符串
	 * 
	 * @param inputData
	 * @return
	 */
	public static String decodeData(String inputData) {
		try {
			if (null == inputData) {
				return null;
			}
			return new String(Base64.decodeBase64(inputData.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error(inputData, e);
		}
		return null;
	}

	/**
	 * 解密加密后的字符串
	 * 
	 * @param inputData
	 * @return
	 */
	public static String encodeData(String inputData) {
		try {
			if (null == inputData) {
				return null;
			}
			return new String(Base64.encodeBase64(inputData.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error(inputData, e);
		}
		return null;
	}
	
	/**
    *
    * @param plainText
    *            明文
    * @return 32位密文
    */
   public static String encryption(String plainText) {
       String re_md5 = new String();
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(plainText.getBytes());
           byte b[] = md.digest();

           int i;

           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
               i = b[offset];
               if (i < 0)
                   i += 256;
               if (i < 16)
                   buf.append("0");
               buf.append(Integer.toHexString(i));
           }

           re_md5 = buf.toString();

       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
       return re_md5;
   }

}
