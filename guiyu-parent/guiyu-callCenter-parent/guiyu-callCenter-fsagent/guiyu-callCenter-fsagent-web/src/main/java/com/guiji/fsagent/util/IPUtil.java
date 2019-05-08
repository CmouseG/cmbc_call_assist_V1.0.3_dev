package com.guiji.fsagent.util;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: 魏驰
 * @Date: 2019/4/1 09:54
 * @Project：guiyu-parent
 * @Description:
 */
public class IPUtil {
    private static String IPADDRESS_PATTERN = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    private static String VALIDATE_IPADDRESS_PATTERN = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";
    private static Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
    private static Pattern validPattern = Pattern.compile(VALIDATE_IPADDRESS_PATTERN);

    public static void main1(String[] args) {
        System.out.println("get external ip: " + getExtIp());
        System.out.println(extractIp("192.168.1.111"));
        System.out.println(extractIp("142.87.98.234"));
        System.out.println(extractIp("49.77.216.187"));
        System.out.println(extractIp("{\"address\": \"49.77.216.245\"}"));
        System.out.println(extractIp("var returnCitySN = {\"cip\": \"49.77.216.224\", \"cid\": \"320000\", \"cname\": \"江苏省\"};"));

        System.out.println(validIp("192.168.1.111"));
        System.out.println(validIp("142.87.98.234"));
        System.out.println(validIp("49.77.216.187"));
        System.out.println(validIp("var returnCitySN = {\"cip\": \"49.77.216.224\", \"cid\": \"320000\", \"cname\": \"江苏省\"};"));
    }

    /**
     * 获取公网IP地址
     *
     * @return
     */
    public static String getExtIp() {
        String extIp;

        String result;

        try {
            result = RestHttpUtil.get("http://pv.sohu.com/cityjson");
            extIp = extractIp(result);
            if(extIp!=null){
                return extIp;
            }
        } catch (Exception e) {}

        try {
            result = RestHttpUtil.get("http://icanhazip.com/");
            extIp = extractIp(result);
            if(extIp!=null){
                return extIp;
            }
        } catch (Exception e) { }

        try {
            result = RestHttpUtil.get("http://ident.me/.json");
            extIp = extractIp(result);
            if(extIp!=null){
                return extIp;
            }
        } catch (Exception e) { }


        return null;
    }


    /**
     * 从字符串中获取解析出ip地址
     * @param ipString
     * @return
     */
    public static String extractIp(String ipString){
        if(Strings.isNullOrEmpty(ipString)){
            return null;
        }

        Matcher matcher = pattern.matcher(ipString);
        if (matcher.find()) {
            return matcher.group();
        } else{
            return null;
        }
    }

    /**
     * 判断ip地址是否合法
     * @param ipString
     * @return
     */
    public static boolean validIp(String ipString){
        if(Strings.isNullOrEmpty(ipString)){
            return false;
        }

        Matcher matcher = validPattern.matcher(ipString);
        if (matcher.find()) {
            return true;
        } else{
            return false;
        }
    }
}
