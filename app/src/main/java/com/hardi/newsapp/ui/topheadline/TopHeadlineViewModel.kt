package com.hardi.newsapp.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.newsapp.data.repository.TopHeadlineRepository
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.utils.AppConstant
import com.hardi.newsapp.utils.DispatcherProvider
import com.hardi.newsapp.utils.internetcheck.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.hardi.newsapp.data.local.entity.ArticleEntity
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ArticleEntity>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<ArticleEntity>>> = _uiState

    init {
        if (networkHelper.isInternetConnected()) {
            fetchNews()
        } else {
            fetchNewsFromDB()
        }
    }

    fun fetchNews() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch {e ->
                    _uiState.value = UiState.Error(e)
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private fun fetchNewsFromDB() {
        viewModelScope.launch {
            topHeadlineRepository.getArticlesDirectlyFromDB()
                .flowOn(Dispatchers.IO)
                .catch {
                    _uiState.value = UiState.Error(it)
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}