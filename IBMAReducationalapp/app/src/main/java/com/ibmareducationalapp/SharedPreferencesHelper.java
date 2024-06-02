package com.ibmareducationalapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String PREFERENCES_FILE = "AppPrefs";
    private static final String USER_ID_KEY = "UserId";

    public static int getCurrentUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        // This will return -1 if no user id was saved which indicates that the user is not logged in or there's an error
        return prefs.getInt(USER_ID_KEY, -1);
    }

    public static void saveCurrentUserId(Context context, int userId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }
}
