package com.hardi.newsapp.ui.searchactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.data.repository.SearchNewsRepository
import com.hardi.newsapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class SearchViewModel(private val searchNewsRepository: SearchNewsRepository ): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    fun fetchEverything(query : String){
        viewModelScope.launch {
            searchNewsRepository.getEverything(query)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect(){
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}