package com.guiji.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.TreeMap;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ty
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 得到当前以及前五年的年份
	 */
	public static TreeMap<Integer,String> getYears(){
		Integer currentYear = Integer.valueOf(getYear());
		TreeMap<Integer,String> yearMap = new TreeMap<Integer,String>();
		yearMap.put(currentYear, currentYear+"年");
		yearMap.put(currentYear-1, currentYear-1+"年");
		yearMap.put(currentYear-2, currentYear-2+"年");
		yearMap.put(currentYear-3, currentYear-3+"年");
		yearMap.put(currentYear-4, currentYear-4+"年");
		yearMap.put(currentYear-5, currentYear-5+"年");
		return yearMap;
	}

	/**
	 * 得到当前前一年以及前4年的年份
	 */
	public static TreeMap<Integer,String> getSortYears(){
		Integer currentYear = Integer.valueOf(getYear());
		TreeMap<Integer,String> yearMap = new TreeMap<Integer,String>();
		yearMap.put(currentYear-1, currentYear-1+"年");
		yearMap.put(currentYear, currentYear+"年");
		yearMap.put(currentYear-2, currentYear-2+"年");
		yearMap.put(currentYear-3, currentYear-3+"年");
		yearMap.put(currentYear-4, currentYear-4+"年");
		yearMap.put(currentYear-5, currentYear-5+"年");
		return yearMap;
	}



	public static TreeMap<Integer,String> getMonths(){
		TreeMap<Integer,String> monthMap = new TreeMap<Integer,String>();
		monthMap.put(0, "");
		monthMap.put(1, "1月");
		monthMap.put(2, "2月");
		monthMap.put(3, "3月");
		monthMap.put(4, "4月");
		monthMap.put(5, "5月");
		monthMap.put(6, "6月");
		monthMap.put(7, "7月");
		monthMap.put(8, "8月");
		monthMap.put(9, "9月");
		monthMap.put(10, "10月");
		monthMap.put(11, "11月");
		monthMap.put(12, "12月");
		return monthMap;
	}
	
	/**
	 * 获取当前季度
	 * @return
	 */
	public static Integer getCurQuarter() {
		Integer quarter = 1;
		Integer month = Integer.parseInt(DateUtils.getMonth());
		if (month <= 3) {
			quarter = 1;
		} else if (3 < month && month <= 6) {
			quarter = 2;
		} else if (6 < month && month <= 9) {
			quarter = 3;
		} else {
			quarter = 4;
		}
		return quarter;
	}
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
	}
}
