package com.android.tasker;

import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {

    private static final String PREF_CURRENT_DAY_QUERY = "currentDayQuery";
    private static final String PREF_NUMBER_OF_DAYS_QUERY = "numberOfDaysQuery";
    private static final String PREF_DATE_QUERY = "startDateQuery";

    public static int getDayQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_CURRENT_DAY_QUERY, 0);
    }

    public static void setDayQuery(Context context, int query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_CURRENT_DAY_QUERY, query)
                .apply();
    }

    public static int getNumberOfDaysQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_NUMBER_OF_DAYS_QUERY, 0);
    }

    public static void setNumberOfDaysQuery(Context context, int query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_NUMBER_OF_DAYS_QUERY, query)
                .apply();
    }

    public static String getDateQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_DATE_QUERY, "");
    }

    public static void setDateQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_DATE_QUERY, query)
                .apply();
    }

}
