package com.sinochem.printlib;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {

    // 06-12 08:32:33

    public static String geTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formate = new SimpleDateFormat("yyyyMMddHHmm");
        return formate.format(date);
    }
    public static String geTime_ymd() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd");
        return formate.format(date);
    }

    public static String geTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
        return formate.format(date);
    }

    public static String getTime_FileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formate = new SimpleDateFormat("yyyyMMddHHmmss");
        return formate.format(date);
    }

    public static String getTime_() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formate.format(date);
    }

    public static String getTime_(long time) {
        Date date = new Date(time);
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formate.format(date);
    }

    public static String getTime1(long time) {
        Date date = new Date(time);
        SimpleDateFormat formate = new SimpleDateFormat("yyyy年MM月dd日");
        return formate.format(date);
    }
    public static String getTime1() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formate = new SimpleDateFormat("yyyy年MM月");
        return formate.format(date);
    }
    public static String getTime2() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formate = new SimpleDateFormat("MM-dd");
        return formate.format(date);
    }


    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date;
        if (TextUtils.isEmpty(strTime)) {
            date = new Date(System.currentTimeMillis());
        } else {
            date = stringToDate(strTime, formatType); // String类型转成date类型
        }
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // string类型转换为date类型
// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
// HH时mm分ss秒，
// strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }
}
