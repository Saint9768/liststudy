package com.saint.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.resources.cldr.af.CalendarData_af_NA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date工具类
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-09 17:02
 */
public class DateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 小时与毫秒的换算
     */
    private static final long HOUR_MILLISECOND = 60 * 60 * 1000;

    /**
     * 天与毫秒的换算
     */
    private static final long DAY_MILLISECOND = 24 * 60 * 60 * 1000;

    /**
     * 分与毫秒的换算
     */
    private static final long MIN_MILLISECOND = 60 * 1000;

    /**
     * 秒与毫秒的换算
     */
    private static final long SECOND_MILLISECOND = 1000;

    /**
     * Date类型转String类型
     *
     * @param date    Date类型日期
     * @param pattern 格式化String类型的格式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * String类型转Date类型
     *
     * @param date    String类型日期
     * @param pattern 格式化Date类型的格式
     * @return
     */
    public static Date stringToDate(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date res = null;
        try {
            res = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            LOGGER.error("格式化日期异常！date:{},pattern:{}", date, pattern, e);
        }
        return res;
    }

    /**
     * 给指定日期date添加interval月
     *
     * @param interval 间隔月份
     */
    public static Date addMonth(Date date, int interval) {
        // 使用默认的时区和语言环境获取日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, interval);
        return calendar.getTime();
    }

    /**
     * 给指定日期date添加interval天
     *
     * @param interval 间隔天数
     */
    public static Date addDay(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, interval);
        return calendar.getTime();
    }

    /**
     * 给指定日期date添加interval小时
     *
     * @param interval 间隔小时
     */
    public static Date addHour(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, interval);
        return calendar.getTime();
    }

    /**
     * 给指定日期date添加interval分钟
     *
     * @param interval 间隔分钟
     */
    public static Date addMinute(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, interval);
        return calendar.getTime();
    }

    /**
     * 给指定日期date添加interval秒
     *
     * @param interval 间隔秒
     */
    public static Date addSecond(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, interval);
        return calendar.getTime();
    }

    public static long getIntervalDay(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / DAY_MILLISECOND;
    }

    public static long getIntervalHour(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / HOUR_MILLISECOND;
    }

    public static long getIntervalMinute(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / MIN_MILLISECOND;
    }

    public static long getIntervalSecond(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / SECOND_MILLISECOND;
    }

    public static long getIntervalMillisecond(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }
}
