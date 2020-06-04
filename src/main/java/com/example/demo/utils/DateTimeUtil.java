package com.example.demo.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zj
 * @date 2020/1/20
 */
public class DateTimeUtil {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 时间格式校验
     *
     * @param time
     * @return
     */
    public static boolean checkTime(String time) {
        return parseTime(time) != null;
    }

    /**
     * 转换时间格式
     *
     * @param time
     * @return
     */
    public static LocalTime parseTime(String time) {
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(time, timeFormatter);
        } catch (Exception e) {
            return null;
        }
        return localTime;
    }

    /**
     * 日期格式校验
     *
     * @param date
     * @return
     */
    public static boolean checkDate(String date) {
        return parseDate(date) != null;
    }

    /**
     * 转换日期格式
     *
     * @param date
     * @return
     */
    public static LocalDate parseDate(String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, dateFormatter);
        } catch (Exception e) {
            return null;
        }
        return localDate;
    }

    /**
     * 日期时间格式校验
     *
     * @param dateTime
     * @return
     */
    public static boolean checkDateTime(String dateTime) {
        return parseDate(dateTime) != null;
    }

    /**
     * 转换日期时间格式
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        } catch (Exception e) {
            return null;
        }
        return localDateTime;
    }

    /**
     * 将秒数转成用户展示的x时x分x秒格式
     *
     * @param second
     * @return
     */
    public static String transferSecondToViewDateTime(String second) {
        Integer seconds = Integer.parseInt(second);
        Integer temp;
        StringBuffer sb = new StringBuffer();
        temp = seconds / 3600;
        if (temp > 0) {
            sb.append(temp).append("小时");
        }
        temp = seconds % 3600 / 60;
        if (temp > 0) {
            sb.append(temp).append("分");
        }
        temp = seconds % 3600 % 60;
        sb.append(temp).append("秒");
        return sb.toString();
    }
}
