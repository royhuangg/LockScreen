package com.roy.lockscreen;

import android.util.Log;

/**
 * Created by Roy on 2017/2/16.
 */

public class Config {
    public static boolean DEBUG = BuildConfig.DEBUG;

    public static String TAG = "LockScreen";

    public static void logd(String message) {
        if (DEBUG) {
            Log.d(Config.TAG, message);
        }
    }

    public static void logd(Boolean message) {
        if (DEBUG) {
            Log.d(Config.TAG, message.toString());
        }
    }

    public static void loge(String message) {
        if (DEBUG) {
            Log.e(Config.TAG, message);
        }
    }

    public static void loge(String message, Throwable th) {
        if (DEBUG) {
            Log.e(Config.TAG, message, th);
        }
    }

    public static void loge(Throwable th) {
        if (DEBUG) {
            Log.e(Config.TAG, null, th);
        }
    }

    public static void logi(String message) {
        if (DEBUG) {
            Log.i(Config.TAG, message);
        }
    }

    public static void logi(Boolean message) {
        if (DEBUG) {
            Log.i(Config.TAG, message.toString());
        }
    }
}
