package com.hardi.newsapp.utils

import com.hardi.newsapp.utils.AppConstant.MORNING_TIME_FOR_UPDATE
import java.util.Calendar

object TimeUtils {

    fun calculateInitialDelay(): Long {
        // Calculate the initial delay to run the worker around 5 o'clock
        val currentTimeMillis = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTimeMillis
            set(Calendar.HOUR_OF_DAY, MORNING_TIME_FOR_UPDATE)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= currentTimeMillis) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return calendar.timeInMillis - currentTimeMillis
    }
}