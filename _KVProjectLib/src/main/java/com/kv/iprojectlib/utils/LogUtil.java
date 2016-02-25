package com.kv.iprojectlib.utils;

import java.io.IOException;

import android.util.Log;

public class LogUtil {

    public static final boolean DEBUG = true;

    private static final String MY_FLAG = "";

    public static void v(String TAG, String info) {
        if (DEBUG) {
            Log.v(MY_FLAG + TAG, info);
        }
    }

    public static void d(String TAG, String info) {
        if (DEBUG) {
            Log.d(MY_FLAG + TAG, info);
        }
    }

    public static void i(String TAG, String info) {
        if (DEBUG) {
            Log.i(MY_FLAG + TAG, info);
        }
    }

    public static void w(String TAG, String info) {
        if (DEBUG) {
            Log.w(MY_FLAG + TAG, info);
        }
    }

    public static void e(String TAG, String info) {
        if (DEBUG) {
            Log.e(MY_FLAG + TAG, info);
        }
    }
    
    public static void e(String info) {
        if (DEBUG) {
            Log.e(MY_FLAG, info);
        }
    }

    public static void e(String TAG, String info, IOException e) {
        if (DEBUG) {
            Log.e(MY_FLAG + TAG, info, e);
        }
    }
}
