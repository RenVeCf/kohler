package com.kohler.arscan.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreUtil {
    private final static String nameString = "config";
    private static SharePreUtil Instances;
    private SharedPreferences sp;

    public static synchronized SharePreUtil getInstance(Context context) {
        if (Instances == null) {
            synchronized (SharePreUtil.class) {
                if (Instances == null) {
                    Instances = new SharePreUtil(context.getApplicationContext());
                }
            }
        }
        return Instances;
    }

    private SharePreUtil(Context context) {
        sp = context.getSharedPreferences(nameString, Context.MODE_PRIVATE);
    }

    public void saveConfig(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public void saveConfig(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public void saveConfig(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public void saveConfig(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public void saveConfig(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }


    public boolean getConfig(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public float getConfig(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public int getConfig(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long getConfig(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public String getConfig(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean clearConfig() {
        SharedPreferences.Editor edit = sp.edit();
        return edit.clear().commit();
    }
}
