package com.guiji.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	/**
	 * 根据正则表达式，从字符串中截取需要的值
	 * @param srcString	源字符串
	 * @param regex		正则表达式
	 * @param count		需要截取的字符串数量,即正则表达式中括号的个数
	 * @return
	 */
	public static List<String> getGroupStringByRegex(String srcString, String regex, int count){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(srcString);
		ArrayList<String> resultList = new ArrayList<String>();
		while (matcher.find()) {
			for(int i=1; i<count+1; i++){
				resultList.add(matcher.group(i));
			}
		}
		
		return resultList;
	}
	
	/**
	 * 根据正则表达式，获取匹配的第一个结果
	 * @param srcString
	 * @param regex
	 * @return
	 */
	public static String getStringByRegex(String srcString, String regex){
		List<String> list = getGroupStringByRegex(srcString, regex, 1);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

	/**
	 * @param args
	 */
	public static void main1(String[] args) {
		// TODO Auto-generated method stub
		String str = "<name>sofia/internal/65735@10.1.106.65:11160</name>";
		String regex = "\\D*(\\d*)@";
		
		List<String> result = getGroupStringByRegex(str,regex, 1);
		for(int i=0; i< result.size(); i++){
			System.out.format("%s: %s", i, result.get(i));
		}
		
		String rt = getStringByRegex(str, regex);
		System.out.format("\ngetStringByRegex: %s", rt);
		
		String roomUrl = "http://hw-vidyo.huawei.com/flex.html?roomdirect.html&key=msms28923jkselwfleksafff234sf";
		String roomRegex = "https?://(.*?)/flex.*key=(.*?)$";
		System.out.println("\n\n" + getGroupStringByRegex(roomUrl, roomRegex,2));
	}

}
