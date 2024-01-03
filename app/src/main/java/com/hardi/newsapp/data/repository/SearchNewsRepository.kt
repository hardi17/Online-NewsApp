package com.hardi.newsapp.data.repository

import com.hardi.newsapp.data.api.NetworkService
import com.hardi.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchNewsRepository @Inject constructor(private val networkService: NetworkService) {

    fun getEverything(q: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getEverything(q))
        }.map{
            it.articles
        }
    }
}