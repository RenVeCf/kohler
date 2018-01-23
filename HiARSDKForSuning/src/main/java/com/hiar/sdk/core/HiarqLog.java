package com.hiar.sdk.core;

import android.util.Log;

public class HiarqLog {
    public HiarqLog(String title) {
        m_title = title;
    }

    public void callback(int level, String msg) {
        Log.println(level, m_title, "msg: " + msg);
    }

    private String m_title;
}