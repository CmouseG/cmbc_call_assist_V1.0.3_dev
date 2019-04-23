package com.guiji.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DateUtil.class);
    /**
     * 日期格式为 年-月-日 时:分:秒
     */
    public static final String FORMAT_YEARMONTHDAY_HOURMINSEC = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YEARMONTHDAY = "yyyy-MM-dd";

    /**
     * 获取当前的日期， 年月日，如20121218
     *
     * @return
     */
    public static String getCurrentDate() {
        return getCurrentDateByFormat("yyyyMMdd");
    }

    /**
     * 获取当前的日期时间， 年月日 时分秒 如2013-05-24 14:39:43
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前的日期时间， 年月日 时分秒 如2013-05-24 14:39:43
     *
     * @return
     */
    public static String getCurrentDateTimeChina() {
        return getCurrentDateByFormat("yyyy年MM月dd日HH点mm分ss秒");
    }

    /**
     * 获取当前的日期， 年月日，如20121218121212
     *
     * @return
     */
    public static String getCurrentDateTimeOther() {
        return getCurrentDateByFormat("yyyyMMddHHmmss");
    }

    /**
     * 按照指定格式返回当前日期和时间
     *
     * @param format
     *            时间格式，如yyyyMMdd 或 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDateByFormat(String format) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(format);
        String currDate = df.format(date);
        return currDate;
    }

    /**
     * 将字符串按指定的格式转化为日期类型
     *
     * @param date
     *            日期字符串
     * @param formatString
     *            格式字符串， 如yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Calendar getCalendarByDateAndFormat(String date, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        Calendar calendar = Calendar.getInstance();
        try {
            Date dt = format.parse(date);
            calendar.setTime(dt);
        } catch (ParseException e) {
            calendar = null;
        }

        return calendar;
    }

    public static Date getDateByDateAndFormat(String date, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        Date calendar = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatString);
            sdf.setLenient(false);//是否严格匹配
            calendar =  sdf.parse(date);
        } catch (ParseException e) {
            calendar = null;
        }
        return calendar;
    }

    /**
     * 比较两个日期 cal1 - cal2，返回两个日期的差，以秒值返回
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static Integer CompareTwoCalendar(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            Long lon = (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / 1000;
            return lon.intValue();
        } else {
            return null;
        }
    }

    /**
     * 获取日期字符串
     * @param date
     * @param strFormat
     * @return
     */
    public static String getStrDate(Date date,String strFormat)
    {
        String strDate = "";
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        strDate = format.format(date);
        return  strDate;
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            formatDateTime.parse(str);
            convertSuccess = true;
            return convertSuccess;
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate.parse(str);
            convertSuccess = true;
            return convertSuccess;
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return  convertSuccess;
    }

    public static String getUserYears(String yearIng){
        String effectiveDate=null;
        try {
            Preconditions.checkNotNull(yearIng,"null yearIng");
            int add=Integer.parseInt(yearIng);
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String yearOld=sdf.format(date);
            String[] yearMonthData=yearOld.split("-");
            int year=Integer.parseInt(yearMonthData[0]);
            String yearNow=year+add+"";
            effectiveDate=yearNow+"-"+yearMonthData[1]+"-"+yearMonthData[2];
        } catch (NumberFormatException e) {
            log.warn(e.getMessage());
        }
        return effectiveDate;
    }

    /**
     * 将标准的日期时间格式(yyyy-MM-dd HH:mm:ss)，转成时间戳
     * @param dateTime
     * @return
     */
    public static Long toEpoch(String dateTime){
        if(Strings.isNullOrEmpty(dateTime)){
            return null;
        }

        Long epoch = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC).getEpochSecond();
        return epoch;
    }

    public static String timeStampToDate(Long epoch){

        Timestamp timestamp1 = new Timestamp(epoch);
        Date date = new Date(timestamp1.getTime());
        return getStrDate(date, FORMAT_YEARMONTHDAY_HOURMINSEC);
    }


    public static long dateDiffTime(Date startTime, Date endTime) {
        //获得两个时间的毫秒时间差异
        long diff = endTime.getTime() - startTime.getTime();
        return diff;//计算差多少小时
    }

    public static String toString(Calendar calendar,String formate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        String dateStr = sdf.format(calendar.getTime());
        return  dateStr;
    }

    public static String toString(Date calendar,String formate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        String dateStr = sdf.format(calendar.getTime());
        return  dateStr;
    }

    public static Timestamp toTimeStamp(String dateTime){
        if(Strings.isNullOrEmpty(dateTime)){
            return null;
        }

        Timestamp myStamp = null;
        try {
            myStamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime).getTime());
        } catch (ParseException e) {
        }

        return myStamp;
    }

    public static String localTimeToString(LocalDateTime localDateTime){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(localDateTime);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss时间格式的时间字符串转换为yyyyMMddHHmmss
     * @param time
     * @return
     */
    public static String dateFormatToNoSeparator(String time){
        time = time.replaceAll(":","");
        time = time.replaceAll(" ","");
        time = time.replaceAll("-","");
        return time;
    }


    /**
     * 获得当天零时零分零秒
     * @return
     */
    public static Date initDateByDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

}

