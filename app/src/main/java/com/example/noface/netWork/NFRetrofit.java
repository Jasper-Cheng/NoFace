package com.example.noface.netWork;

import com.example.noface.interfaces.ApiServices;
import com.example.noface.Constants;
import com.example.noface.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 网络请求Retrofit
 */

public class NFRetrofit {
    private String TAG = "NFRetrofit";

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    private static class SingletonHolder {
        public static NFRetrofit instance = new NFRetrofit();
    }

    private NFRetrofit() {
        initRetrofit();
    }

    public static NFRetrofit getInstance() {
        return SingletonHolder.instance;
    }


    private OkHttpClient genericClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    LogUtil.e(TAG,"OKHttp-----:"+text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtil.e(TAG,"OKHttp-----:"+message);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(15, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(15, TimeUnit.SECONDS)//设置连接超时时间
                .eventListenerFactory(HttpEventListener.FACTORY)
                .addInterceptor(new HeadInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
        return mOkHttpClient;
    }

    private void initRetrofit() {
        if (null == mRetrofit) {
            if (null == mOkHttpClient) {
                mOkHttpClient = genericClient();
            }
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.NetWorkUrl.BASE_URL)
                    .addConverterFactory(MyGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
    }

    public ApiServices getApi() {
        return mRetrofit.create(ApiServices.class);
    }

    public static ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static ObservableTransformer schedulersIoTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
            }
        };
    }

}
