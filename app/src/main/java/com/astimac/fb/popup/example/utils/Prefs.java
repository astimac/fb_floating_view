package com.astimac.fb.popup.example.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by alex on 6/10/15.
 */
public class Prefs {

    private static final String NAME = "fb_floating_view_prefs";

    private static final String KEY_SERVICE_RUNNING = "key_service_running";

    private static final String KEY_X = "key_position_x";

    private static final String KEY_Y = "key_position_y";

    public Prefs() {

    }

    public static void setServiceRunning(@NonNull Context context, boolean serviceRunning) {
        SharedPreferences mPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean(KEY_SERVICE_RUNNING, serviceRunning);
        mEditor.apply();
    }

    public static void setX(@NonNull Context context, int x) {
        SharedPreferences mPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt(KEY_X, x);
        mEditor.apply();
    }

    public static void setY(@NonNull Context context, int y) {
        SharedPreferences mPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt(KEY_Y, y);
        mEditor.apply();
    }

    public static boolean isServiceRunning(@NonNull Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return mPrefs.getBoolean(KEY_SERVICE_RUNNING, false);
    }

    public static int getX(@NonNull Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return mPrefs.getInt(KEY_X, 0);
    }

    public static int getY(@NonNull Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return mPrefs.getInt(KEY_Y, 0);
    }
}
