/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.utils;

import org.apache.commons.lang.StringUtils;

/** 
 *@Description: 掩码工具类
 *@Author:weiyunbo
 *@date:2018年7月4日 下午8:28:00
 *@history:
 *@Version:v1.0 
 */
public class DisplayUtil {
	
	/**
     * 手机号显示首3末4位，中间用*号隐藏代替，如：138****4213
     *
     * @param mobile
     * @return
     */
    public static String displayMobile(String mobile) {
        if(StringUtils.isBlank(mobile) || mobile.length() <= 8) {
            return mobile;
        }
        return wordMask(mobile, 3, 4, "*");
    }
 
    /**
     * 电话号码显示区号及末4位，中间用*号隐藏代替，如：010****4213
     *
     * @param telephone
     * @return
     */
    public static String displayTelephone(String telephone) {
        if(StringUtils.isBlank(telephone)) {
            return telephone;
        }
        String result;
        if (telephone.length() > 8) {
            if (telephone.contains("-")) {
                String[] temp = telephone.split("-");
                result = temp[0] + "****" + temp[1].substring(temp[1].length() - 4, temp[1].length());
            } else {
                result = telephone.substring(0, 3) + "****" + telephone.substring(telephone.length() - 4, telephone.length());
            }
        } else {
            result = "****" + telephone.substring(telephone.length() - 4, telephone.length());
        }
        return result;
    }
 
    /**
     * 身份证号显示首6末4位，中间用4个*号隐藏代替，如：421002****1012
     *
     * @param idCard
     * @return
     */
    public static String displayIDCard(String idCard) {
        if(StringUtils.isBlank(idCard) || idCard.length() < 11) {
            return idCard;
        }
        return wordMask(idCard, 3, 4, "*");
    }
 
    /**
     * 银行卡显示首6末4位，中间用4个*号隐藏代替，如：622202****4123
     *
     * @param cardNo
     * @return
     */
    public static String displayBankCard(String cardNo) {
        if(StringUtils.isBlank(cardNo) || cardNo.length() < 11) {
            return cardNo;
        }
        return wordMask(cardNo, 6, 4, "*");
    }
 
    /**
     * 邮箱是前两位及最后一位字符，及@后邮箱域名信息，如：y****y@163.com
     *
     * @param email
     * @return
     */
    public static String displayEmail(String email) {
        if(StringUtils.isBlank(email)) {
            return email;
        }
        String[] temp = email.split("@");
        return wordMask(temp[0], 2, 1, "*") + "@" + temp[1];
    }
 
    /**
     * 三个字掩码，如：张晓明 如：张*明
     * 两个字掩码，如：小明 如：*明
     * 多个字掩码，如：张小明明 如：张**明
     *
     * @param name
     * @return
     */
    public static String displayName(String name) {
        if(StringUtils.isBlank(name) || name.length() == 1) {
            return name;
        }
        if (name.length() == 2) {
            return "*" + name.substring(1, 2);
        }
        return wordMask(name, 1, 1, "*");
    }
 
    /**
     * 对字符串进行脱敏处理
     *
     * @param word 被脱敏的字符
     * @param startLength 被保留的开始长度 前余n位
     * @param endLength 被保留的结束长度 后余n位
     * @param pad 填充字符
     * */
    public static String wordMask(String word,int startLength ,int endLength,String pad)    {
        if (startLength + endLength > word.length()) {
            return StringUtils.leftPad("", word.length() - 1, pad);
        }
        String startStr = word.substring(0, startLength);
        String endStr = word.substring(word.length() - endLength, word.length());
        return startStr + StringUtils.leftPad("", word.length() - startLength - endLength, pad) + endStr;
    }
    
    
    public static void main(String[] args) throws Exception {
		System.out.println(DisplayUtil.displayMobile("15221738675"));	//手机
		System.out.println(DisplayUtil.displayMobile("021-68712653"));  //固话
		System.out.println(DisplayUtil.displayIDCard("4103271988031098")); //身份证
		System.out.println(DisplayUtil.displayBankCard("6225220201411935"));	//银行卡
		System.out.println(DisplayUtil.displayEmail("weijiajia@163.com"));	//邮箱
		System.out.println(DisplayUtil.displayName("魏嘉嘉"));	//姓名
		System.out.println(DisplayUtil.wordMask("这是一段绝密文字",1,3,"啊"));
	}
}
  
