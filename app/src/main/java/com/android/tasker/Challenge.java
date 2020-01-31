package com.android.tasker;

public class Challenge {
    private int currentDay;
    private int lastDay;
    private String startDate;

    public Challenge() {
        currentDay = 0;
        lastDay = 0;
        startDate = null;
    }

    public Challenge(int currentDay, int lastDay, String startDate) {
        this.currentDay = currentDay;
        this.lastDay = lastDay;
        this.startDate = startDate;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void start() {

    }

    public void stop() {

    }

}
