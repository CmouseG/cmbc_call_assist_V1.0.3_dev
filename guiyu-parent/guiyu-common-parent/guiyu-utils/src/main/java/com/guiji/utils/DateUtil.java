package com.guiji.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 日期工具类
 * @ClassName: DateUtil
 * @Description: 日期工具了
 * @author administratro
 * @date 2015-12-30 上午12:14:55
 *
 */
public final class DateUtil {
    private DateUtil(){};
    private static final String ymdhms = "yyyy-MM-dd HH:mm:ss";
    private static final String ymdhms2 = "yyyyMMddHHmmss";
    private static final String ymd = "yyyy-MM-dd";
    private static final String year = "yyyy";
    private static final String month = "MM";
    private static final String day = "dd";
    private static final long DATEMM = 86400L;

    /**
     * 转换日期字符串
     * @param dataStr
     * @return
     */
    public static Date parseDate(String dataStr){
        if(dataStr == null){
            return null;
        }else if(dataStr.length() ==8){
            return stringToDate(dataStr, "yyyyMMdd");
        }else if(dataStr.length() ==10){
            return stringToDate(dataStr, ymd);
        }else if(dataStr.length() ==14){
            return stringToDate(dataStr, ymdhms2);
        }else if(dataStr.length() ==19){
            return stringToDate(dataStr, ymdhms);
        }else{
            return null;
        }
    }
    /**
     * 获得当前时间
     * 格式：2014-12-02 10:38:53
     * @return String
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat(ymdhms).format(new Date());
    }


    /**
     * 获得当前时间
     * 格式：20141202103853
     * @return String
     */
    public static String getCurrentTime2() {
        return new SimpleDateFormat(ymdhms2).format(new Date());
    }

