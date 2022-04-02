package com.example.noface.netWork;


import com.example.noface.NFApplication;
import com.example.noface.utils.AppCommonUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * header拦截器
 * Created by 17258 on 2018/4/3.
 */

public class HeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String appVersionName = AppCommonUtil.getAppVersionName(NFApplication.getInstance());
        int appVersionCode = AppCommonUtil.getAppVersionCode(NFApplication.getInstance());
        if (null == appVersionName) {
            appVersionName = "appVersionName is null";
        }
        Request request = chain.request()
                .newBuilder()
                .addHeader("appVersionName", appVersionName)
                .addHeader("appVersionCode", appVersionName)
                .build();
        return chain.proceed(request);
    }

}
