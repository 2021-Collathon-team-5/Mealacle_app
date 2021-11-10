package com.naca.mealacle.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    public static final String PREFERENCES_NAME = "user_preference";

    private static final String USER_ID = "";

    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, USER_ID);
        return value;
    }
}