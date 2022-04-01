package com.example.noface;

import android.app.Activity;
import android.app.Application;

import com.example.noface.Utils.LogUtil;
import com.example.noface.Utils.SharedPreferencesUtil;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.Map;

public class NFApplication extends Application {
    private static String TAG="NFApplication";
    private static Map<String, Activity> mExistActivity=new HashMap<>();//record activity for global unique

    @Override
    public void onCreate() {
        super.onCreate();

        //init SharedPreferences
        SharedPreferencesUtil.initSharedPreferences(this);

        initThirdPartComponent();

        //崩溃和数据库包和ignore和网络框架okhttp,各种工具包
    }

    private void initThirdPartComponent(){
        //init LitePal Database
        LitePal.initialize(this);

    }

    public static void addActivityToMap(String activityName,Activity activity){
        mExistActivity.put(activityName,activity);
    }
    public static void destroyActivityToMap(String activityName){
        LogUtil.i(TAG, "destroyActivityToMap:" + activityName);
        try {
            if (mExistActivity.containsKey(activityName)) {
                mExistActivity.get(activityName).finish();
                mExistActivity.remove(activityName);
            }
        } catch (Exception e) {

        }
    }
}
