package com.hardi.newsapp.utils

import android.util.Log

object Logger {

    var LOG_TAG = "Logger"
    var isDebug = true

    fun setIsDebug(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    fun getIsDebug(): Boolean {
        return isDebug
    }

    fun i(message: String) {
        if (getIsDebug()) {
            Log.i(LOG_TAG, "" + message)
        }
    }

    fun d(message: String) {
        if (getIsDebug()) {
            Log.d(LOG_TAG, "" + message)
        }
    }

    fun v(message: String) {
        if (getIsDebug()) {
            Log.v(LOG_TAG, "" + message)
        }
    }

    fun w(message: String) {
        if (getIsDebug()) {
            Log.w(LOG_TAG, "" + message)
        }
    }

    fun e(message: String) {
        if (getIsDebug()) {
            Log.e(LOG_TAG, "" + message)
        }
    }
}