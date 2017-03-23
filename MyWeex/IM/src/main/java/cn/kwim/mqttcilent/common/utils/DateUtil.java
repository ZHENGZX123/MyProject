
package cn.kwim.mqttcilent.common.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Description
 * @author huangmingguo
 * 
 */
public class DateUtil {

    private static Date preRefreshDateTime;
    
    //判断时候刷新
    public static boolean toRefresh() {
        Date curdate = new Date();
        if (null == preRefreshDateTime) {
            preRefreshDateTime = curdate;
            return true;
        }

        long timediff = (curdate.getTime() - preRefreshDateTime.getTime());
        preRefreshDateTime = curdate;
        return (timediff >= 60 * 60 * 1000);
    }
    
    //获得时间传入时间的 时分秒
    @SuppressLint("SimpleDateFormat")
    public static Date getDate(Long dateLong) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(dateLong);
        Date date;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }
    //消息发送时间与当前时间大于5min 就显示 时间撮
    public static boolean needDisplayTime(Date predate, Date curdate) {
        if (predate == null || curdate == null) {
            return true;
        }

        long timediff = (curdate.getTime() - predate.getTime());
        return (timediff >= 5 * 60 * 1000);
    }
    //获得时间搓
    public static String getTimeDiffDesc(Date date) {

        if (date == null) {
            return null;
        }

        String strDesc = null;
        Calendar curCalendar = Calendar.getInstance();
        Date curDate = new Date();
        curCalendar.setTime(curDate);
        Calendar thenCalendar = Calendar.getInstance();
        thenCalendar.setTime(date);

        String[] weekDays = {
                "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"
        };
        int w = thenCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance(); // 今天
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天
        yesterday.setTime(curDate);
        yesterday.add(Calendar.DATE, -1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        Calendar sevendaysago = Calendar.getInstance(); // 7天
        sevendaysago.setTime(curDate);
        sevendaysago.add(Calendar.DATE, -7);
        sevendaysago.set(Calendar.HOUR_OF_DAY, 0);
        sevendaysago.set(Calendar.MINUTE, 0);
        sevendaysago.set(Calendar.SECOND, 0);
       
        int thenMonth = thenCalendar.get(Calendar.MONTH);
        int thenDay = thenCalendar.get(Calendar.DAY_OF_MONTH);
        int h = thenCalendar.get(Calendar.HOUR_OF_DAY);
        int m = thenCalendar.get(Calendar.MINUTE);
        String sh = "", sm = "";
        if (h < 10)
            sh = "0";

        if (m < 10)
            sm = "0";
        if (thenCalendar.after(today))// today
        {
            if (h < 6) {
                strDesc = "凌晨 " + sh + h + " : " + sm + m;
            } else if (h < 12) {
                strDesc = "上午 " + sh + h + " : " + sm + m;
            } else if (h < 13) {
                strDesc = "下午 " + h + " : " + sm + m;
            } else if (h < 19) {
                strDesc = "下午 " + (h - 12) + " : " + sm + m;
            } else {
                strDesc = "晚上 " + (h - 12) + " : " + sm + m;
            }
        } else if (thenCalendar.before(today) && thenCalendar.after(yesterday)) {// yestoday

        	if (h < 6) {
                strDesc = "昨天凌晨 " + sh + h + " : " + sm + m;
            } else if (h < 12) {
                strDesc = "昨天上午 " + sh + h + " : " + sm + m;
            } else if (h < 13) {
                strDesc = "昨天下午 " + h + " : " + sm + m;
            } else if (h < 19) {
                strDesc = "昨天下午 " + (h - 12) + " : " + sm + m;
            } else {
                strDesc = "昨天晚上 " + (h - 12) + " : " + sm + m;
            }
        } else if (thenCalendar.before(yesterday)
                && thenCalendar.after(sevendaysago)) {// 2 ~ 7days ago

        	if (h < 6) {
                strDesc = weekDays[w] + "凌晨 " + sh + h + " : " + sm + m;
            } else if (h < 12) {
                strDesc = weekDays[w] + "上午 " + sh + h + " : " + sm + m;
            } else if (h < 13) {
                strDesc = weekDays[w] + "下午 " + h + " : " + sm + m;
            } else if (h < 19) {
                strDesc = weekDays[w] + "下午 " + (h - 12) + " : " + sm + m;
            } else {
                strDesc = weekDays[w] + "晚上 " + (h - 12) + " : " + sm + m;
            }
        } else {

        	if (h < 6) {
                strDesc = (thenMonth + 1) + "月" + thenDay + "日" + "凌晨 " + sh
                        + h + " : " + sm + m;
            } else if (h < 12) {
                strDesc = (thenMonth + 1) + "月" + thenDay + "日" + "上午 " + sh
                        + h + " : " + sm + m;
            } else if (h < 13) {
                strDesc = (thenMonth + 1) + "月" + thenDay + "日" + "下午 " + h
                        + " : " + sm + m;
            } else if (h < 19) {
                strDesc = (thenMonth + 1) + "月" + thenDay + "日" + "下午 "
                        + (h - 12) + " : " + sm + m;
            } else {
                strDesc = (thenMonth + 1) + "月" + thenDay + "日" + "晚上 "
                        + (h - 12) + " : " + sm + m;
            }
        }
        
        return strDesc;
    }
    //显示时分秒时间搓
    public static String getTimeDisplay(Date date) {

        if (date == null) {
            return null;
        }

        String strDesc = null;
        Calendar curCalendar = Calendar.getInstance();
        Date curDate = new Date();
        curCalendar.setTime(curDate);
        Calendar thenCalendar = Calendar.getInstance();
        thenCalendar.setTime(date);

        String[] weekDays = {
                "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"
        };
        int w = thenCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        // SimpleDateFormat format = new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance(); // 今天
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天
        yesterday.setTime(curDate);
        yesterday.add(Calendar.DATE, -1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        Calendar sevendaysago = Calendar.getInstance(); // 7天
        sevendaysago.setTime(curDate);
        sevendaysago.add(Calendar.DATE, -7);
        sevendaysago.set(Calendar.HOUR_OF_DAY, 0);
        sevendaysago.set(Calendar.MINUTE, 0);
        sevendaysago.set(Calendar.SECOND, 0);

        long timediff = (curDate.getTime() - date.getTime());
        int theYear = thenCalendar.get(Calendar.YEAR);
        int thenMonth = thenCalendar.get(Calendar.MONTH);
        int thenDay = thenCalendar.get(Calendar.DAY_OF_MONTH);
        if (thenCalendar.after(today))// today
        {
            if (timediff < 5 * 60 * 1000) {
                strDesc = "刚刚";
            } else if (timediff < 60 * 60 * 1000) {
                strDesc = (timediff / 60 / 1000) + "分钟之前";
            } else {
                strDesc = (timediff / 3600 / 1000) + "小时之前";
            }
        } else if (thenCalendar.before(today) && thenCalendar.after(yesterday)) {// yestoday
            strDesc = "昨天";
        } else if (thenCalendar.before(yesterday)
                && thenCalendar.after(sevendaysago)) {// 2 ~ 7days ago
            strDesc = weekDays[w];
        } else {
            strDesc = "" + (theYear) + "-" + (thenMonth + 1) + "-"
                    + thenDay;
        }
        return strDesc;
    }

    public static String getTime4TimeTitle(Date date) {
        // M:月 d:天 a:上午或下午 h:12小时制的小时 m:分钟
        SimpleDateFormat format = new SimpleDateFormat("MM-dd a hh:mm",
                Locale.CHINA);
        return format.format(date);
    }

    public static int getCurTimeStamp() {

        return (int) (System.currentTimeMillis() / 1000);

    }
}
