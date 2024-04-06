package com.hardi.newsapp.data.repository

import com.hardi.newsapp.data.api.NetworkService
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.data.roomdatabase.entity.toArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.hardi.newsapp.data.local.AppRoomDatabaseService
import me.hardi.newsapp.data.local.DatabaseService
import me.hardi.newsapp.data.local.entity.ArticleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService){

    fun getTopHeadlines(country : String): Flow<List<ArticleEntity>>{
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles.map { article -> article.toArticleEntity() }
        }.flatMapConcat{ articleEntity ->
            flow{
                emit(databaseService.deleteAllAndInsertAll(articleEntity))
            }
        }.flatMapConcat {
            databaseService.getArticles()
        }
    }

    fun getArticlesDirectlyFromDB(): Flow<List<ArticleEntity>> {
        return databaseService.getArticles()
    }

    fun getNewsBySources(sources : String): Flow<List<Article>>{
        return flow {
            emit(networkService.getNewsBySources(sources))
        }.map {
            it.articles
        }
    }

    fun getNewsByCountry(country : String): Flow<List<Article>>{
        return flow {
            emit(networkService.getNewsByCountry(country))
        }.map {
            it.articles
        }
    }

    fun getNewsByLanguage(langauge : String): Flow<List<Article>>{
        return flow {
            emit(networkService.getNewsByLanguage(langauge))
        }.map {
            it.articles
        }
    }
}