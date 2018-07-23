package com.betsy.chatsy.weatherapp.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateParser {

    public static String pattern = "yyyy-MM-dd HH:mm:ss";

    public static Date stringToDate(String s) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(s);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String dateToString(Date date) {

        DateFormat df = new SimpleDateFormat(pattern);

        String reportDate = df.format(date);

        return reportDate;
    }

}
