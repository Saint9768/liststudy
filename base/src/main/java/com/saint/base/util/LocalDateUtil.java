package com.saint.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 本地日期和时间格式化工具
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-08 7:00
 */
public class LocalDateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateUtil.class);

    /**
     * String类型转LocalDateTime类型
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return LocalDateTime类型
     */
    public static LocalDateTime stringToLocalDateTime(String date, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            return LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * String类型转LocalDate类型
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return LocalDate类型
     */
    public static LocalDate stringToLocalDate(String date, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            return LocalDate.of(1900, 1, 1);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }

    /**
     * String类型转LocalTime类型
     *
     * @param date    时间字符串
     * @param pattern 时间格式
     * @return LocalTime类型
     */
    public static LocalTime stringToLocalTime(String date, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            return LocalTime.of(0, 0, 0);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(date, formatter);
    }

    /**
     * LocalDateTime类型转String类型
     *
     * @param localDateTime localDateTime类型日期
     * @param pattern       字符串格式
     * @return String类型
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            return "1900-01-01T00:00:00.000";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * LocalDate类型转String类型
     *
     * @param localDate localDate类型日期
     * @param pattern   字符串格式
     * @return String类型
     */
    public static String localDateToString(LocalDate localDate, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            return "1900-01-01";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(formatter);
    }

    /**
     * Date类型转LocalDateTime类型
     *
     * @param date Date类型日期
     * @return LocalDateTime类型
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        }
        Instant instant = date.toInstant();
        // 获取本地时区
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * Date类型转LocalDate类型
     *
     * @param date Date类型日期
     * @return LocalDate类型
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date == null) {
            return LocalDate.of(1900, 1, 1);
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.toLocalDate();
    }

    /**
     * Date类型转LocalTime类型
     *
     * @param date Date类型日期
     * @return LocalTime类型
     */
    public static LocalTime dateToLocalTime(Date date) {
        if (date == null) {
            return LocalTime.of(0, 0, 0);
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.toLocalTime();
    }

    /**
     * LocalDateTime类型转Date类型
     *
     * @param localDateTime LocalDateTime类型时间
     * @return Date类型
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            localDateTime = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        }
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate类型转Date类型
     *
     * @param localDate LocalDate类型日期
     * @return Date类型
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.of(1900, 1, 1);
        }
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalTime类型 + LocalDate类型 转Date类型
     *
     * @param localDate LocalDate类型日期
     * @param localTime LocalTime类型时间
     * @return Date类型
     */
    public static Date localTimeToDate(LocalDate localDate, LocalTime localTime) {
        if (localTime == null) {
            return localDateToDate(localDate);
        }
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return localDateTimeToDate(localDateTime);
    }

}
