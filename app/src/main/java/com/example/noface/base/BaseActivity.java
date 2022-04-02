package com.example.noface.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noface.NFApplication;
import com.example.noface.R;
import com.example.noface.utils.StatusBarUtil;
import com.example.noface.utils.WaitDialogUtil;
import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {
    private WaitDialogUtil mDialog;
    public static Gson mGson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTranslucentStatus();
        mGson = new Gson();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showProgress(String title) {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.loading);
        }
        if (null == mDialog) {
            mDialog = new WaitDialogUtil(this);
        }
        mDialog.showWaitDialog(title);
    }

    public void showProgress(boolean cancelAble, String title) {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.loading);
        }
        if (null == mDialog) {
            mDialog = new WaitDialogUtil(this);
        }
        mDialog.setCancel(cancelAble);
        mDialog.showWaitDialog(title);
    }

    public void setDialogCancel(boolean cancel) {
        if (null == mDialog) {
            mDialog = new WaitDialogUtil(this);
        }
        mDialog.setCancel(cancel);
    }

    public void dismissProgress() {
        if (null != mDialog) {
            mDialog.closeDialog();
        }
    }

    /**
     * 设置状态栏透明
     */
    protected void setTranslucentStatus() {
        // 如果您的项目是6.0以上机型, 推荐使用两个参数的setUseStatusBarColor。
        StatusBarUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));

        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色
        StatusBarUtil.setSystemStatus(this, true, true);
    }

    /**
     * Android 6.0 以上设置状态栏颜色
     */
    protected void setStatusBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgress();
        NFApplication.finishAllActivityByMap();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

}
