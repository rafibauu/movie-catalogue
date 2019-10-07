package com.example.moviecatalogue.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    static final String DAILY_REMINDER = "daily_reminder",
            NEW_RELEASE_REMINDER = "new_release_reminder",
            LANGUAGE = "lang";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLanguage(Context context, String state){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(LANGUAGE, state);
        editor.apply();
    }

    public static String getLanguage(Context context){
        return getSharedPreference(context).getString(LANGUAGE,"");
    }

    public static void setDailyReminder(Context context, Boolean state){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(DAILY_REMINDER, state);
        editor.apply();
    }

    public static Boolean getDailyReminder(Context context){
        return getSharedPreference(context).getBoolean(DAILY_REMINDER,false);
    }

    public static void setNewReleaseReminder(Context context, Boolean state){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(NEW_RELEASE_REMINDER, state);
        editor.apply();
    }

    public static Boolean getNewReleaseReminder(Context context){
        return getSharedPreference(context).getBoolean(NEW_RELEASE_REMINDER,false);
    }

    public static void clearReminder (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(DAILY_REMINDER);
        editor.remove(NEW_RELEASE_REMINDER);
        editor.apply();
    }

    public static void clearLanguage (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(LANGUAGE);
        editor.apply();
    }
}
