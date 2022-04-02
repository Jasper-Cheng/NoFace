package com.example.noface;

import android.app.Activity;
import android.app.Application;

import com.example.noface.utils.LogUtil;
import com.example.noface.utils.SharedPreferencesUtil;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.Map;

public class NFApplication extends Application {
    private static String TAG="NFApplication";

    private static NFApplication application;

    private static Map<String, Activity> mExistActivity=new HashMap<>();//record activity for global unique

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        //init SharedPreferences
        SharedPreferencesUtil.initSharedPreferences(this);
        //init SSJCrashHandler
        NFCrashHandler.getInstance().init(getApplicationContext());

        initThirdPartComponent();
    }

    public static NFApplication getInstance() {
        return application;
    }

    private void initThirdPartComponent(){
        //init LitePal Database
        LitePal.initialize(this);

    }

    public static void addActivityToMap(String activityName,Activity activity){
        mExistActivity.put(activityName,activity);
    }
    public static void destroyActivityByMap(String activityName){
        LogUtil.i(TAG, "destroyActivityToMap:" + activityName);
        try {
            if (mExistActivity.containsKey(activityName)) {
                mExistActivity.get(activityName).finish();
                mExistActivity.remove(activityName);
            }
        } catch (Exception e) {

        }
    }

    public static void finishAllActivityByMap() {
        for (int i = 0, size = mExistActivity.size(); i < size; i++) {
            if (null != mExistActivity.get(i)) {
                mExistActivity.get(i).finish();
            }
        }
        mExistActivity.clear();
    }
}
