package com.example.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {
    public static String fromDBtoDisplay(String dateSrc) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfSrc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
        try {
            calendar.setTime(sdfSrc.parse(dateSrc));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdfDst = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        sdfDst.setTimeZone(TimeZone.getDefault());
        return sdfDst.format(calendar.getTime());
    }
}
