package com.bltech.mobile.ecglibrary.utils;

/**
 * Created by qindachang on 2017/2/9.
 */

public class TimeUtils {
    public static String seconds2Date(int seconds) {int hour;
        int minute = seconds / 60;
        int second;
        if (minute < 60) {
            hour = 0;
            second = seconds % 60;
        } else {
            hour = seconds / 60 / 60;
            minute = seconds / 60 % 60;
            second = seconds % 60;
        }
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
