package com.hardi.newsapp.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {


    fun closeSoftKeyboard(context: Activity) {
        try {
            context.currentFocus?.let {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                it.clearFocus()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}