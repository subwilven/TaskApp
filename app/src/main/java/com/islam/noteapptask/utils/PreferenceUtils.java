package com.islam.noteapptask.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import static com.islam.noteapptask.utils.Constants.SHARED_FILE_NAME;
import static com.islam.noteapptask.utils.Constants.SHARED_KEY_USERNAME;

public final class PreferenceUtils {

    private PreferenceUtils() {
    }

    public static SharedPreferences getProviderSharedPreference(Context context) {
        return context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveUserName(Context context, String value) {
        SharedPreferences sharedPreferences = getProviderSharedPreference(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_KEY_USERNAME, value);
        editor.apply();
    }

    public static String getUserName(Context context) {
        return getProviderSharedPreference(context).getString(SHARED_KEY_USERNAME, null);
    }

}
