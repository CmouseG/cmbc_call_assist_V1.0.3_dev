package com.guiji.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 系统工具类;
 * 一句话描述信息
 * @author administrator
 * 2015-7-27
 */
public final class SystemUtil {
	private SystemUtil(){};
	/**
     * 获取系统流水号
     * @return 长度为20的全数字
     */
    public static  String getSysJournalNo(){
        return getSysJournalNo(20, true);
    }
    
    /**
     * 默认32位的业务序列号
     * @param prefix 前缀+yyyymmdd+随机数
     * @return
     */
    public static String getBusiSerialNo(String prefix){
    	if(prefix == null) prefix = "";
    	String yyyymmdd = DateUtil.getTodateString();	//获取当天日期
    	//总长度30位，包括前缀
    	return prefix+yyyymmdd+getSysJournalNo(30-8-prefix.length(), true);
    }
    
    /**
     * 业务序列号，可变长
     * @param prefix 前缀+yyyymmdd+随机数
     * @return
     */
    public static String getBusiSerialNo(String prefix,int length){
    	if(prefix == null) prefix = "";
    	String yyyymmdd = DateUtil.getTodateString();	//获取当天日期
    	return prefix+yyyymmdd+getSysJournalNo(length-8-prefix.length(), true);
    }
    
    /**
     * 获取系统流水号
     * @param length   指定流水号长度
     * @param isNumber 指定流水号是否全由数字组成
     */
    public static synchronized String getSysJournalNo(int length, boolean isNumber){
        //replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if(uuid.length() > length){
            uuid = uuid.substring(0, length);
        }else if(uuid.length() < length){
            for(int i=0; i<length-uuid.length(); i++){
                uuid = uuid + Math.round(Math.random()*9);
            }
        }
        if(isNumber){
            return uuid.replaceAll("a", "1").replaceAll("b", "2").replaceAll("c", "3").replaceAll("d", "4").replaceAll("e", "5").replaceAll("f", "6");
        }else{
            return uuid;
        }
    }
    
    public static boolean isInteger(String str) {  
    	Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
	}
    
    /**
     * 获取本机IP地址
     * @return
     */
    public static String getHostIp() {
         try {
			return InetAddress.getLocalHost().getHostAddress().toString(); //获取本机IP地址
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
        return null;
    }
    
    
    public static void main(String[] args) {
		System.out.println(SystemUtil.getBusiSerialNo("TT"));
	}
}
