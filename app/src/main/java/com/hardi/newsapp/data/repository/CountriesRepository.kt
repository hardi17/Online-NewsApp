package com.hardi.newsapp.data.repository

import android.content.Context
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.LocaleInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getCountries(): Flow<List<LocaleInfo>> {
        return flow {
            val names = context.resources.getStringArray(R.array.country_name)
            val codes = context.resources.getStringArray(R.array.country_code)
            val resultList = names.mapIndexed { index, names ->
                LocaleInfo(names, codes[index])
            }
            emit(resultList)
        }
    }

}