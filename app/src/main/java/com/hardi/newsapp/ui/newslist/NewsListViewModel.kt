package com.hardi.newsapp.ui.newslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.data.repository.TopHeadlineRepository
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.utils.AppConstant.COUNTRY_ID
import com.hardi.newsapp.utils.AppConstant.LANG_ID
import com.hardi.newsapp.utils.AppConstant.SOURCE_ID
import com.hardi.newsapp.utils.DispatcherProvider
import com.hardi.newsapp.utils.NoInternetException
import com.hardi.newsapp.utils.ValidationUtils.checkIfValidArgNews
import com.hardi.newsapp.utils.internetcheck.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    init {
        fetchNewsByfilter()
    }

    fun fetchNewsByfilter() {
        if (checkIfValidArgNews(savedStateHandle.get<String>(SOURCE_ID).toString())) {
            fetchNewsBySource(savedStateHandle.get<String>(SOURCE_ID).toString())
        } else if (checkIfValidArgNews(savedStateHandle.get<String>(COUNTRY_ID).toString())) {
            fetchNewsByCountry(savedStateHandle.get<String>(COUNTRY_ID).toString())
        } else {
            fetchNewsByLanguage(savedStateHandle.get<String>(LANG_ID).toString())
        }

    }


    private fun fetchNewsBySource(sources: String) {
        viewModelScope.launch {
            if (networkHelper.isInternetConnected()) {
                topHeadlineRepository.getNewsBySources(sources)
                    .flowOn(dispatcherProvider.io)
                    .catch {e ->
                        _uiState.value = UiState.Error(e)
                    }.collect() {
                        _uiState.value = UiState.Success(it)
                    }
            } else {
                _uiState.value = UiState.Error(
                    throwable = NoInternetException()
                )
                return@launch
            }
        }
    }

    private fun fetchNewsByCountry(country: String) {
        viewModelScope.launch {
            if (networkHelper.isInternetConnected()) {
                topHeadlineRepository.getNewsByCountry(country)
                    .flowOn(dispatcherProvider.io)
                    .catch {e ->
                        _uiState.value = UiState.Error(e)
                    }.collect() {
                        _uiState.value = UiState.Success(it)
                    }
            } else {
                _uiState.value = UiState.Error(
                    throwable = NoInternetException()
                )
                return@launch
            }
        }
    }

    private fun fetchNewsByLanguage(language: String) {
        viewModelScope.launch {
            if (networkHelper.isInternetConnected()) {
                topHeadlineRepository.getNewsByLanguage(language)
                    .flowOn(dispatcherProvider.io)
                    .catch {e ->
                        _uiState.value = UiState.Error(e)
                    }.collect() {
                        _uiState.value = UiState.Success(it)
                    }
            } else {
                _uiState.value = UiState.Error(
                    throwable = NoInternetException()
                )
                return@launch
            }
        }
    }

}