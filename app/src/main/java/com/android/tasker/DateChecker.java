package com.android.tasker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateChecker {

    static Calendar calendarNow = Calendar.getInstance();
    static Calendar calendarLastComplete = Calendar.getInstance();

    public static int check(String dateLastComplete) {

        String dateNow = //"02.01.20";
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendarNow.getTime());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = format.parse(dateNow);
            dateTwo = format.parse(dateLastComplete);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarNow.setTime(dateOne);
        calendarLastComplete.setTime(dateTwo);

        return calendarNow.get(Calendar.DAY_OF_YEAR) - calendarLastComplete.get(Calendar.DAY_OF_YEAR);

    }

    public static int getCurrentDay(String dateStart) {

        Calendar calendarNow = Calendar.getInstance();
        Calendar calendarStart = Calendar.getInstance();

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

        return calendarNow.get(Calendar.DAY_OF_YEAR) - calendarStart.get(Calendar.DAY_OF_YEAR) + 1;
    }

    public static int getDifDay(String date) {

        Calendar calendarNow = Calendar.getInstance();
        Calendar calendarStart = Calendar.getInstance();

        String dateNow = //"02.01.20";
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(calendarNow.getTime());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = format.parse(dateNow);
            dateTwo = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarNow.setTime(dateOne);
        calendarStart.setTime(dateTwo);

        return calendarNow.get(Calendar.DAY_OF_YEAR) - calendarStart.get(Calendar.DAY_OF_YEAR);
    }

}
