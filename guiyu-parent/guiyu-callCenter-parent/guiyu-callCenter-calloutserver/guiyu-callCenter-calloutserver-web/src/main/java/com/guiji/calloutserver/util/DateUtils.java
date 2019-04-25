package com.guiji.calloutserver.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取当天的开始时间
     */
    public static Date getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 获取本周的开始时间
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取某个日期的开始时间
     * @param d
     * @return
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取6个月前的日期
     * @return
     */
    public static Date getHalfYearDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -6);
        return c.getTime();
    }

    /**
     * 获取多少天前的日期
     * @return
     */
    public static Date getDaysAgo(int days){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -24*days);
        return c.getTime();
    }

    /**
     * 获取几个小时之前的日期
     * @return
     */
    public static Date getHoursAgoDate(int hour){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -hour);
        return c.getTime();
    }
    /**
     * 将秒数转换为日时分秒，
     * @param second
     * @return
     */
    public static String secondToTime(Integer second){
        int days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        int hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        int minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        if(days>0){
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        }else if(hours>0){
            return hours + "小时" + minutes + "分" + second + "秒";
        }else if(minutes>0){
            return minutes + "分" + second + "秒";
        }else {
            return second + "秒";
        }
    }

}
