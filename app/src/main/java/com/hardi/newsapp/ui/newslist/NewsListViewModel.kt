package com.hardi.newsapp.ui.newslist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.data.repository.TopHeadlineRepository
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.utils.ValidationUtils.checkIfValidArgNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val topHeadlineRepository: TopHeadlineRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    init {
        fetchNewsByfilter()
    }

    private fun fetchNewsByfilter() {
        if (checkIfValidArgNews(savedStateHandle.get<String>("sourceId").toString())) {
            fetchNewsBySource(savedStateHandle.get<String>("sourceId").toString())
        } else if (checkIfValidArgNews(savedStateHandle.get<String>("countryId").toString())) {
            fetchNewsByCountry(savedStateHandle.get<String>("countryId").toString())
        } else {
            fetchNewsByLanguage(savedStateHandle.get<String>("langId").toString())
        }

    }


    private fun fetchNewsBySource(sources: String) {
        viewModelScope.launch {
            topHeadlineRepository.getNewsBySources(sources)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect() {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private fun fetchNewsByCountry(country: String) {
        viewModelScope.launch {
            topHeadlineRepository.getNewsByCountry(country)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect() {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private fun fetchNewsByLanguage(language: String) {
        viewModelScope.launch {
            topHeadlineRepository.getNewsByLanguage(language)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect() {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}