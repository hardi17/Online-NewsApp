package me.hardi.newsapp.data.local

import kotlinx.coroutines.flow.Flow
import me.hardi.newsapp.data.local.entity.ArticleEntity

interface DatabaseService {

    fun getArticles(): Flow<List<ArticleEntity>>

    fun deleteAllAndInsertAll(articleEntity: List<ArticleEntity>)
}