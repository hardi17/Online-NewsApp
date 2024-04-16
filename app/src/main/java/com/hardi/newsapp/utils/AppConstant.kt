package com.hardi.newsapp.utils

import com.hardi.newsapp.BuildConfig

object AppConstant {

    const val DEBOUNCE_TIMEOUT = 200L
    const val MIN_SEARCH_CHAR = 2
    const val DEFAULT_COUNTRY = "us"
    const val COUNTRY_ID = "countryId"
    const val LANG_ID = "langId"
    const val SOURCE_ID = "sourceId"
    const val INITIAL_PAGE = 1
    const val PAGE_SIZE = 10
    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = BuildConfig.API_KEY

    const val DATABASE_NAME = "news-database"
    const val WORKMANAGER_NAME = "NewsWorkManager"
    const val MORNING_TIME_FOR_UPDATE = 5
}