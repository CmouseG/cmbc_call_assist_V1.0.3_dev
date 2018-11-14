/**
 * Copyright: Copyright (c) 2016 
 * Company:东方网力科技股份有限公司
 * 
 * @author  zhaoweiwei
 * @date 2016年5月3日 上午11:49:32
 * @version V1.0
 */
package com.guiji.dispatch.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ToolDateTime {

	/**
	 * pattern_ym(年-月)
	 */
	public static final String PATTERN_YM = "yyyy-MM";
	/**
	 * pattern_ymd
	 */
	public static final String PATTERN_YMD = "yyyy-MM-dd";

	/**
	 * PATTERN_YYMMDD
	 */
	public static final String PATTERN_YYMMDD = "yyyyMMdd";

	/**
	 * pattern_ymd_00
	 */
	public static final String PATTERN_YMD_00 = "yyyy-MM-dd 00:00:00";
	/**
	 * pattern_ymd_24
	 */
	public static final String PATTERN_YMD_24 = "yyyy-MM-dd 23:59:59";

	/**
	 * pattern_ymdtime
	 */
	public static final String PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * PATTERN_YMD_HM
	 */
	public static final String PATTERN_YMD_HM = "yyyy-MM-dd HH:mm";

	/**
	 * pattern_ymdtimeMillisecond
	 */
	public static final String PATTERN_YMD_HMS_S = "yyyy-MM-dd HH:mm:ss:SSS";

	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	/**
	 * pattern_YYYYMMDDHHMMSS
	 */
	public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * pattern_yyyyMMddHHmmssSSS
	 */
	public static final String PATTERN_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	/**
	 * pattern_ymd_t_hms
	 */
	public static final String PATTERN_YMD_T_HMS = "yyyy-MM-dd'T'HH:mm:ss";

	public static final String DTLONG = "yyyyMMddHHmmss";

	/**
	 * 主要是给jfinal使用，数据库只认java.sql.*
	 * 
	 * @param date
	 *            数据
	 * @return Timestamp对象
	 */
	public static Timestamp getSqlTimestamp(Date date) {
		if (null == date) {
			date = new Date();
		}
		return getSqlTimestamp(date.getTime());
	}

	/**
	 * 主要是给jfinal使用，数据库只认java.sql.*
	 * 
	 * @param time
	 *            时间
	 * @return Timestamp对象
	 */
	public static Timestamp getSqlTimestamp(long time) {
		return new java.sql.Timestamp(time);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return date 时间
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 *
	 * @Title: getFormatDate
	 * @Description: 获取当前时间（格式化）
	 * @return String
	 */
	public static String getFormatDate() {
		return format(new Date(), PATTERN_YMD_HMS);
	}

	/**
	 * 获取当前时间的时间戳
	 * 
	 * @return long 时间戳
	 */
	public static long getDateByTime() {
		return new Date().getTime();
	}

	/**
	 * 格式化
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return String 带格式的日期
	 */
	public static String format(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public static Date getCurrentTime() throws Exception {
		SimpleDateFormat dateFormatter = null;
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormatter.parse(dateFormatter.format(new Date()));  
	}

}