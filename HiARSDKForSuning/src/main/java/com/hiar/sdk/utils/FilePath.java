package com.hiar.sdk.utils;

import android.os.Environment;

import java.io.File;

public class FilePath {

    public static String appName = "HiScene";

    public static final String appname = Environment.getExternalStorageDirectory().getPath() + File.separator + appName;
    public static final String appname_ = appname + File.separator;

    public static final String appname_reco = appname_ + "reco";
    public static final String appname_reco_ = appname_reco + File.separator;

    public static String keyPath = null;

    public static String getPublicKeyPath() {
        if (keyPath == null) {
            return appname_reco_;
        }
        return keyPath;
    }

    public static void setPublicKeyPath(String path) {
        keyPath = path;
    }

}
