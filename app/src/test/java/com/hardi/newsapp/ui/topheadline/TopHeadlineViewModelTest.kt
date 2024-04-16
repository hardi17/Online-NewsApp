package com.hardi.newsapp.ui.topheadline

import app.cash.turbine.test
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.data.repository.TopHeadlineRepository
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.utils.AppConstant
import com.hardi.newsapp.utils.DispatcherProvider
import com.hardi.newsapp.utils.TestDispatcherProvider
import com.hardi.newsapp.utils.internetcheck.NetworkHelper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineViewModelTest {

    @Mock
    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Mock
    private lateinit var networkHelper: NetworkHelper

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun fetchNews_whenRepositoryResponse_success_shouldSetSuccessUiState() {
        runTest {
            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flowOf(emptyList<Article>()))
                .`when`(topHeadlineRepository)
                .getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
            val viewModel =
                TopHeadlineViewModel(topHeadlineRepository, dispatcherProvider, networkHelper)
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<Article>()), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
            verify(topHeadlineRepository, times(1)).getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
            verify(networkHelper, times(1)).isInternetConnected()
        }
    }

    @Test
    fun fetchNews_whenRepositoryResponse_error_shouldSetErrorUiState() {
        runTest {
            val exception = IllegalStateException("Testing error message")
            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flow<List<Article>> {
                throw exception
            })
                .`when`(topHeadlineRepository)
                .getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
            val viewModel =
                TopHeadlineViewModel(topHeadlineRepository, dispatcherProvider, networkHelper)
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(exception,null).toString(),
                    awaitItem().toString()
                )
                cancelAndConsumeRemainingEvents()
            }

            verify(topHeadlineRepository, times(1)).getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
            verify(networkHelper, times(1)).isInternetConnected()
        }
    }

    @After
    fun tearDown() {
        /*if required when you have to stop/close something like livedata kind of thing*/
    }

}