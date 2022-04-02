package com.example.noface;

import android.os.Environment;

public class Constants {
    public static final boolean FORMAL_ENVIRONMENT = false;

    public static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "/" + "NoFace/";
    public static String LogPath = ROOT_PATH + "log/";
    public static String CrashPath= ROOT_PATH + "crash_user/";
    public static final String TOKEN = "token";

    public class NetWorkUrl{
        public static final String unFormal = "https://unforaml.noface.com/";
        public static final String Formal = "https://formal.noface.com/";
        public static final String URL = (FORMAL_ENVIRONMENT ?  Formal : unFormal);
        public static final String BASE_URL = URL + "api/parents/";
    }
}
