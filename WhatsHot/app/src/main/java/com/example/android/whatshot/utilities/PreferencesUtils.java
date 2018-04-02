package com.example.android.whatshot.utilities;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/**
 * Created by tservo on 2018/3/27.
 */
public class PreferencesUtils {
    public static void getRadius(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    }
}