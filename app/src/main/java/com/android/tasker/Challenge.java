package com.android.tasker;

import android.content.Context;

public class Challenge {

    private int lastDay;
    private String startDate;
    private String lastCompleteDate;

    public Challenge(Context context) {
        lastDay = QueryPreferences.getNumberOfDaysQuery(context);
        startDate = QueryPreferences.getStartDateQuery(context);
        lastCompleteDate = QueryPreferences.getLastCompleteDateQuery(context);
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
        QueryPreferences.setStartDateQuery(context, startDate);
    }

    public String getLastCompleteDate() {
        return lastCompleteDate;
    }

    public void setLastCompleteDate(Context context, String lastCompleteDate) {
        QueryPreferences.setLastCompleteDateQuery(context, lastCompleteDate);
    }

}
