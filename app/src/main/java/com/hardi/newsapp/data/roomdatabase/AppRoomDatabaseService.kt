package me.hardi.newsapp.data.local

import kotlinx.coroutines.flow.Flow
import me.hardi.newsapp.data.local.entity.ArticleEntity

class AppRoomDatabaseService(
    private val appRoomDataBase: AppRoomDataBase
) : DatabaseService {

    override fun getArticles(): Flow<List<ArticleEntity>> {
        return appRoomDataBase.articleDao().getAll()
    }

    override fun deleteAllAndInsertAll(articleEntity: List<ArticleEntity>) {
        return appRoomDataBase.articleDao().deleteAllAndInsertAll(articleEntity)
    }
}