package com.example.noface;

import android.app.Application;

import com.example.noface.Utils.SharedPreferencesUtil;

import org.litepal.LitePal;

public class NFApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //init SharedPreferences
        SharedPreferencesUtil.initSharedPreferences(this);

        initThirdPartComponent();
    }

    private void initThirdPartComponent(){
        //init LitePal Database
        LitePal.initialize(this);

    }
}
