package com.android.tasker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateChecker {

    static Calendar calendarNow = Calendar.getInstance();
    static Calendar calendarStart = Calendar.getInstance();

    public static int check(int currentDay, String dateStart) {

        String dateNow = //"02.01.20";
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendarNow.getTime());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = format.parse(dateNow);
            dateTwo = format.parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarNow.setTime(dateOne);
        calendarStart.setTime(dateTwo);
        calendarStart.add(Calendar.DAY_OF_MONTH, currentDay - 1);

        return calendarNow.get(Calendar.DAY_OF_YEAR) - calendarStart.get(Calendar.DAY_OF_YEAR);

    }
}
