package com.cloud.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by hasee on 2019/6/24.
 */
public class TimeZoneUtil {
    /**
     * 取北京时间，格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getBeijingTime(){
        return getFormatedDateString(8);
    }

    /**
     * 印度时间
     * @return
     */
    public static String getBombayTime(){
        return getFormatedDateString((float)5.5);
    }

    /**
     * 此函数非原创，从网上搜索而来，timeZoneOffset原为int类型，为班加罗尔调整成float类型
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     * @param timeZoneOffset
     * @return
     */
    public static String getFormatedDateString(float timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }

        int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }
}
