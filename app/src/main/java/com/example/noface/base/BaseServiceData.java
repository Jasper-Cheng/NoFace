package com.example.noface.base;


import com.google.gson.JsonObject;

import java.io.Serializable;


public class BaseServiceData implements Serializable {
    private String code;
    private String msg;
    private String status;
    private JsonObject data;

    public BaseServiceData() {
    }

    public BaseServiceData(String statu, String msg, String suc, JsonObject obj) {
        code = statu;
        this.msg = msg;
        status = suc;
        data = obj;
    }

    public String getCode() {
        return code;
    }

    public JsonObject getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("code:");
        builder.append(code);
        builder.append(",msg:");
        builder.append(msg);
        builder.append(",status:");
        builder.append(status);
        builder.append(",data:");
        builder.append(data);
        return builder.toString();
    }
}
