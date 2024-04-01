package com.hardi.newsapp.utils

object ValidationUtils {
    fun checkIfValidArgNews(str: String?): Boolean {
        return str?.let {
            it !in listOf(null, " ", "{countryId}", "{langId}", "{sourceId}")
        } ?: false
    }
}