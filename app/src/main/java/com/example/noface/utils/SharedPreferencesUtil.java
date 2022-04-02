package com.example.noface.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.noface.Constants;

public class SharedPreferencesUtil {
    private static SharedPreferences mSharedPreferences;

    public static void initSharedPreferences(Context context){
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static void setToken(String token) {
        mSharedPreferences.edit().putString(Constants.TOKEN, token).apply();
    }

    public static String getToken() {
        return mSharedPreferences.getString(Constants.TOKEN, "");
    }
}
