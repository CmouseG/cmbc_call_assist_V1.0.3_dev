package com.guiji.dispatch.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getCurrentDate(final long timeStamp)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        return sdf.format(new Date(timeStamp));
    }
}
