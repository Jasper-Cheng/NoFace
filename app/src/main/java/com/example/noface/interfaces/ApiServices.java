package com.example.noface.interfaces;

import com.example.noface.base.BaseServiceData;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 网络请求函数集
 */

public interface ApiServices {


    @POST("--")
    Observable<BaseServiceData> getSchoolList(@Query("accessToken") String token, @Body RequestBody requestBody);

}
