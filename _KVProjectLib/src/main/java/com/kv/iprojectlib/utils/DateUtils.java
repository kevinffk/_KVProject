package com.kv.iprojectlib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.text.TextUtils;

public class DateUtils {

    public static String getDateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    public static String getDateTimeFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }
    
    public static String getChinaTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    
    public static int getYearOfDateFormat(String date) {
        String [] dates = date.split("-");
        return Integer.parseInt(dates[0]);
    }
    
    public static int getMonthOfDateFormat(String date) {
        String [] dates = date.split("-");
        return Integer.parseInt(dates[1]);
    }
    
    public static int getDayOfDateFormat(String date) {
        String [] dates = date.split("-");
        return Integer.parseInt(dates[2]);
    }
    
    public static boolean isCorrectDateRange(String startTime, String endTime) {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = sdf.parse(startTime);
                Date endDate = sdf.parse(endTime);
                return endDate.getTime() - startDate.getTime() >= 0;
            } catch(Exception e) {
                return false;
            }
        }
        return false;
    }
    
    public static boolean isAfterToday(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date targetDate = sdf.parse(dateStr);
            Date soureDate = sdf.parse(sdf.format(new Date()));
            return targetDate.getTime() > soureDate.getTime();
        } catch(Exception e) {
            return false;
        }
    }
}
