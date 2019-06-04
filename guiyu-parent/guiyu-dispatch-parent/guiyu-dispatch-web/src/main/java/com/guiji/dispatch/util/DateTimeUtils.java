package com.guiji.dispatch.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 * @author
 *
 */
public class DateTimeUtils {
//	private final static Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);
	
	public final static String DEFAULT_DATE_FORMAT_PATTERN_SHORT = "yyyy-MM-dd";
	
	public final static String DEFAULT_TIME_FORMAT_PATTERN_SHORT = "HH:mm:ss";
	
	public final static String DEFAULT_DATE_FORMAT_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";

	public final static String DEFAULT_DATE_FORMAT_PATTERN_MIDDLE = "yyyy-MM-dd HH:mm";

	public final static String DEFAULT_MONTH_DAY_FORMAT_PATTERN_SHORT = "MM-dd";
	
	public final static String DEFAULT_TIME_NOT_SECOND_FORMAT_PATTERN_SHORT = "HH:mm";

	public final static String DEFAULT_DATE_FORMAT_PATTERN_SPLICING = "yyyyMMdd";
	
	public final static String DEFAULT_DATE_END_TIME = "23:59:59";
	
	public final static String DEFAULT_DATE_START_TIME = "00:00:00";
	
	private  static  Map<String, FastDateFormat> conMap = new ConcurrentHashMap<String, FastDateFormat>();
	
	static {
		conMap.put(DEFAULT_DATE_FORMAT_PATTERN_SHORT, FastDateFormat.getInstance(DEFAULT_DATE_FORMAT_PATTERN_SHORT));
		conMap.put(DEFAULT_TIME_FORMAT_PATTERN_SHORT, FastDateFormat.getInstance(DEFAULT_DATE_FORMAT_PATTERN_SHORT));
		conMap.put(DEFAULT_DATE_FORMAT_PATTERN_FULL, FastDateFormat.getInstance(DEFAULT_DATE_FORMAT_PATTERN_FULL));
	}
	/**
	 * 获得当前时间的字符串
	 * @param pattern 时间格式
	 * @return 时间字符串
	 */
	public static String getCurrentDateString(String pattern){
		FastDateFormat sdf = getDateFormat(pattern);
		return sdf.format(new Date());
	}
	/**
	 * 获得时间格式对象
	 * @param pattern
	 * @return
	 */
	public static FastDateFormat getDateFormat(String pattern){
		if(StringUtils.isEmpty(pattern)){
			pattern=DEFAULT_DATE_FORMAT_PATTERN_FULL;
		}
		FastDateFormat sdf = null;
		if(conMap.containsKey(pattern)){
			sdf = conMap.get(pattern);
		}else{
			//处理没有找到的格式
			try {
				sdf = FastDateFormat.getInstance(pattern);
				conMap.put(pattern, sdf);
			} catch (Exception e) {
				sdf = FastDateFormat.getInstance(DEFAULT_DATE_FORMAT_PATTERN_FULL);
			}
		}
		return sdf;
	}
	/**
	 * 获得时间的字符串
	 * @param date 待转换的日期
	 * @param pattern 时间格式
	 * @return
	 */
	public static String getDateString(Date date, String pattern){
		FastDateFormat sdf = getDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 将字符串时间转换成指定格式时间
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date getDateByString(String dateStr, String pattern){
		if(StringUtils.isEmpty(dateStr) ){
			return null;
		}
		/*
		FastDateFormat sdf = getDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
//			logger.error("getDateByString e={}",e);
			return null;
		}
		*/
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		}catch (ParseException e){
			return null;
		}
	}

	/**
	 * 将字符串时间转化指定格式
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getFormatStrByDateStr(String dateStr, String pattern){
		if(StringUtils.isEmpty(dateStr)){
			return null;
		}

		if(StringUtils.isEmpty(dateStr)){
			return dateStr;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date = sdf.parse(dateStr);
			return sdf.format(date);
		}catch (ParseException e){
			return null;
		}
	}

	public final static void main(String args[]){
		String s = getFormatStrByDateStr("2019-02-01 23:59:59", "yyyy-MM-dd");
		System.out.println(s);
	}
	
	/**
	 * 根据时期和时间创建时间
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date creatDate(String date,String time){
		if(StringUtils.isEmpty(date) || StringUtils.isEmpty(time)){
			return null;
		}
		StringBuffer dateSb = new StringBuffer(date);
		return getDateByString(dateSb.append(" ").append(time).toString(), DEFAULT_DATE_FORMAT_PATTERN_FULL);
	}
	
	/**
	 * @Description: 从当前时间开始计算，获取指定偏移时间
	 * @param offset(-60:向前偏移60天;45向后偏移45天)
	 * @return 
	 */
	    
	public static String getDateByOffsetDay(int offset){
		return getDateByOffsetDay(offset,DEFAULT_DATE_FORMAT_PATTERN_SHORT);  
	}
	
	/**
	 * @Description: 从当前时间开始计算，获取指定偏移时间
	 * @param offset(-60:向前偏移60天;45向后偏移45天)
	 * @param dateFmt
	 * @return 
	 */
	    
