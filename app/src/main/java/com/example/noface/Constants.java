package com.example.noface;

import android.os.Environment;

public class Constants {
    public static final boolean FORMAL_ENVIRONMENT = false;

    public static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "/" + "NoFace/";
    public static String LogPath = ROOT_PATH + "log/";
    public static String CrashPath= ROOT_PATH + "crash_user/";
    public static final String TOKEN = "token";
    private static final String FILE_UnFORMAL="https://unformal.img.noface.com";
    private static final String FILE_FORMAL="https://formal.img.noface.com";
    public static final String FILE_URL = (FORMAL_ENVIRONMENT ? FILE_FORMAL : FILE_UnFORMAL);


    public class NetWorkUrl{
        public static final String unFormal = "https://unforaml.noface.com/";
        public static final String Formal = "https://formal.noface.com/";
        public static final String URL = (FORMAL_ENVIRONMENT ?  Formal : unFormal);
        public static final String BASE_URL = URL + "api/parents/";
    }
}
