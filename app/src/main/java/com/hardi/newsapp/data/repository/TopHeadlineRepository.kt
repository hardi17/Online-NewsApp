package com.hardi.newsapp.data.repository

import com.hardi.newsapp.data.api.NetworkService
import com.hardi.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService){

    fun getTopHeadlines(country : String): Flow<List<Article>>{
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

    fun getNewsBySources(sources : String): Flow<List<Article>>{
        return flow {
            emit(networkService.getNewsBySources(sources))
        }.map {
            it.articles
        }
    }
}