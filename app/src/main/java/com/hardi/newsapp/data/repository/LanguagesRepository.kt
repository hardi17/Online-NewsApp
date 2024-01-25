package com.hardi.newsapp.data.repository

import android.content.Context
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.LocaleInfo
import com.hardi.newsapp.di.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguagesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getLanguages(): Flow<List<LocaleInfo>> {
        return flow {
            val names = context.resources.getStringArray(R.array.language_names)
            val codes = context.resources.getStringArray(R.array.language_codes)

            val resultList = names.mapIndexed { index, names ->
                LocaleInfo(names, codes[index])
            }
            emit(resultList)
        }
    }
}