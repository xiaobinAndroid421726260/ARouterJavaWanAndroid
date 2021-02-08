package com.dbz.network.retrofit.utils;

import android.util.Log;

/**
 * description:
 *
 * @author Db_z
 * date 2020/3/4 13:17
 * @version V1.0
 */
public class LogUtils {
    private static boolean allow = true;
    private static final String TAG = "---LogUtils: ";

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (allow) {
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (allow) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (allow) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (allow) {
            Log.w(tag, msg);
        }
    }


}