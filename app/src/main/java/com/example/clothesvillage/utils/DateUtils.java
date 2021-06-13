package com.example.clothesvillage.utils;

import java.text.SimpleDateFormat;


public class DateUtils {

    public static String getDate(long timeMils) {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        return date.format(timeMils);
    }
}


