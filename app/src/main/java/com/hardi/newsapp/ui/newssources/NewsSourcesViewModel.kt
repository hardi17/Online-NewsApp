package com.hardi.newsapp.ui.newssources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.data.model.NewsSource
import com.hardi.newsapp.data.repository.NewsSourcesRepository
import com.hardi.newsapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsSourcesViewModel(private val newsSourcesRepository: NewsSourcesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<NewsSource>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<NewsSource>>> = _uiState

    init {
        fetchNewsSources()
    }

    private fun fetchNewsSources(){
        viewModelScope.launch {
                    newsSourcesRepository.getNewsSources()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect(){
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}