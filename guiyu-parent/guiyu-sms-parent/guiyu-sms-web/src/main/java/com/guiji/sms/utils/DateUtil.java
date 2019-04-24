package com.guiji.sms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	//解决SimpleDateFormat在多线程环境下不安全问题
	private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

	/**
	 * 获取多线程环境下解决处理时间异常的SimpleDateFormat
	 * @param formatPattern 时间格式，如：yyyy-MM-dd HH:mm:ss
	 */
	public static SimpleDateFormat getSimpleDateFormat(String formatPattern)
	{
		SimpleDateFormat sdf = null;
		sdf = threadLocal.get();
		if (sdf == null) {
			sdf = new SimpleDateFormat(formatPattern);
			threadLocal.set(sdf);
		}
		return sdf;
	}
	
	/**
	 * 将String类型按照指定格式转换成Date类型，注意：dateString和formatPattern格式需相同
	 * @param dateString 
	 * @param formatPattern 时间格式，如：yyyy-MM-dd HH:mm:ss
	 */
	public static Date parse(String dateString, String formatPattern) throws ParseException
	{
		return new SimpleDateFormat(formatPattern).parse(dateString);
	}
	
	/**
	 * 将Date类型按照指定格式转换成String类型
	 * @param date
	 * @param formatPattern 时间格式，如：yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Date date, String formatPattern)
	{
		return new SimpleDateFormat(formatPattern).format(date);
	}
	
}
