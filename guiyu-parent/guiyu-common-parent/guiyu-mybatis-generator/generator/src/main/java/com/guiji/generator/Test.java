package com.guiji.generator;

import java.io.UnsupportedEncodingException;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String aa = MD5Encoder.encode("http://192.168.1.208:8888".getBytes());
		//System.out.println(aa);
		
	}

	public static String stringToUnicode(String s) {
		try {
			StringBuffer out = new StringBuffer("");
			//直接获取字符串的unicode二进制
			byte[] bytes = s.getBytes("unicode");
			//然后将其byte转换成对应的16进制表示即可
			for (int i = 0; i < bytes.length - 1; i += 2) {
				out.append("\\u");
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++) {
					out.append("0");
				}
				String str1 = Integer.toHexString(bytes[i] & 0xff);
				out.append(str1);
				out.append(str);
			}
			return out.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String convertStringToUTF8(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			char c;
			for (int i = 0; i < s.length(); i++) {
				c = s.charAt(i);
				if (c >= 0 && c <= 255) {
					sb.append(c);
				} else {
					byte[] b;
					b = Character.toString(c).getBytes("utf-8");
					for (int j = 0; j < b.length; j++) {
						int k = b[j];
						//转换为unsigned integer  无符号integer
						/*if (k < 0)
							k += 256;*/
						k = k < 0? k+256:k;
						//返回整数参数的字符串表示形式 作为十六进制（base16）中的无符号整数
						//该值以十六进制（base16）转换为ASCII数字的字符串
						sb.append(Integer.toHexString(k).toUpperCase());
	 
						// url转置形式
						// sb.append("%" +Integer.toHexString(k).toUpperCase());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	private static boolean isChineseByScript(char c) {
		Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        if (sc == Character.UnicodeScript.HAN) {
        	return true;
        }
        return false;
	}
	
	
}
