package com.example.noface.netWork;

import com.example.noface.base.BaseServiceData;
import com.example.noface.utils.LogUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MyGsonConverterFactory extends Converter.Factory {

    private static final String TAG = "MyGsonConverterFactory";

    public static MyGsonConverterFactory create() {
        return new MyGsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new JsonResponseBodyConverter<JsonObject>();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

        JsonResponseBodyConverter() {

        }

        @Override
        public T convert(ResponseBody value) throws IOException {

            BaseServiceData data = new BaseServiceData();

            try {
                String string = value.string();
                com.google.gson.JsonParser jsonParser = new com.google.gson.JsonParser();
                JsonElement parse = jsonParser.parse(string);
                if (null != parse && parse.isJsonObject()) {
                    JsonObject res = jsonParser.parse(string).getAsJsonObject();
                    String code = res.get("code").getAsString();
                    JsonObject jsonObject = null;
                    JsonElement jsonElement = res.get("data");
                    if (null != jsonElement && !jsonElement.isJsonNull() && jsonElement.isJsonObject()) {
                        jsonObject = jsonElement.getAsJsonObject();
                    }
                    String msg = res.get("msg").getAsString();
                    String status = res.get("status").getAsString();
                    data.setStatus(status);
                    data.setData(jsonObject);
                    data.setMsg(msg);
                    data.setCode(code);
                }
                return (T) data;
            } catch (Exception e) {
                LogUtil.e(TAG, "parseData Exception = " + e.getMessage());
                return (T) data;
            }
        }
    }

}
