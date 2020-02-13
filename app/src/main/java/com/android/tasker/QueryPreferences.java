package com.android.tasker;

import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {

    private static final String PREF_NUMBER_OF_DAYS_QUERY = "numberOfDaysQuery";
    private static final String PREF_START_DATE_QUERY = "startDateQuery";
    private static final String PREF_LAST_COMPLETE_DATE_QUERY = "lastCompleteDateQuery";

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

    public static String getStartDateQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_START_DATE_QUERY, "");
    }

    public static void setStartDateQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_START_DATE_QUERY, query)
                .apply();
    }

    public static String getLastCompleteDateQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LAST_COMPLETE_DATE_QUERY, "");
    }

    public static void setLastCompleteDateQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_COMPLETE_DATE_QUERY, query)
                .apply();
    }

}