    /**
     * 获取当前timestamp时间
     * @return
     */
    public static Timestamp getCurrentTime3(){
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当前日期,并且转换成指定格式;
     * @Title: getNowDate
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @throws
     */
    public static Date getNowDate(){
        String currDate = dateToString(new Date(),"yyyy-MM-dd");
        return stringToDate(currDate);
    }

    /**
     * 日期转字符串;
     * @Title: dateToString
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @throws
     */
    public static String dateToString(Date date,String pattern){
        String dateResult = null;
        if(date != null){
            SimpleDateFormat f = new SimpleDateFormat(StrUtils.isNotEmpty(pattern)?pattern:"yyyy-MM-dd");
            dateResult = f.format(date);
        }
        return dateResult;
    }

    /**
     * 字符串转成日期;
     * @Title: stringToDate
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @throws
     */
    public static Date stringToDate(String date){
        Date dateResult = null;
        try {
            if(date != null && ""!=date){
                dateResult = new SimpleDateFormat(ymd).parse(date);
            }
            return dateResult;
        } catch (ParseException e) {
            e.printStackTrace();
            dateResult = getNowDate();
        }
        return dateResult;
    }

    /**
     * 可以获取昨天的日期
     * 格式：2014-12-01
     * @return String
     */
    public static String getYesterdayYYYYMMDD() {
        Date date = new Date(System.currentTimeMillis() - DATEMM * 1000L);
        return new SimpleDateFormat(
                "yyyyMMdd").format(date);
    }
    /**
     * 可以获取后退N天的日期
     * 格式：传入2 得到2014-11-30
     * @param backDay
     * @return String
     */
    public String getStrDate(String backDay) {
        Calendar calendar = Calendar.getInstance() ;
        calendar.add(Calendar.DATE, Integer.parseInt("-" + backDay));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
        String back = sdf.format(calendar.getTime()) ;
        return back ;
    }
    /**
     *获取当前的年、月、日
     * @return String
     */
    public static String getCurrentYear() {
        return new SimpleDateFormat(year).format(new Date());
    }
    public static String getCurrentMonth() {
        return new SimpleDateFormat(month).format(new Date());
    }
    public static String getCurrentDay() {
        return new SimpleDateFormat(day).format(new Date());
    }
    /**
     * 获取年月日 也就是当前时间
     * 格式：2014-12-02
     * @return String
     */
    public static String getCurrentymd() {
        return new SimpleDateFormat(ymd).format(new Date());
    }
    /**
     * 获取今天0点开始的秒数
     * @return long
     */
    public static long getTimeNumberToday() {
        Date date = new Date();
        String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            return date.getTime() / 1000L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }
    /**
     * 获取今天的日期
     * 格式：20141202
     * @return String
     */
    public static String getTodayString() {
        return getStringDate(new Date());
    }


    /**
     * 将日期类型转为CMS系统中常用的yyyyMMdd类型的日期字符串
     * @return
     */
    public static String getStringDate(Date date){
        return new SimpleDateFormat(
                "yyyyMMdd").format(date);
    }

    /**
     * 获取昨天的日期
     * 格式：20141201
     * @return String
     */
    public static String getYesterdayString() {
        Date date = new Date(System.currentTimeMillis() - DATEMM * 1000L);
        String str = new SimpleDateFormat(
                "yyyyMMdd").format(date);
        return str;
    }
    /**
     * 获得昨天零点
     *
     * @return Date
     */
    public static Date getYesterDayZeroHour() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal.getTime();
    }
    /**
     * 把long型日期转String ；---OK
     *
     * @param date
     *            long型日期；
     * @param format
     *            日期格式；
     * @return
     */
    public static String longToString(long date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt2 = new Date(date * 1000L);
        String sDateTime = sdf.format(dt2); // 得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    /**
     * 获得今天零点
     *
     * @return Date
     */
    public static Date getTodayZeroHour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal.getTime();
    }
    /**
     * 获得昨天23时59分59秒
     *
     * @return
     */
    public static Date getYesterDay24Hour() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR, 23);
        return cal.getTime();
    }
    /**
     * String To Date ---OK
     *
     * @param date
     *            待转换的字符串型日期；
     * @param format
     *            转化的日期格式
     * @return 返回该字符串的日期型数据；
     */
    public static Date stringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取指定日期所在周的周一
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getMondayOfDate(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat(
                    "yyyyMMdd").parse(date));
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
            return new SimpleDateFormat(
                    "yyyyMMdd").format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期所在周的周日
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getSundayOfDate(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat(
                    "yyyyMMdd").parse(date));
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, 6);
            return new SimpleDateFormat(
                    "yyyyMMdd").format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得指定日期所在当月第一天
     *
     * @param date
     * @return
     */
    public static Date getStartDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期所在当月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        date = c.getTime();
        return date;
    }

    /**
     * 获取指定日期所在月的第一天
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getMonthStartDate(String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat(
                    "yyyyMMdd").parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return new SimpleDateFormat(
                    "yyyyMMdd").format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期所在月的最后一天
     *
     * @param date
     * @return yyyy--MM-dd
     */
    public static String getMonthEndDate(String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat(
                    "yyyyMMdd").parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return new SimpleDateFormat(
                    "yyyyMMdd").format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获得指定日期的下一个月的第一天
     *
     * @param date
     * @return
     */
    public static Date getStartDayOfNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期的下一个月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 2);
        c.add(Calendar.DATE, -1);
        date = c.getTime();
        return date;
    }

    /**
     *
     * 求某一个时间向前多少秒的时间(currentTimeToBefer)---OK
     *
     * @param givedTime
     *            给定的时间
     * @param interval
     *            间隔时间的毫秒数；计算方式 ：n(天)*24(小时)*60(分钟)*60(秒)(类型)
     * @param format_Date_Sign
     *            输出日期的格式；如yyyy-MM-dd、yyyyMMdd等；
     */
    public static String givedTimeToBefer(String givedTime, long interval,
                                          String format_Date_Sign) {
        String tomorrow = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format_Date_Sign);
            Date gDate = sdf.parse(givedTime);
            long current = gDate.getTime(); // 将Calendar表示的时间转换成毫秒
            long beforeOrAfter = current - interval * 1000L; // 将Calendar表示的时间转换成毫秒
            Date date = new Date(beforeOrAfter); // 用timeTwo作参数构造date2
            tomorrow = new SimpleDateFormat(format_Date_Sign).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tomorrow;
    }
    /**
     * 把String 日期转换成long型日期；---OK
     *
     * @param date
     *            String 型日期；
     * @param format
     *            日期格式；
     * @return
     */
    public static long stringToLong(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dt2 = null;
        long lTime = 0;
        try {
            dt2 = sdf.parse(date);
            // 继续转换得到秒数的long型
            lTime = dt2.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return lTime;
    }

    /**
     * 得到二个日期间的间隔日期；
     *
     * @param endTime
     *            结束时间
     * @param beginTime
     *            开始时间
     * @param isEndTime
     *            是否包含结束日期；
     * @return
     */
    public static Map<String, String> getTwoDay(String endTime,
                                                String beginTime, boolean isEndTime) {
        Map<String, String> result = new HashMap<String, String>();
        if ((endTime == null || endTime.equals("") || (beginTime == null || beginTime
                .equals(""))))
            return null;
        try {
            Date date = new SimpleDateFormat(ymd).parse(endTime);
            endTime = new SimpleDateFormat(ymd).format(date);
            Date mydate = new SimpleDateFormat(ymd).parse(beginTime);
            long day = (date.getTime() - mydate.getTime())
                    / (24 * 60 * 60 * 1000);
            result = getDate(endTime, Integer.parseInt(day + ""), isEndTime);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 得到二个日期间的间隔日期；
     *
     * @param endTime    yyyy-MM-dd
     *            结束时间
     * @param beginTime  yyyy-MM-dd
     *            开始时间
     * @param isEndTime
     *            是否包含结束日期；
     * @return
     */
    public static Integer getTwoDayInterval(String endTime, String beginTime,
                                            boolean isEndTime) {
        if ((endTime == null || endTime.equals("") || (beginTime == null || beginTime
                .equals(""))))
            return 0;
        long day = 0l;
        try {
            Date date = new SimpleDateFormat(ymd).parse(endTime);
            endTime = new SimpleDateFormat(ymd).format(date);
            Date mydate = new SimpleDateFormat(ymd).parse(beginTime);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return 0 ;
        }
        return Integer.parseInt(day + "");
    }

    /**
     * 得到二个日期间的间隔日期；
     *
     * @param endTime    yyyyMMdd
     *            结束时间
     * @param beginTime  yyyyMMdd
     *            开始时间
     * @param isEndTime
     *            是否包含结束日期；
     * @return
     */
    public static Integer getTwoDayInterval2(String endTime, String beginTime,
                                             boolean isEndTime) {
        if ((endTime == null || endTime.equals("") || (beginTime == null || beginTime
                .equals(""))))
            return 0;
        long day = 0l;
        try {
            Date date = new SimpleDateFormat(
                    "yyyyMMdd").parse(endTime);
            endTime = new SimpleDateFormat(
                    "yyyyMMdd").format(date);
            Date mydate = new SimpleDateFormat(
                    "yyyyMMdd").parse(beginTime);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return 0 ;
        }
        return Integer.parseInt(day + "");
    }

    /**
     * 根据结束时间以及间隔差值，求符合要求的日期集合；
     *
     * @param endTime
     * @param interval
     * @param isEndTime
     * @return
     */
    public static Map<String, String> getDate(String endTime, Integer interval,
                                              boolean isEndTime) {
        Map<String, String> result = new HashMap<String, String>();
        if (interval == 0 || isEndTime) {
            if (isEndTime)
                result.put(endTime, endTime);
        }
        if (interval > 0) {
            int begin = 0;
            for (int i = begin; i < interval; i++) {
                endTime = givedTimeToBefer(endTime, DATEMM, ymd);
                result.put(endTime, endTime);
            }
        }
        return result;
    }


    /**
     * 得到指定月后（前）的日期 参数传负数即可
     * @param dateString yyyyMMdd
     * @param month
     * @return
     */
    public static String  getAfterMonth(String dateString,int month) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try{
            date = sdf.parse(dateString);//初始日期
        }catch(Exception e){

        }
        c.setTime(date);//设置日历时间
        c.add(Calendar.MONTH,month);//在日历的月份上增加6个月
        String strDate = sdf.format(c.getTime());//的到你想要得6个月后的日期
        return strDate;
    }

    /**
     * 根据起始日、期限、期限单位计算到期日
     * @param startDate
     * @param termType
     * @param term
     * @return
     */
    public static Date getEndDate(Date startDate,String termType,int term){
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        if("Y".equalsIgnoreCase(termType)){
            c.add(Calendar.YEAR, term);    //年
        }else if("M".equalsIgnoreCase(termType)){
            c.add(Calendar.MONTH, term);	//月
        }else if("D".equalsIgnoreCase(termType)){
            c.add(Calendar.DATE, term);    //年
        }
        Date endDate = c.getTime();
        return endDate;
    }


    /**
     * 此方法用来比较当前日期是目标日期月份或者更早的月份
     * 如：当前日期20161116 ，目标20161130 tue
     *     当前日期20161015 ，目标20161130 tue
     *     当前日期20161031 ，目标20161130 tue
     *     当前日期20161201 ，目标20161130 false
     * @param targetDate 要比较的目标月份
     */
    public static boolean isBeforeCurrentMonth(String targetDate){
        if(StrUtils.isEmpty(targetDate)) throw new RuntimeException("日前不能位空");
        String currentYear = DateUtil.getCurrentYear();	//当前日前年
        String currentMonth = DateUtil.getCurrentMonth();	//当前日前月
        String targetYear = targetDate.substring(0, 4);	//目标年
        String targetMonth = targetDate.substring(4, 6);	//目标月
        if((currentYear.compareTo(targetYear) > 0)
                ||((currentYear.equals(targetYear)) && currentMonth.compareTo(targetMonth) >0)){
            return false;
        }
        return true;
    }

    /**
     * 比较两个日期隔了多少天
     * @param beforeDate
     * @param endDate
     * @return
     */
    public static int daysOfTwo(Date beforeDate, Date endDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(beforeDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(endDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 格式化我们系统中常用的日期格式
     * 将yyyyMMdd格式化为yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatCommonSysDate(String date){
        return DateUtil.dateToString(DateUtil.stringToDate(date,"yyyyMMdd"),null);
    }

    /**
     * 日期转星期
     * @param date
     * @param weekDays
     * @return
     */
    public static String dateToWeek(Date date,String[] weekDays) {
        if(weekDays == null || weekDays.length ==0) {
            //默认周名称
            weekDays =new String[]{ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        }
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getTodateString() {
        return getStringDate(new Date());
    }

    /**
     * 获取前/后半年的开始时间
     * @return
     */
    public static String getHalfYearStartTime(String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 0);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            return new SimpleDateFormat(
                    "yyyyMMdd").format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 获取前/后半年的结束时间
     * @return
     */
    public static String getHalfYearEndTime(String date){
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date));
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            return new SimpleDateFormat(
                    "yyyyMMdd").format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 距离当前月第几个月的第一天
     * 如上个月第一天，参数是：-1，上上个月-2
     * @throws Exception
     */
    public static String getMonthFirstdate(int month){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(
                "yyyyMMdd").format(calendar.getTime());
    }

    /**
     * 距离当前月第几个月的自后天
     * 如上个月第一天，参数是：-1，上上个月-2
     * @throws Exception
     */
    public static String getMonthLastdate(int month){
        Calendar calendar=Calendar.getInstance();
        int now=calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, now + month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat(
                "yyyyMMdd").format(calendar.getTime());
    }

    /**
     * 上月第一天
     * @throws Exception
     */
    public static String getBeforeFirstMonthdate(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(
                "yyyyMMdd").format(calendar.getTime());
    }

    /**
     * 上月最后一天
     * @throws Exception
     */
    public static String getBeforeLastMonthdate(){
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat(
                "yyyyMMdd").format(calendar.getTime());
    }

    /**
     * 获取指定年月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH,firstDay);
        //格式化日期
        return new SimpleDateFormat(
                "yyyyMMdd").format(cal.getTime());
    }

    /**
     * 获取指定年月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        return new SimpleDateFormat(
                "yyyyMMdd").format(cal.getTime());
    }

    /**
     * 某个季度第一天
     * @param quarter
     * @return
     */
    public static String getQuarterBeginDate(int quarter) {
        String today = DateUtil.getTodateString();
        int year = Integer.parseInt(today.substring(0, 4));
        if(1==quarter) {
            //一季度（1月-3月）
            return getFirstDayOfMonth1(year,1); //1月第一天
        }else if(2==quarter) {
            //二季度（4月-6月）
            return getFirstDayOfMonth1(year,4); //4月第一天
        }else if(3==quarter) {
            //三季度（7月-9月）
            return getFirstDayOfMonth1(year,7); //7月第一天
        }else if(4==quarter) {
            //四季度（10月-12月）
            return getFirstDayOfMonth1(year,10); //10月第一天
        }
        return null;
    }

    /**
     * 某个季度最后一天
     * @param quarter
     * @return
     */
    public static String getQuarterEndDate(int quarter) {
        String today = DateUtil.getTodateString();
        int year = Integer.parseInt(today.substring(0, 4));
        if(1==quarter) {
            //一季度（1月-3月）
            return getLastDayOfMonth1(year,3); //3月最后一天
        }else if(2==quarter) {
            //二季度（4月-6月）
            return getLastDayOfMonth1(year,6); //6月最后一天
        }else if(3==quarter) {
            //三季度（7月-9月）
            return getLastDayOfMonth1(year,9); //9月最后一天
        }else if(4==quarter) {
            //四季度（10月-12月）
            return getLastDayOfMonth1(year,12); //12月最后一天
        }
        return null;
    }

    public static void main(String[] args) {
//    	System.out.println(new DateUtil().getMondayOfDate("20180702")); //本周一
//    	System.out.println(new DateUtil().getSundayOfDate("20180702")); //本周日
//    	System.out.println(new DateUtil().getMonthStartDate("20180702")); //本月第一天
//    	System.out.println(new DateUtil().getMonthEndDate("20180702")); //本月最后一天
        System.out.println(DateUtil.getMonthFirstdate(1));
        System.out.println(DateUtil.getMonthLastdate(1));
    }
}