	public static String getDateByOffsetDay(int offset,String dateFmt){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, offset);
		FastDateFormat sdf = getDateFormat(dateFmt);
		return sdf.format(cal.getTime());  
	}

	public static Date getDateByOffsetDays(int offset){
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, offset);

		return cal.getTime();
	}

	/**
	 * 从给定时间开始计算，获取指定偏移时间
	 * @param offset
	 * @return
	 */
	public static Date getDateByOffsetDays(Date date,int offset){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, offset);

		return cal.getTime();
	}
	
	public static Date getDateByOffsetMonths(Date date, int offset){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, offset);
		return cal.getTime();
	}
	
	/**
	 * @Description: 当前时间按HOUR偏移
	 * @param offset+
	 * @return 
	 */
	    
	public static Date getDateByOffsetHours(int offset){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, offset);
		return cal.getTime();  
	}
	

    /**
     * @Description: 当前时间按Minutes偏移
     * @param offset
     * @return 
     */
        
    public static Date getDateByOffsetMinutes(int offset){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, offset);
        return cal.getTime();  
    }
    

    /**
     * @Description: 当前时间按Minutes偏移
     * @param offset
     * @return 
     */
        
    public static Time getTimeByOffsetMinutes(int offset)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, offset);
        Date date=cal.getTime();
        String time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        return Time.valueOf(time);
    }
    
    public static Date getDateByOffsetSecond(Date startDate, int offset){
    	Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, offset);
		return cal.getTime();
    }
    

	public static Date getDate(String date ,String dateFmt) throws ParseException{
		 DateFormat df = new SimpleDateFormat(dateFmt);
		 return df.parse(date);
	}

	/**
	 * 校验格式为yyyy-mm-dd 如2017-02-01
	 * @param dateString
	 * @return
	 */
	public static boolean isValidYyyyMm(String dateString){
		//使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
		Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
		Matcher m = p.matcher(dateString);
		if(!m.matches()){	return false;}

		//得到年月日
		String[] array = dateString.split("-");
		int year = Integer.valueOf(array[0]);
		int month = Integer.valueOf(array[1]);
		int day = Integer.valueOf(array[2]);

		if(month<1 || month>12){	return false;}
		int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(isLeapYear(year)){
			monthLengths[2] = 29;
		}else{
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if(day!=1){
			return false;
		}
		/*if(day<1 || day>monthLength){
			return false;
		}*/
		return true;
	}
	/**
	 * 校验格式为yyyy-mm-dd 如2017-02-01
	 * @param dateString
	 * @return
	 */
	public static boolean isValidYyyy(String dateString){
		//使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
		Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
		Matcher m = p.matcher(dateString);
		if(!m.matches()){	return false;}

		//得到年月日
		String[] array = dateString.split("-");
		int month = Integer.valueOf(array[1]);

		if(month<1 || month>12){	return false;}
		return true;
	}
	/**
	 * 获取两个时间差多少天
	 */
	public static long distanceDays(String oneDay, String someDay,String pattern){
		DateFormat df = new SimpleDateFormat(pattern);
		Date oneDate;
		Date someDate;
		long days=0;
		try {
			oneDate=df.parse(oneDay);
			someDate=df.parse(someDay);
			long diff =oneDate.getTime()-someDate.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {

		}
		return days;
	}
	/**
	 * 获取两个时间差多少天
	 * @param str1 开始时间 格式如2017-01-12 12:12:12
	 * @param str2 结束时间 格式如2017-01-15 12:12:12
	 * @return
	 * @throws Exception
	 */
	public static long getDistanceDays(String str1, String str2){
		DateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN_FULL);
		Date one;
		Date two;
		long days=0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff ;
			if(time1<time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}
	/**
	 * 获取两个时间差多少天
	 * @param date1 开始日期
	 * @param date2 结束日期
	 * @return date1 和 date2 相差天数
	 * @throws Exception
	 */
	public static long getDistanceDays(Date date1, Date date2){
		long days=0;
		try {
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			long diff ;
			if(time1<time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 获取两个时间差多少年
	 * @param date1 开始日期
	 * @param date2 结束日期
	 * @return
	 */
	public static long getDistanceYears(Date date1, Date date2){
		if (date1 != null && date2 != null){
			return date2.getYear() - date1.getYear();
		}
		return 0;

	}

	/**
	 * 获得某个日期所在月的天数
	 * @param date
	 * @return
	 */
	public static int getMonthDays(Date date){
		if (date == null){
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days = calendar.getActualMaximum(Calendar.DATE);
		return days;
	}

	/**
	 * 获取某个日期是星期几
	 * @param date
	 * @return
	 */
	public static String getWeekDay(Date date){
		if (date == null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

    /**
     * 获取给定日期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (null != date) {
			calendar.setTime(date);
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				0, 0, 0);
		return calendar.getTime();
	}

    /**
     * 获取给定日期的结束时间
     *
     * @param date
     * @return
     */
    public static Date getDayEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (null != date) {
			calendar.setTime(date);
		}
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				23, 59, 59);
		return calendar.getTime();
	}

	/** 是否是闰年 */
	private static boolean isLeapYear(int year){
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ;
	}


	public final static String DEFAULT_BEGIN_TIME = "1970-01-01 00:00:00";

	public final static String DEFAULT_END_TIME   = "2050-12-31 23:59:59";

	public final static String DEFAULT_BEGIN_DATE = "1970-01-01";

	public final static String DEFAULT_END_DATE   = "2050-12-31";

	public final static String DEFAULT_BEGIN_MONTH = "1970-01";

	public final static String DEFAULT_END_MONTH   = "2050-12";

}
