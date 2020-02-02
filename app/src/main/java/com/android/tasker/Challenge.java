package com.android.tasker;

import android.content.Context;

public class Challenge {

    private int currentDay;
    private int lastDay;
    private String startDate;

    public Challenge(Context context) {
        currentDay = QueryPreferences.getDayQuery(context);
        lastDay = QueryPreferences.getNumberOfDaysQuery(context);
        startDate = QueryPreferences.getDateQuery(context);
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Context context, int currentDay) {
        QueryPreferences.setDayQuery(context, currentDay);
    }

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(Context context, int lastDay) {
        QueryPreferences.setNumberOfDaysQuery(context, lastDay);
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(Context context, String startDate) {
        QueryPreferences.setDateQuery(context, startDate);
    }

}
