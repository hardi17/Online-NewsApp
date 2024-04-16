package com.hardi.newsapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hardi.newsapp.data.api.NetworkService
import com.hardi.newsapp.data.roomdatabase.entity.toArticleEntity
import com.hardi.newsapp.utils.AppConstant.DEFAULT_COUNTRY
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.hardi.newsapp.data.local.DatabaseService


@HiltWorker
class NewsWorkManager @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted param: WorkerParameters,
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) : CoroutineWorker(context, param) {

    override suspend fun doWork(): Result {
        try {
            val articles = networkService.getTopHeadlines(DEFAULT_COUNTRY)
            val artcilesEntities = articles.articles.map { article -> article.toArticleEntity() }
            databaseService.deleteAllAndInsertAll(artcilesEntities)
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

}