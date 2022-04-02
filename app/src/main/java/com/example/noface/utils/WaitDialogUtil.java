package com.example.noface.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.noface.R;


public class WaitDialogUtil {
    private Dialog mDialog;
    private TextView mMsg;
    //private GifView mGifview;

    public WaitDialogUtil(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.dialog_loading_view);// 加载布局
        //mGifView = v.findViewById(R.id.wait_dialog_gif);
        //mGifView.setImageResource(R.drawable.gif_loading);
        mMsg = v.findViewById(R.id.dialog_show_text);
        mDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        mDialog.setCancelable(true); // 是否可以按“返回键”消失
        mDialog.setCanceledOnTouchOutside(true); // 点击加载框以外的区域
        mDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
    }

    public void setCancel(boolean cancel) {
        mDialog.setCancelable(cancel); // 是否可以按“返回键”消失
        mDialog.setCanceledOnTouchOutside(cancel); // 点击加载框以外的区域
    }

    public void showWaitDialog(String msg) {
        if (null != mDialog && !mDialog.isShowing()) {
            if (null != mMsg) {
                mMsg.setText(msg);
            }
            mDialog.show();
        }
    }


    public void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}