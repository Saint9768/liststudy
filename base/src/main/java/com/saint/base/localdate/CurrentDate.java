package com.saint.base.localdate;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Saint
 * @createTime 2020-06-26 19:15
 */
public class CurrentDate {
    public static void main(String[] args) {
        //1）获取今天的日期
        LocalDate today = LocalDate.now();
        System.out.println("Today's local data : " + today);
        //使用Date获取今天的日期
        Date date = new Date();
        System.out.println(date);

        //2）获取年、月、日信息
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.printf("Year: %d, Month: %d, Day: %d。 %n ", year, month, day);

        //3）创建特定日期
        LocalDate dateOfBirth = LocalDate.of(2019, 06, 26);
        System.out.println("The special date is: " + dateOfBirth);

        //4）判断两个日期是否相等
        boolean equal = today.equals(dateOfBirth);
        System.out.println("是今天？ " + equal);

        //5）检查像生日这种周期性事件
        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.now();
        boolean happen = currentMonthDay.equals(birthday);
        System.out.println("生日到了？ " + happen);

        //6）获取当前时间
        LocalTime time = LocalTime.now();
        System.out.println("Local time now: " + time);

        //7）在现有的时间上增加小时
        LocalTime newTime = time.plusHours(2);
        System.out.println("Time after 2 hours: " + newTime);

        //8）计算一周后的日期,LocalDate的plus方法增加天数、周数或者月数。
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Today is: " + today);
        System.out.println("Date after 1 week: " + nextWeek);

        //9）计算一年前的日期
        LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
        System.out.println("Date before 1 year: " + previousYear);

        //10）Java8的Clock时钟类
        //根据系统时间返回当前时间并设置为UTC
        Clock clock = Clock.systemUTC();
        System.out.println("Clock: " + clock);

        //11）判断日期是早于还是晚于另一个日期
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        if (tomorrow.isAfter(today)) {
            System.out.println("Tomorrow comes after today!");
        }

        //12）处理时区，Java8不仅分离了日期和时间，也把时区分离出来了。
        //使用ZoneId处理特定的时区
        ZoneId america = ZoneId.of("America/New_York");
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTimeInNewYork = ZonedDateTime.of(localDateTime, america);
        System.out.println("现在的日期和时间再特定的时区是：" + zonedDateTimeInNewYork);

        //13）YearMonth 固定日期，lengthOfMonth用来获取一月有几天
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("%s have %d days. %n", currentYearMonth, currentYearMonth.lengthOfMonth());

        //14）检查闰年
        boolean isLeapYear = today.isLeapYear();
        System.out.println("今年是闰年吗？" + isLeapYear);

        //15）计算两个日期之间的天数和月数
        LocalDate java8time = LocalDate.of(2019, Month.AUGUST, 31);
        Period period = Period.between(java8time, today);
        System.out.printf("从2019年8月31到现在过去了 %d 个月。\n", period.getMonths());

        //16）包含时差信息的日期和时间
        LocalDateTime dateTime = LocalDateTime.of(2020, Month.JANUARY, 10, 11, 11);
        //时差加上5个半小时
        ZoneOffset offset = ZoneOffset.of("+05:30");
        OffsetDateTime offsetDateTime = OffsetDateTime.of(dateTime, offset);
        System.out.println("2020-1-10 11:11 加上5个半小时时差后的北京时间为：" + offsetDateTime);

        //17）获取当前的时间戳
        Instant timestamp = Instant.now();
        System.out.println("What is value of this instant：" + timestamp);

        //18）使用预定义的格式化工具去解析或格式化日期
        String dayAfterTomorrow = "20200628";
        LocalDate formatted = LocalDate.parse(dayAfterTomorrow, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s. %n", dayAfterTomorrow, formatted);

    }
}
