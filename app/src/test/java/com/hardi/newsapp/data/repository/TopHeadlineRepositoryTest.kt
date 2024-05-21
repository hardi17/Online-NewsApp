package com.hardi.newsapp.data.repository

import app.cash.turbine.test
import com.hardi.newsapp.data.api.NetworkService
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.data.model.Source
import com.hardi.newsapp.data.model.TopHeadlinesResponse
import com.hardi.newsapp.data.roomdatabase.entity.toArticleEntity
import com.hardi.newsapp.utils.AppConstant
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.hardi.newsapp.data.local.DatabaseService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    @Mock
    private lateinit var databaseService: DatabaseService

    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Before
    fun setUp() {
        topHeadlineRepository = TopHeadlineRepository(networkService, databaseService)
    }

    @Test
    fun getTopHeadlines_whenNetworkServiceResponse_success() {
        runTest {
            val articleList = mutableListOf<Article>()
            val sourceItem = Source("0", "test")
            val article = Article(
                "Dummy news",
                "For testing purpose",
                "", "",
                "url",
                "dummyImageUrl",
                sourceItem
            )
            articleList.add(article)

            val response = TopHeadlinesResponse(
                "Ok", 0, articleList
            )

            val articleEntities = articleList.map { it.toArticleEntity() }

            doReturn(response)
                .`when`(networkService)
                .getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
            doNothing()
                .`when`(databaseService)
                .deleteAllAndInsertAll(articleEntities)
            doReturn(flowOf(articleEntities))
                .`when`(databaseService)
                .getArticles()
            topHeadlineRepository.getTopHeadlines(AppConstant.DEFAULT_COUNTRY).test {
                assertEquals(articleEntities, awaitItem())
                cancelAndConsumeRemainingEvents()
            }

            verify(networkService, times(1)).getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
            verify(databaseService, times(1)).deleteAllAndInsertAll(articleEntities)
            verify(databaseService, times(1)).getArticles()
        }
    }

    @Test
    fun getTopHeadlines_whenNetworkServiceResponse_error() {
        runTest {
            val error = "Too many request: 429"

            doThrow(RuntimeException(error))
                .`when`(networkService)
                .getTopHeadlines(AppConstant.DEFAULT_COUNTRY)

            topHeadlineRepository.getTopHeadlines(AppConstant.DEFAULT_COUNTRY).test {
                assertEquals(error, awaitError().message)
                cancelAndConsumeRemainingEvents()
            }

            verify(networkService, times(1)).getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
        }
    }

}